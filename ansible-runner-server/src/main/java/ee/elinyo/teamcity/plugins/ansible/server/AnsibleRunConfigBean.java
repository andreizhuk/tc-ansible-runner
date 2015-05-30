package ee.elinyo.teamcity.plugins.ansible.server;

import java.util.Arrays;
import java.util.Collection;

import ee.elinyo.teamcity.plugins.ansible.common.AnsibleCommand;
import ee.elinyo.teamcity.plugins.ansible.common.AnsibleRunnerConstants;

public class AnsibleRunConfigBean {
    
    public String getCommandTypeKey() {
        return AnsibleRunnerConstants.COMMAND_TYPE_KEY;
    }
    
    public Collection<AnsibleCommand> getCommandTypeValues() {
        return Arrays.asList(AnsibleCommand.values());
    }
    
    public String getCustomScriptCommandValue() {
        return AnsibleCommand.CUSTOM_SCRIPT.getValue();
    }
    
    public String getExecutableCommandValue() {
        return AnsibleCommand.EXECUTABLE.getValue();
    }
    
    public String getScriptCodeKey() {
        return AnsibleRunnerConstants.SOURCE_CODE_KEY;
    }
    
    public String getPlaybookFileKey() {
        return AnsibleRunnerConstants.PLAYBOOK_FILE_KEY;
    }
    
    public String getInventoryFileKey() {
        return AnsibleRunnerConstants.INVENTORY_FILE_KEY;
    }
    
    public String getExecutableKey() {
        return AnsibleRunnerConstants.EXECUTABLE_KEY;
    }
    
    public String getOptionsKey() {
        return AnsibleRunnerConstants.OPTIONS_KEY;
    }
    

}
