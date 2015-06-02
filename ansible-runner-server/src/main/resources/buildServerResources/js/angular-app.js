var ARReportApp = angular.module('ar-report', []);

ARReportApp.factory('PlaybookWrapperService',[function() {
	var _allRecap = {
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
			_allRecap.ok += recap.statusSummary.ok;
			playbookRecap.failed += recap.statusSummary.failed;
			_allRecap.failed += recap.statusSummary.failed;
			playbookRecap.changed += recap.statusSummary.changed;
			_allRecap.changed += recap.statusSummary.changed;
			playbookRecap.unreachable += recap.statusSummary.unreachable;
			_allRecap.unreachable += recap.statusSummary.unreachable;
		});
		return playbookRecap;
	}
	var _wrap = function(data) {
		$j.each(data.playbooks, function(idx, playbook) {
			playbook.totalRecap = _getTotalRecap(playbook);
		});
		data.allRecap = _allRecap;
	}
	return {
		wrap: _wrap
	}
}])

ARReportApp.controller('arRootCtrl', ['$scope','$http', 'PlaybookWrapperService', function($scope, $http, dataWrapper){
	$scope.loaded = false;
	var url = "/httpAuth/app/rest/builds/id:"
		+ AnsibleRunReport.buildId
		+ "/artifacts/content/.teamcity/ansible-run/ansible-run-report.json";
	$http.get(url)
      .success(function(data, status, headers, config) {
    	  dataWrapper.wrap(data);
    	  $scope.allRecap = data.allRecap;
          $scope.playbooks = data.playbooks;
          $scope.loaded = true;
      })
      .error(function(data, status, headers, config) {
              $scope.alerts.push(data);
      });
}]);
