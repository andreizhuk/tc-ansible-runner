package ee.elinyo.teamcity.plugins.ansible.server;

import java.util.Arrays;
import java.util.Collection;

import ee.elinyo.teamcity.plugins.ansible.common.AnsibleCommand;
import ee.elinyo.teamcity.plugins.ansible.common.AnsibleRunnerConstants;

public class AnsibleRunBean {
    
    public String getCommandTypeKey() {
        return AnsibleRunnerConstants.COMMAND_TYPE_KEY;
    }
    
    public Collection<AnsibleCommand> getCommandTypeValues() {
        return Arrays.asList(AnsibleCommand.values());
    }
    
    public String getCustomCommandValue() {
        return AnsibleCommand.CUSTOM.name();
    }
    
    public String getPlaybookCommandValue() {
        return AnsibleCommand.PLAYBOOK.name();
    }
    
    public String getScriptCodeKey() {
        return AnsibleRunnerConstants.SOURCE_CODE_KEY;
    }
    
    public String getPlaybookFileKey() {
        return AnsibleRunnerConstants.PLAYBOOK_FILE_KEY;
    }
    
    public static AnsibleRunBean getInstance() {
        return new AnsibleRunBean();
    }

}
