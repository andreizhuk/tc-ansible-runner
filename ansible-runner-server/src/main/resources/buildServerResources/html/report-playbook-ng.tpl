<div data-ng-repeat="play in pb.plays" data-ng-controller="arPlayCtrl" class="ar-play-container">
  <div class="ar-summary">
      <span title="Click to show/hide taks" class="handle handle_{{ expandState }}" ng-click="toggle()"></span>
      <span class="ar-name">PLAY [{{ play.name }}]</span>
  </div>
  <div ng-show="expandState === 'expanded'" class="ar-nested-level">
    <div data-ng-if="play.tasks.length == 0" class="ar-skipping">Skpped play</div>
    <div data-ng-if="play.tasks.length > 0" data-ng-repeat="task in filteredTasks = (play.tasks | filter:taskSearch)" data-ng-controller="arTaskCtrl">
            <span title="Click to show/hide hosts" class="handle handle_{{ expandState }}" ng-click="toggle()"></span>
            <span>{{ task.name }}</span>
            <span>{{ task.duration }}</span>
            <span>{{ task.order }}</span>
        <ul class="ar-host" ng-show="expandState === 'expanded'">
            <li class="ar-{{ host.status }}" data-ng-repeat="host in filteredHosts = (task.hosts | filter:hostSearch)">
                <span>{{ host.status }}: [{{ host.hostName }}] {{ host.result }}</span>
                <a href="" data-ng-click="showHostDetails(host)" data-ng-if="host.details.length > 0" title="Click to see more">...</a>
             </li>
             <li data-ng-if="filteredHosts.length < 1">No matching hosts</li>
        </ul>
    </div>
    <div data-ng-if="filteredTasks.length < 1 && play.tasks.length > 0">No matching tasks</div>
  </div>
</div>
