var AnsibleRunReport = (function() {

	var _load = function(buildId) {
		var url = "/httpAuth/app/rest/builds/id:"
				+ buildId
				+ "/artifacts/content/.teamcity/ansible-run/ansible-run-report.json";
		$j.getJSON(url, _generateReport);
	}

	var _generateReport = function(data) {
		$j.get('/plugins/ansible-runner/html/report.mst', function(template) {
			var rendered = Mustache.render(template, data);
			$j('#ar_results_container').html(rendered);
		});
	}

	return {
		generate : _load
	}
})();