package ee.elinyo.teamcity.plugins.ansible.logparser.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HostRecap {
    
    private static final Pattern HOST_RECAP_LINE_PATTERN = Pattern
            .compile("^([^:\\s]+)\\s*:\\s+ok=(\\d+)\\s+changed=(\\d+)\\s+unreachable=(\\d+)\\s+failed=(\\d+).*");

    private String hostname;
    private Map<String, Integer> statusSummary = new HashMap<String, Integer>();
    
    public static HostRecap fromOutputLine(String line) {
        Matcher matcher = HOST_RECAP_LINE_PATTERN.matcher(line);
        HostRecap hr = null;
        if (matcher.find()) {
            hr = new HostRecap();
            hr.setHostname(matcher.group(1));
            hr.statusSummary.put("ok", Integer.valueOf(matcher.group(2)));
            hr.statusSummary.put("changed", Integer.valueOf(matcher.group(3)));
            hr.statusSummary.put("unreachable", Integer.valueOf(matcher.group(4)));
            hr.statusSummary.put("failed", Integer.valueOf(matcher.group(5)));
        }
        return hr;
    }
    
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Map<String, Integer> getStatusSummary() {
        return statusSummary;
    }

    public void setStatusSummary(Map<String, Integer> statusSummary) {
        this.statusSummary = statusSummary;
    }

}
