package com.orchestral.papoy.plugin.wprob.handlers;

import java.io.InputStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.orchestral.papoy.plugin.wprob.CreateWebProjectDialog;
import com.orchestral.papoy.plugin.wprob.WebBundleBuildUtils;
import com.orchestral.papoy.plugin.wprob.WebModuleMetadata;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 *
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class WebBundleBuildHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public WebBundleBuildHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information from the application context.
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		final TreePath treePath = getClickLocation(window);
		final String eventTrigger = getResourceLocation(treePath);
		if (!(eventTrigger.contains("src/main/web") || eventTrigger.contains("src\\main\\web"))) {
			MessageDialog.openError(window.getShell(), "Invalid web project location",
					"Pls create web project module in 'src/main/web'.");
		} else {
			final CreateWebProjectDialog dialog = new CreateWebProjectDialog(window.getShell());
			dialog.create();
			if (dialog.open() == Window.OK) {
				final WebModuleMetadata metaData = dialog.getMetaData();
				if (metaData != null) {
					final String moduleName = metaData.getModuleName();
					final IProject project = getProject(treePath);
					final IFolder srcFolder = project.getFolder("src\\main\\web");

					// Create root module
					final IFolder moduleFolder = createFolder(window, srcFolder, moduleName, true);

					// Create skinnable content
					if (metaData.isSkinnable()) {
						final IFolder assetsFolder = createFolder(window, moduleFolder, "assets", false);
						final IFolder skinsFolder = createFolder(window, assetsFolder, "skins", false);
						final IFolder hiveFolder = createFolder(window, skinsFolder, "hive", false);
						createFileUnderFolderFromInputStream(window, hiveFolder, "_skin.scss", WebBundleBuildUtils.getHiveInputStream());

						createFileUnderFolderFromInputStream(window, hiveFolder, moduleName + ".scss",
								WebBundleBuildUtils.getImportCssInputStream());
					}

					// Create js folder
					final IFolder jsFolder = createFolder(window, moduleFolder, "js", false);
					createFileUnderFolderFromInputStream(window, jsFolder, moduleName + ".js",
							WebBundleBuildUtils.getJavascriptClassInputStream(metaData));

					// Create meta folder
					final IFolder metaFolder = createFolder(window, moduleFolder, "meta", false);
					createFileUnderFolderFromInputStream(window, metaFolder, moduleName + ".json",
							WebBundleBuildUtils.getMetaInputStream(metaData));

					// Create build.json
					createFileUnderFolderFromInputStream(window, moduleFolder, "build.json",
							WebBundleBuildUtils.getBuildInputStream(metaData));
				}
			}
		}
		return null;
	}

	public IFile createFileUnderFolderFromInputStream(final IWorkbenchWindow window, final IFolder folder, final String nameOfNewFile,
			final InputStream inputStream) {
		final IFile ifile = folder.getFile(nameOfNewFile);
		if (!ifile.exists()) {
			try {
				ifile.create(inputStream, false, null);
			} catch (final CoreException e) {
				MessageDialog.openError(window.getShell(), "Oops!",
						"Could not create all files under the web project module. Try again after deleting the module or manually add them.");
			}
		}
		return ifile;
	}

	public IFolder createFolder(final IWorkbenchWindow window, final IFolder parentFolder, final String name, final boolean isModule) {
		final IFolder folder = parentFolder.getFolder(name);
		if (!folder.exists()) {
			try {
				folder.create(false, true, null);
			} catch (final CoreException e) {
				MessageDialog.openError(window.getShell(), "Unable to create web project module",
						String.format("Unable to create web project module folder '%s'", folder));
				throw new IllegalStateException(e);
			}
		} else if (isModule) {
			MessageDialog.openError(window.getShell(), "Duplicate web project Module",
					String.format("The web project module '%s' already exists. Try another name.", name));
		}
		return folder;
	}

	/**
	 * Extract the location where right-click+ create web project module
	 */
	private TreePath getClickLocation(final IWorkbenchWindow window) {
		final TreeSelection treeSelection = getSelection(window);
		final TreePath[] treePaths = treeSelection.getPaths();
		final TreePath treePath = treePaths[0];
		return treePath;
	}

	private TreeSelection getSelection(final IWorkbenchWindow window) {
		// Get the active WorkbenchPage
		final IWorkbenchPage activePage = window.getActivePage();
		// Get the Selection from the active WorkbenchPage page
		final ISelection selection = activePage.getSelection();
		if (!(selection instanceof ITreeSelection)) {
			final String selectionClass = selection.getClass().getSimpleName();
			MessageDialog.openError(window.getShell(), "Web project module Plugin", String
					.format("Expected a TreeSelection but got a %s instead.\nProcessing Terminated.", selectionClass));
		}

		return (TreeSelection) selection;
	}

	private String getResourceLocation(final TreePath treePath) {
		final Object lastSegmentObj = treePath.getLastSegment();
		final IResource theResource = ((IAdaptable) lastSegmentObj).getAdapter(IResource.class);
		if (theResource == null) {
			return null;
		}
		return theResource.getLocation().toOSString();
	}

	private IProject getProject(final TreePath treePath) {
		final Object lastSegmentObj = treePath.getLastSegment();
		final IResource theResource = ((IAdaptable) lastSegmentObj).getAdapter(IResource.class);
		if (theResource == null) {
			return null;
		}
		return theResource.getProject();
	}

}
