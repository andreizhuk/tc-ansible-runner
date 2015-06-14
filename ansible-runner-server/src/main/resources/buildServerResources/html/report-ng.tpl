<div data-ng-controller="arRootCtrl" class="ar-report">
<div data-ng-hide="loaded">
    <span class="icon-refresh icon-spin"></span>&nbsp;Loading Ansible Run report...
</div>
<div data-ng-show="loaded">
    <div class="actionBar">
        <span class="actionBarRight">
            <span class="nowrap">
                <a data-ng-click="toggleExpand()" href="">
                    {{ expandState === 'expanded' ? 'Collapse' : 'Expand' }} All
                </a>
            </span>
        </span>
        <span class="fields">
            <span class="nowrap">
                <label for="taskFilter">Task:&nbsp;</label>
                <input value="" id="taskFilter" name="taskFilter" class="actionInput" data-ng-model="search.task"/>
            </span>
            <span class="nowrap">
                <label for="hostFilter">Host:&nbsp;</label>
                <input value="" id="hostFilter" name="hostFilter" class="actionInput" data-ng-model="search.host"/>
            </span>
            <span class="nowrap">
                <label for="statusFilter">Status:&nbsp;</label>
                <select id="statusFilter" name="statusFilter" class="actionInput" data-ng-model="search.status">
                    <option value="">all</option>
                    <option value="changed">changed</option>
                    <option value="failed">failed</option>
                    <option value="fatal">fatal</option>
                    <option value="ok">ok</option>
                    <option value="skipping">skipping</option>
                </select>
            </span>
        </span>
    </div>
    <div class="ar-all-recap">Total Recap: ok={{ allRecap.ok }}, changed={{ allRecap.changed }}, unreachable={{ allRecap.unreachable }}, failed={{ allRecap.failed }}</div>
    <div data-ng-repeat="pb in playbooks" class="ar-step-container" data-ng-controller="arRunnerStepCtrl">
        <div class="ar-summary">
            <span title="Click to show/hide plays" class="handle handle_{{ expandState }}" ng-click="toggle()">&nbsp;</span>
            <span class="ar-name">{{ pb.buildMeta.tcStepName }}</span>
            <span class="ar-summary-col">{{ pb.duration }}s</span>
        </div>
        <div class="ar-nested-level" data-ng-include="'/plugins/ansible-runner/html/report-playbook-ng.tpl'" ng-show="expandState === 'expanded'"></div>
    </div>
</div>
</div>