package ee.elinyo.teamcity.plugins.ansible.common;

import java.util.HashMap;
import java.util.Map;

public class AnsibleRunConfig {
    
    private final Map<String, String> properties;

    public AnsibleRunConfig(Map<String, String> properties) {
        this.properties = new HashMap<String, String>(properties);
    }
    
    public AnsibleCommand getCommandType() {
        String value = properties.get(AnsibleRunnerConstants.COMMAND_TYPE_KEY);
        return value == null ? null : AnsibleCommand.valueOf(value);
    }
    
    public String getPlaybook() {
        return properties.get(AnsibleRunnerConstants.PLAYBOOK_FILE_KEY);
    }
    
    public String getInventory() {
        return properties.get(AnsibleRunnerConstants.INVENTORY_FILE_KEY);
    }
    
    public String getExecutable() {
        return properties.get(AnsibleRunnerConstants.EXECUTABLE_KEY).trim();
    }
    
    public String getOptions() {
        return properties.get(AnsibleRunnerConstants.OPTIONS_KEY);
    }
    
    public String getSourceCode() {
        return properties.get(AnsibleRunnerConstants.SOURCE_CODE_KEY);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[\n");
        sb.append("commandType: ").append(getCommandType());
        sb.append("executable: ").append(getExecutable());
        sb.append("playbook: ").append(getPlaybook());
        sb.append("invetory: ").append(getInventory());
        sb.append("options: ").append(getOptions());
        sb.append("sourceCode: ").append(getSourceCode());
        sb.append("\n]");
        return sb.toString();
    }

}
