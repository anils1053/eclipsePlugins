{
	"name": "${moduleName}",
	"builds": {
		"${moduleName}": {
			"prependfiles": [
				"../../.copyright.js"
			],
			"jsfiles": [
				"${moduleName}.js"
			]
		}
	},
	"shifter": {
		"strict": true
	}
}