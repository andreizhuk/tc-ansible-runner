<jsp:useBean id="buildData" type="jetbrains.buildServer.serverSide.SBuild" scope="request"/>
<div id="ar_results_container">Loading Ansible Run report...</div>
<script type="text/javascript" src="/plugins/ansible-runner/js/mustache.min.js"></script>
<script type="text/javascript" src="/plugins/ansible-runner/js/app.js"></script>
<script type="text/javascript">
//AR.load("/httpAuth/app/rest/builds/id:${buildData.buildId}/artifacts/content/.teamcity/ansible-run/ansible-run-report.json");
AnsibleRunReport.generate('${buildData.buildId}');
</script>
