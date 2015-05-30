package ee.elinyo.teamcity.plugins.ansible.common;

public enum AnsibleCommand {
    
    EXECUTABLE, CUSTOM_SCRIPT;
    
    public String getValue() {
        return this.name();
    }

}
