<jsp:useBean id="buildData" type="jetbrains.buildServer.serverSide.SBuild" scope="request"/>
<link href="/plugins/ansible-runner/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var AnsibleRunReport = {
	buildId: '${buildData.buildId}'
};
</script>
<script type="text/javascript" src="/plugins/ansible-runner/js/angular.min.js"></script>
<script type="text/javascript" src="/plugins/ansible-runner/js/angular-app.js"></script>
<div data-ng-app="ar-report" data-ng-include="'/plugins/ansible-runner/html/report-ng.tpl'">
</div>