<div data-ng-controller="arRootCtrl">
<div data-ng-hide="loaded">
    <span class="icon-refresh icon-spin"></span>&nbsp;Loading Ansible Run report...
</div>
<div data-ng-show="loaded">
    <div>Total Recap: ok={{ allRecap.ok }}, changed={{ allRecap.changed }}, unreachable={{ allRecap.unreachable }}, failed={{ allRecap.failed }}</div>
    <div class="actionBar">
        <span class="actionBarRight">Expand/Collapse</span>
        <span class="fields">
            <span class="nowrap">
                <label for="taskFilter">Task:&nbsp;</label>
                <input value="" id="taskFilter" name="taskFilter" class="actionInput" />
            </span>
            <span class="nowrap">
                <label for="hostFilter">Host:&nbsp;</label>
                <input value="" id="hostFilter" name="hostFilter" class="actionInput" />
            </span>
            <span class="nowrap">
                <label for="statusFilter">Status:&nbsp;</label>
                <select value="" id="statusFilter" name="statusFilter" class="actionInput">
                    <option value="" selected="">all</option>
                    <option value="changed">changed</option>
                    <option value="failed">failed</option>
                    <option value="fatal">fatal</option>
                    <option value="ok">ok</option>
                    <option value="skipping">skipping</option>
                </select>
            </span>
        </span>
    </div>
    <div data-ng-repeat="pb in playbooks">
        <h1>Step: ok={{ pb.totalRecap.ok }}, changed={{ pb.totalRecap.changed }}, unreachable={{ pb.totalRecap.unreachable }}, failed={{ pb.totalRecap.failed }}</h1>
        <div data-ng-repeat="play in pb.plays">
            <h2>{{ play.name }}</h2>
            <div data-ng-repeat="task in play.tasks">
                <h3>{{ task.name }}</h3>
            </div>
        </div>
    </div>
</div>
</div>