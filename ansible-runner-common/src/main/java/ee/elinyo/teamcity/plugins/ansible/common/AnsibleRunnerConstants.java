package ee.elinyo.teamcity.plugins.ansible.common;

public class AnsibleRunnerConstants {
    
    public static final String RUN_TYPE = "elinyo_ansible_runner";
    
    public static final String COMMAND_TYPE_KEY = "elinyo_ar_command";
    
    public static final String SOURCE_CODE_KEY = "elinyo_ar_source_code"; 
    
    public static final String PLAYBOOK_FILE_KEY = "elinyo_ar_playbook_file";
    
    public static final String INVENTORY_FILE_KEY = "elinyo_ar_inventory_file";
    
    public static final String EXECUTABLE_KEY = "elinyo_ar_exe";
    
    public static final String OPTIONS_KEY = "elinyo_ar_options";
    
    public static final String ARTIFACTS_TMP_DIR_KEY = "elinyo_ar_artifacts_tmp";
    
    public static final String ARTIFACTS_BASE_DIR = ".teamcity/ansible-run";
    
    public static final String ARTIFACTS_JSON_REPORT = "ansible-run-report.json";
    
    public static final String RUNNER_ID_META_KEY = "tcRunnerId";
    
    public static final String STEP_NAME_META_KEY = "tcStepName";
    
    public static final String EXIT_CODE_META_KEY = "tcExitCode";

}
