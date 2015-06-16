var ARReportApp = angular.module('ar-report', []);

ARReportApp.factory('Utils', [function(){
	return {
		startsIgnoreCase: function(string, match) {
			return string.toLowerCase().indexOf(match.toLowerCase()) == 0;
		}
	}
}]);

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
	
	var _calcSecs = function (object) {
		return (object.finishedAt - object.startedAt) / 1000;
	}
	
	var _setDurations = function(playbook) {
		playbook.duration = _calcSecs(playbook);
		$j.each(playbook.plays, function(idx, play) {
			play.duration = _calcSecs(play);
			$j.each(play.tasks, function(idx, task) {
				task.duration = _calcSecs(task);
			})
		})
	}
	
	var _incrementStatus = function(statuses, status) {
		if (!statuses[status]) {
			statuses[status] = 0;
		}
		statuses[status]++;
	}
	
	var _propagateStatus = function(playbook) {
		playbook.hostStatus = {};
		$j.each(playbook.plays, function(idx, play) {
			play.hostStatus = {};
			$j.each(play.tasks, function(idx, task) {
				task.hostStatus = {};
				$j.each(task.hosts, function(idx, host) {
					_incrementStatus(task.hostStatus, host.status);
					_incrementStatus(play.hostStatus, host.status);
					_incrementStatus(playbook.hostStatus, host.status);
				})
			})
		})
	}
	
	var _wrap = function(data) {
		$j.each(data.playbooks, function(idx, playbook) {
			playbook.totalRecap = _getTotalRecap(playbook);
			_setDurations(playbook);
			_propagateStatus(playbook);
		});
		data.allRecap = _allRecap;
	}
	return {
		wrap: _wrap
	}
}])

ARReportApp.controller('arRootCtrl', ['$scope','$http', 'PlaybookWrapperService', 'Utils', function($scope, $http, dataWrapper, utils) {
	$scope.loaded = false;
	$scope.groupByView = 'tasks';
	$scope.search = {
		task: "",
		host: "",
		status: "",
		showFacts: false
	}
	var url = "/httpAuth/app/rest/builds/id:"
		+ AnsibleRunReport.buildId
		+ "/artifacts/content/.teamcity/ansible-run/ansible-run-report.json";
	$http.get(url)
      .success(function(data, status, headers, config) {
    	  dataWrapper.wrap(data);
    	  $scope.allRecap = data.allRecap;
    	  if (data.allRecap.failed > 0 || data.allRecap.unreachable > 0) {
    		  $scope.search.showFacts = true;
    	  }
          $scope.playbooks = data.playbooks;
          $scope.loaded = true;
      })
      .error(function(data, status, headers, config) {
              $scope.alerts.push(data);
      });
	$scope.expandState = 'collapsed';
	$scope.toggleExpand = function() {
		$scope.expandState = $scope.expandState === 'expanded' ? 'collapsed' : 'expanded';
		$scope.$broadcast('expand-toggle', $scope.expandState);
	}
	$scope.taskSearch = function(task, idx) {
		var result = true;
		if ($scope.search.task) {
			result = utils.startsIgnoreCase(task.name, $scope.search.task);
		}
		if (result && $scope.search.status) {
			result = task.hostStatus[$scope.search.status] > 0;
		}
		return result;
	}
	$scope.hostSearch = function(host, idx) {
		var result = true;
		if ($scope.search.host) {
			result = utils.startsIgnoreCase(host.hostName, $scope.search.host);
		}
		if (result && $scope.search.status) {
			result = host.status == $scope.search.status;
		}
		return result;
	}
}]);

ARReportApp.controller('arPlayCtrl', ['$scope', function($scope) {
	$scope.isSkipped = function(play) {
		return play.tasks.length == 0;
	}
	$scope.toggle = function() {
		$scope.expandState = $scope.expandState === 'expanded' ? 'collapsed' : 'expanded';
	}
	$scope.$on('expand-toggle', function(event, newState) {
		$scope.expandState = newState;
	});
	
}]);

ARReportApp.controller('arExpandCollapseCtrl', ['$scope', function($scope) {
	$scope.expandState = 'expanded';
	$scope.toggle = function() {
		$scope.expandState = $scope.expandState === 'expanded' ? 'collapsed' : 'expanded';
	}
	$scope.$on('expand-toggle', function(event, newState) {
		$scope.expandState = newState;
	});
}]);

ARReportApp.controller('arTaskCtrl', ['$scope', function($scope) {
	$scope.expandState = $scope.task.hostStatus.failed > 0 || $scope.task.hostStatus.fatal > 0 ? 'expanded' : 'collapsed';
	$scope.toggle = function() {
		$scope.expandState = $scope.expandState === 'expanded' ? 'collapsed' : 'expanded';
	}
	$scope.$on('expand-toggle', function(event, newState) {
		$scope.expandState = newState;
	});
	$scope.showHostDetails = function(host) {
		var msg = [];
		msg.push(host.status);
		msg.push(': [');
		msg.push(host.hostName);
		msg.push('] ');
		msg.push();
		msg.push(host.result);
		alert(msg.concat(host.details.join('\n')).join(''));
	}
}]);

