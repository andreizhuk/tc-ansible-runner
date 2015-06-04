package ee.elinyo.teamcity.plugins.ansible.logparser.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task {
    
    private static final Pattern TASK_FIRST_LINE_PATTERN = Pattern
            .compile("^(TASK|NOTIFIED):\\s+\\Q[\\E(.+)\\Q]\\E");

    private long startedAt;
    private long finishedAt;
    private String name;
    private boolean notified;
    private int order;
    private List<HostResult> hosts = new ArrayList<HostResult>();
    private List<VerboseMessage> verbose = new ArrayList<VerboseMessage>();

    public static Task fromOutputLine(String line, long startedAt) {
        Matcher matcher = TASK_FIRST_LINE_PATTERN.matcher(line);
        Task t = null;
        if (matcher.find()) {
            t = new Task();
            t.setNotified("NOTIFIED".equals(matcher.group(1)));
            t.startedAt = startedAt;
            t.setName(matcher.group(2));
        }
        return t;
    }
    
    public long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }

    public long getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(long finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public List<HostResult> getHosts() {
        return hosts;
    }

    public void setHosts(List<HostResult> hosts) {
        this.hosts = hosts;
    }

    public List<VerboseMessage> getVerbose() {
        return verbose;
    }

    public void setVerbose(List<VerboseMessage> verbose) {
        this.verbose = verbose;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
