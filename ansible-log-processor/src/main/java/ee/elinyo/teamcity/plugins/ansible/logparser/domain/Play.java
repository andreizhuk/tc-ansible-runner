package ee.elinyo.teamcity.plugins.ansible.logparser.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Play {
    
    private static final Pattern PLAY_FIRST_LINE_PATTERN = Pattern.compile("PLAY\\s+\\Q[\\E(.+)\\Q]\\E");

    private String name;
    private long startedAt;
    private long finishedAt;
    private boolean skipped;
    private List<Task> tasks = new ArrayList<Task>();
    private List<HostResult> facts = new ArrayList<HostResult>();
    
    public static Play fromOutputLine(String line, long startedAt) {
        Play p = null;
        Matcher matcher = PLAY_FIRST_LINE_PATTERN.matcher(line);
        if (matcher.find()) {
            p = new Play();
            p.setName(matcher.group(1));
        }
        return p;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<HostResult> getFacts() {
        return facts;
    }

    public void setFacts(List<HostResult> facts) {
        this.facts = facts;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

}
