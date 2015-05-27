<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="bean" class="ee.elinyo.teamcity.plugins.ansible.server.AnsibleRunBean"/>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>

<forms:workingDirectory/>

<tr>
  <th><label for="${bean.commandTypeKey}">Run:</label></th>
  <td>
    <props:selectProperty name="${bean.commandTypeKey}" id="ar_command_type_option" className="mediumField" onchange="BS.AnsibleRun.updatCommandType()">
      <props:option value="${bean.playbookCommandValue}">Playbook</props:option>
      <props:option value="${bean.customCommandValue}">Custom</props:option>
    </props:selectProperty>
    <span class="smallNote">You can use custom when ansible is executed via wrapper scripts</span>
  </td>
</tr>

<tr id="ar_sourceCode">
  <th><label for="${bean.scriptCodeKey}">Script source:</label></th>
  <td>
    <props:multilineProperty name="${bean.scriptCodeKey}"
                             linkTitle="Enter script content"
                             cols="58" rows="10"
                             expanded="${true}" />
     <span class="smallNote">The script should print ansible output to the standard out</span>
  </td>
</tr>

<tr id="ar_playbook_file">
  <th><label for="${bean.playbookFileKey}">Playbook File:</label></th>
  <td>
    <props:textProperty name="${bean.playbookFileKey}" className="longField"/>
    <bs:vcsTree fieldId="${bean.playbookFileKey}"/>
    <span class="smallNote">Path to the playbook file, relative to the checkout directory</span>
  </td>
</tr>

<script type="text/javascript">
  BS.AnsibleRun = {
    updatCommandType : function() {
      var val = $('ar_command_type_option').value;
      if (val == '${bean.playbookCommandValue}') {
        BS.Util.hide($('ar_sourceCode'));
        BS.Util.show($('ar_playbook_file'));
      }
      if (val == '${bean.customCommandValue}') {
        BS.Util.show($('ar_sourceCode'));
        BS.Util.hide($('ar_playbook_file'));
      }
      BS.MultilineProperties.updateVisible();
    },

  };

  BS.AnsibleRun.updatCommandType();
</script>