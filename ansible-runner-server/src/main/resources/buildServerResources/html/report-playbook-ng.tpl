<div data-ng-controller="arExpandCollapseCtrl">
    <div class="ar-summary ar-failed" data-ng-if="pb.errorMessage">
        {{ pb.errorMessage }}
    </div>
    <div class="ar-summary" data-ng-if="pb.recaps.length > 0">
        <span title="Click to show/hide hosts" class="handle handle_{{ expandState }}" ng-click="toggle()"></span>
        <span class="ar-name">PLAY RECAP</span>
    </div>
    <div ng-show="expandState === 'expanded'" class="ar-nested-level ar-recap-container">
        <div class="ar-summary" data-ng-repeat="host in pb.recaps">
            <span>{{ host.hostName }}</span>:
            <span ng-class="{'ar-ok': host.statusSummary.ok > 0}">ok={{ host.statusSummary.ok || 0 }}</span>
            <span ng-class="{'ar-changed': host.statusSummary.changed > 0}">changed={{ host.statusSummary.changed || 0 }}</span>
            <span ng-class="{'ar-failed': host.statusSummary.unreachable > 0}">unreachable={{ host.statusSummary.unreachable || 0 }}</span>
            <span ng-class="{'ar-failed': host.statusSummary.failed > 0}">failed={{ host.statusSummary.failed || 0 }}</span>
        </div>
    </div>
</div>
<div data-ng-repeat="play in pb.plays" data-ng-controller="arPlayCtrl" class="ar-play-container">
  <div class="ar-summary">
      <span title="Click to show/hide tasks" class="handle handle_{{ expandState }}" ng-click="toggle()"></span>
      <span class="ar-name">PLAY [{{ play.name }}]</span>
      <span class="ar-summary-col">{{ play.duration }}s</span>
      <span class="ar-summary-col" ng-class="{'ar-failed': play.hostStatus.failed > 0}">failed={{ play.hostStatus.failed || 0 }}</span>
      <span class="ar-summary-col" ng-class="{'ar-skipping': play.hostStatus.skipping > 0}">skipped={{ play.hostStatus.skipping || 0 }}</span>
      <span class="ar-summary-col" ng-class="{'ar-changed': play.hostStatus.changed > 0}">changed={{ play.hostStatus.changed || 0 }}</span>
      <span class="ar-summary-col" ng-class="{'ar-ok': play.hostStatus.ok > 0}">ok={{ play.hostStatus.ok || 0 }}</span>
  </div>
  <div ng-show="expandState === 'expanded'" class="ar-nested-level">
    <div data-ng-if="isSkipped(play)" class="ar-skipping">Skpped play</div>
    <div class="ar-task-container" data-ng-if="!isSkipped(play) && search.showFacts" data-ng-controller="arExpandCollapseCtrl">
        <div class="ar-summary">
            <span title="Click to show/hide facts" class="handle handle_{{ expandState }}" ng-click="toggle()"></span>
            <span class="ar-name">GATHERING FACTS</span>
        </div>
        <ul ng-show="expandState === 'expanded'">
            <li class="ar-{{ host.status }}" data-ng-repeat="host in filteredHosts = (play.facts | filter:hostSearch)">
                <span>{{ host.status }}: [{{ host.hostName }}] {{ host.result }}</span>
             </li>
             <li data-ng-if="filteredHosts.length < 1" class="ar-no-match">No hosts matching your filter</li>
        </ul>
    </div>
    <div class="ar-task-container" data-ng-if="!isSkipped(play)" data-ng-repeat="task in filteredTasks = (play.tasks | filter:taskSearch)" data-ng-controller="arTaskCtrl">
        <div class="ar-summary">
            <span title="Click to show/hide hosts" class="handle handle_{{ expandState }}" ng-click="toggle()"></span>
            <span class="ar-name">{{ task.notified ? 'NOTIFIED' : 'TASK' }} [{{ task.name }}]</span>
            <span class="ar-summary-col">{{ task.duration }}s</span>
            <span class="ar-summary-col" ng-class="{'ar-failed': task.hostStatus.failed > 0}">failed={{ task.hostStatus.failed || 0 }}</span>
            <span class="ar-summary-col" ng-class="{'ar-skipping': task.hostStatus.skipping > 0}">skipped={{ task.hostStatus.skipping || 0 }}</span>
            <span class="ar-summary-col" ng-class="{'ar-changed': task.hostStatus.changed > 0}">changed={{ task.hostStatus.changed || 0 }}</span>
            <span class="ar-summary-col" ng-class="{'ar-ok': task.hostStatus.ok > 0}">ok={{ task.hostStatus.ok || 0 }}</span>
        </div>
        <ul ng-show="expandState === 'expanded'">
            <li class="ar-{{ host.status }}" data-ng-repeat="host in filteredHosts = (task.hosts | filter:hostSearch)">
                <span>{{ host.status }}: [{{ host.hostName }}] {{ host.result }}</span>
                <a href="" data-ng-click="showHostDetails(host)" data-ng-if="host.details.length > 0" title="Click to see more">...</a>
             </li>
             <li data-ng-if="filteredHosts.length < 1" class="ar-no-match">No hosts matching your filter</li>
        </ul>
     </div>
    <div data-ng-if="filteredTasks.length < 1 && play.tasks.length > 0" class="ar-no-match">No tasks matching your filter</div>
  </div>
</div>
