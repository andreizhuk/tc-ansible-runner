var AnsibleRunReport = (function() {

	var _load = function(buildId) {
		var url = "/httpAuth/app/rest/builds/id:"
				+ buildId
				+ "/artifacts/content/.teamcity/ansible-run/ansible-run-report.json";
		$j.getJSON(url, _generateReport);
	}
	
	var _totalRecap = {
		ok: 0,
		failed: 0,
		changed: 0,
		unreachable: 0
	};
	
	var _getTotalRecap = function(playbook) {
		var playbookRecap = {
			ok: 0,
			failed: 0,
			changed: 0,
			unreachable: 0
		}
		$j.each(playbook.recaps, function(idx, recap) {
			playbookRecap.ok += recap.statusSummary.ok;
			_totalRecap.ok += recap.statusSummary.ok;
			playbookRecap.failed += recap.statusSummary.failed;
			_totalRecap.failed += recap.statusSummary.failed;
			playbookRecap.changed += recap.statusSummary.changed;
			_totalRecap.changed += recap.statusSummary.changed;
			playbookRecap.unreachable += recap.statusSummary.unreachable;
			_totalRecap.unreachable += recap.statusSummary.unreachable;
		});
		return playbookRecap;
	}
	
	var _prepareModel = function(data) {
		$j.each(data.playbooks, function(idx, playbook) {
			playbook.totalRecap = _getTotalRecap(playbook);
		});
		data.totalRecap = _totalRecap;
	}

	var _generateReport = function(data) {
		_prepareModel(data);
		$j.get('/plugins/ansible-runner/html/report.mst', function(template) {
			var rendered = Mustache.render(template, data);
			$j('#ar_results_container').html(rendered);
		});
	}

	return {
		generate : _load
	}
})();