package ee.elinyo.teamcity.plugins.ansible.logparser.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HostResult {
    
    private static Pattern HOST_RESULT_FIRST_LINE_PATTERN = Pattern
            .compile("^([a-z]+):\\s+\\Q[\\E(.+)\\Q]\\E\\s*(.+)?");

    private String hostName;
    private String status;
    private String result;
    private List<String> details = new ArrayList<String>();
    
    public static HostResult fromOutputLine(String line) {
        Matcher matcher = HOST_RESULT_FIRST_LINE_PATTERN.matcher(line);
        HostResult hr = null;
        if (matcher.find()) {
            hr = new HostResult();
            hr.setStatus(matcher.group(1));
            hr.setHostName(matcher.group(2));
            if (matcher.groupCount() == 3) {
                hr.setResult(matcher.group(3));
            }
        }
        return hr;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

}
