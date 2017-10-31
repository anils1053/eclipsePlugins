package com.orchestral.papoy.plugin.aplore.handlers;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.orchestral.aplore.client.property.PropertyScanner;
import com.orchestral.aplore.client.runner.ApiPublisher;
import com.orchestral.aplore.restapi.scanner.RestAPIProperties;
import com.orchestral.aplore.restapi.scanner.RestAPIPropertiesDialog;

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
	 *
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

		final TreePath treePath = getClickLocation(window);
		final String resourceLocation = getResourceLocation(treePath);
		final String aplorePropertiesFileBasePath = resourceLocation.substring(0, resourceLocation.indexOf("com."));
		final String bundleName = resourceLocation.substring(aplorePropertiesFileBasePath.length(),
				resourceLocation.indexOf("\\src"));
		System.out.println("bundleName" + bundleName);
		if (resourceLocation.contains("resources") || resourceLocation.contains("resource")) {

			final RestAPIPropertiesDialog dialog = new RestAPIPropertiesDialog(window.getShell(),
					getRestAPIProperties(aplorePropertiesFileBasePath));
			dialog.create();
			if (dialog.open() == Window.OK) {
				final String clickingClassPath = resourceLocation.replace('\\', '.').replace(".java", "");
				final String className = clickingClassPath.substring(clickingClassPath.lastIndexOf("com."),
						clickingClassPath.length());
				final IProject project = getProject(treePath);
				final IJavaProject javaProject = JavaCore.create(project);
				final List<URL> urls = calculateProjectClasspath(javaProject);

				final URLClassLoader aploreClassLoader = new AploreURLClassloader(urls.toArray(new URL[0]),
						javaProject.getClass().getClassLoader());
				Thread.currentThread().setContextClassLoader(aploreClassLoader);
				final ClassLoader cl = Thread.currentThread().getContextClassLoader();
				try {
					final Class pathClass = cl.loadClass("javax.ws.rs.Path");
					final Class clickedRestClass = cl.loadClass(className);
					final RestAPIProperties properties = dialog.getProperties();
					updateRestAPIProperties(aplorePropertiesFileBasePath, properties);
					final ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(window.getShell());
					progressDialog.run(true, true, new IRunnableWithProgress() {

						@Override
						public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
							monitor.beginTask("Start publishing REST API to Aplore repository", 1);
							new ApiPublisher().publisher(clickedRestClass, pathClass, cl, properties, bundleName);
							monitor.done();
						}
					});

				} catch (final Exception e) {
					MessageDialog.openError(window.getShell(), "Publish API Fail",
							"You may need to check whether or not Aplore repository is running and also verify all property parameter value.");
				}
			}
		} else {
			MessageDialog.openError(window.getShell(), "Invalid REST resource path",
					"Pls create REST class in a package name which is end with resources or resource eg: com.orchestral.referrals.request.ws.rest.resources");
		}
		return null;
	}

	private RestAPIProperties getRestAPIProperties(final String aplorePropertiesFileBasePath) {
		return PropertyScanner.readVersionProperties(aplorePropertiesFileBasePath);
	}

	private void updateRestAPIProperties(final String propertyFielPath, final RestAPIProperties properties) {
		PropertyScanner.createOrUpdateAploreProperties(propertyFielPath, properties);
	}

	private String getResourceLocation(final TreePath treePath) {
		final Object lastSegmentObj = treePath.getLastSegment();
		final IResource theResource = ((IAdaptable) lastSegmentObj).getAdapter(IResource.class);
		if (theResource == null) {
			return null;
		}
		return theResource.getLocation().toOSString();
	}

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

	private IProject getProject(final TreePath treePath) {
		final Object lastSegmentObj = treePath.getLastSegment();
		final IResource theResource = ((IAdaptable) lastSegmentObj).getAdapter(IResource.class);
		if (theResource == null) {
			return null;
		}
		return theResource.getProject();
	}

	private List<URL> calculateProjectClasspath(final IJavaProject jp) {
		final HashSet<IPath> outputPath = new HashSet<IPath>();
		final HashSet<IPath> libraries = new HashSet<IPath>();
		resolveDataProject(jp, outputPath, libraries);

		final IWorkspaceRoot root = jp.getProject().getWorkspace().getRoot();

		final List<URL> rv = new ArrayList<URL>();
		for (final IPath out : outputPath) {
			final IFolder f = root.getFolder(out);
			if (f.exists()) {
				try {
					rv.add(f.getLocation().toFile().toURI().toURL());
				} catch (final MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		for (final IPath lib : libraries) {
			// System.err.println("LIB: " + lib);
			final IFile f = root.getFile(lib);
			if (f.exists()) {
				try {
					rv.add(f.getLocation().toFile().toURI().toURL());
				} catch (final MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (lib.toFile().exists()) {
				try {
					rv.add(lib.toFile().toURI().toURL());
				} catch (final MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return rv;
	}

	private void resolveDataProject(final IJavaProject project, final Set<IPath> outputPath,
			final Set<IPath> listRefLibraries) {
		// System.err.println("START RESOLVE: " + project.getElementName());
		try {
			final IClasspathEntry[] entries = project.getRawClasspath();
			outputPath.add(project.getOutputLocation());
			for (final IClasspathEntry e : entries) {
				// System.err.println(e + " ====> " + e.getEntryKind());
				if (e.getEntryKind() == IClasspathEntry.CPE_PROJECT) {
					final IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(e.getPath().lastSegment());
					if (p.exists()) {
						resolveDataProject(JavaCore.create(p), outputPath, listRefLibraries);
					}
				} else if (e.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
					// System.err.println("CPE_LIBRARY PUSHING: " +
					// e.getPath());
					listRefLibraries.add(e.getPath());
				} else if ("org.eclipse.pde.core.requiredPlugins".equals(e.getPath().toString())) {
					final IClasspathContainer cpContainer = JavaCore.getClasspathContainer(e.getPath(), project);
					for (final IClasspathEntry cpEntry : cpContainer.getClasspathEntries()) {
						if (cpEntry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {
							final IProject p = ResourcesPlugin.getWorkspace().getRoot()
									.getProject(cpEntry.getPath().lastSegment());
							if (p.exists()) {
								resolveDataProject(JavaCore.create(p), outputPath, listRefLibraries);
							}
						} else if (cpEntry.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
							// System.err.println("requiredPlugins & CPE_LIBRARY
							// PUSHING: " + e.getPath());
							listRefLibraries.add(cpEntry.getPath());
						}
					}
				} else if (e.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
					if (!e.getPath().toString().startsWith("org.eclipse.jdt.launching.JRE_CONTAINER")
							&& !e.getPath().toString().startsWith("org.eclipse.fx.ide.jdt.core.JAVAFX_CONTAINER")) {
						// System.err.println("====> A container");

						final IClasspathContainer cp = JavaCore.getClasspathContainer(e.getPath(), project);
						for (final IClasspathEntry ce : cp.getClasspathEntries()) {
							// System.err.println(ce.getEntryKind() + "=> " +
							// ce);
							if (ce.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
								listRefLibraries.add(ce.getPath());
							} else if (ce.getEntryKind() == IClasspathEntry.CPE_PROJECT) {
								final IProject p = ResourcesPlugin.getWorkspace().getRoot()
										.getProject(ce.getPath().lastSegment());
								if (p.exists()) {
									resolveDataProject(JavaCore.create(p), outputPath, listRefLibraries);
								}
							}
						}
					}
				}
			}
		} catch (final JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.err.println("END RESOLVE");
	}

	static class AploreURLClassloader extends URLClassLoader {

		public AploreURLClassloader(final URL[] urls, final ClassLoader parent) {
			super(urls, parent);
		}

		@Override
		public Class<?> loadClass(final String name) throws ClassNotFoundException {
			return super.loadClass(name);
		}
	}

}
