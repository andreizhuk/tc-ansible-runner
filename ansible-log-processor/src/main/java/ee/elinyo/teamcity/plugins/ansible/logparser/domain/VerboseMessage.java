package ee.elinyo.teamcity.plugins.ansible.logparser.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerboseMessage {
    
    private static Pattern VERBOSE_MESSAGE_LINE_PATTERN = Pattern
            .compile("^<(.+)>\\s+(.+)?");

    private String hostName;
    private String message;
    
    public static VerboseMessage fromOutputLine(String line) {
        Matcher matcher = VERBOSE_MESSAGE_LINE_PATTERN.matcher(line);
        VerboseMessage vm = null;
        if (matcher.find()) {
            vm = new VerboseMessage();
            vm.setHostName(matcher.group(1));
            vm.setMessage(matcher.group(2));
        }
        return vm;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
