<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="bean" class="ee.elinyo.teamcity.plugins.ansible.server.AnsibleRunConfigBean"/>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>

<forms:workingDirectory/>

<tr>
  <th><label for="${bean.commandTypeKey}">Run:</label></th>
  <td>
    <props:selectProperty name="${bean.commandTypeKey}" id="ar_command_type_option" className="mediumField" onchange="BS.AnsibleRun.updatCommandType()">
      <props:option value="${bean.executableCommandValue}">Executable</props:option>
      <props:option value="${bean.customScriptCommandValue}">Custom Script</props:option>
    </props:selectProperty>
    <span class="smallNote">You can use custom when ansible is executed via wrapper scripts</span>
  </td>
</tr>

<tr id="ar_executable">
  <th><label for="${bean.executableKey}">Command:</label></th>
  <td>
    <props:textProperty name="${bean.executableKey}" className="longField"/>
    <span class="smallNote">Absolute path to custom executable if not available on path</span>
  </td>
</tr>

<tr id="ar_playbook_file">
  <th><label for="${bean.playbookFileKey}">Playbook:</label></th>
  <td>
    <props:textProperty name="${bean.playbookFileKey}" className="longField"/>
    <bs:vcsTree fieldId="${bean.playbookFileKey}"/>
    <span class="smallNote">Path to the playbook file, absolute or relative to the checkout directory</span>
  </td>
</tr>

<tr id="ar_inventory_file">
  <th><label for="${bean.inventoryFileKey}">Inventory:</label></th>
  <td>
    <props:textProperty name="${bean.inventoryFileKey}" className="longField"/>
    <bs:vcsTree fieldId="${bean.inventoryFileKey}"/>
    <span class="smallNote">Path to the inventory file, absolute or relative to the checkout directory</span>
  </td>
</tr>

<tr id="ar_options">
  <th><label for="${bean.optionsKey}">Options:</label></th>
  <td>
     <props:textProperty name="${bean.optionsKey}" className="longField"/>
     <span class="smallNote">Command line options: inventory, user, etc.</span>
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

<script type="text/javascript">
  BS.AnsibleRun = {
    updatCommandType : function() {
      var val = $('ar_command_type_option').value;
      if (val == '${bean.executableCommandValue}') {
        BS.Util.hide($('ar_sourceCode'));
        BS.Util.show($('ar_playbook_file'));
        BS.Util.show($('ar_inventory_file'));
        BS.Util.show($('ar_executable'));
        BS.Util.show($('ar_options'));
      }
      if (val == '${bean.customScriptCommandValue}') {
        BS.Util.hide($('ar_playbook_file'));
        BS.Util.hide($('ar_inventory_file'));
        BS.Util.hide($('ar_executable'));
        BS.Util.hide($('ar_options'));
        BS.Util.show($('ar_sourceCode'));
      }
      BS.MultilineProperties.updateVisible();
    },

  };

  BS.AnsibleRun.updatCommandType();
</script>