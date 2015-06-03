package ee.elinyo.teamcity.plugins.ansible.logparser;


public final class AnsibleLogUtils {
    
    private static final String PLAY_START = "PLAY [";
    private static final String TASK_START = "TASK:";
    private static final String NOTIFIED_START = "NOTIFIED:";
    private static final String GATHER_FACT_START = "GATHERING FACTS";
    private static final String FATAL_START = "FATAL:";
    private static final String RECAP_START = "PLAY RECAP";
    private static final String BUILD_META_START = "AR_BUILD_META:";
    
    public static boolean isPlayStart(String line) {
        return line.startsWith(PLAY_START);
    }
    
    public static boolean isPlaySkip(String line) {
        return "skipping: no hosts matched".equals(line);
    }
    
    public static boolean isTaskStart(String line) {
        return line.startsWith(TASK_START);
    }
    
    public static boolean isNotified(String line) {
        return line.startsWith(NOTIFIED_START);
    }
    
    public static boolean isGatheringFactStart(String line) {
        return line.startsWith(GATHER_FACT_START);
    }
    
    public static boolean isRecapStart(String line) {
        return line.startsWith(RECAP_START);
    }
    
    public static boolean isVerboseMsg(String line) {
        return line.startsWith("<");
    }
    
    public static boolean isFatal(String line) {
        return line.startsWith(FATAL_START);
    }
    
    public static boolean isBuildMeta(String line) {
        return line.startsWith(BUILD_META_START);
    }
    
    public static String getBuildMetaMessage(String key, String value) {
        return BUILD_META_START + key + ":" + value;
    }

}
