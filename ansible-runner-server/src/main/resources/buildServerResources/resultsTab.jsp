<jsp:useBean id="buildData" type="jetbrains.buildServer.serverSide.SBuild" scope="request"/>
<script type="text/javascript">
var AnsibleRunReport = {
	buildId: '${buildData.buildId}'
};
</script>
<div data-ng-app="ar-report" data-ng-include="'/plugins/ansible-runner/html/report-ng.tpl'">
</div>