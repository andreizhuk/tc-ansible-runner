package ee.elinyo.teamcity.plugins.ansible.logparser.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Playbook {
    
    private static final Pattern BUILD_META_LINE_PATTERN = Pattern.compile("AR_BUILD_META:(.+):(.+)");

    private long startedAt;
    private long finishedAt;
    private List<Play> plays = new ArrayList<Play>();
    private List<HostRecap> recaps = new ArrayList<HostRecap>();
    private String errorMessage;
    private Map<String, String> buildMeta = new HashMap<String, String>();

    public void addBuildMeta(String line) {
        Matcher matcher = BUILD_META_LINE_PATTERN.matcher(line);
        if (matcher.find()) {
            buildMeta.put(matcher.group(1), matcher.group(2));
        }
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

    public List<Play> getPlays() {
        return plays;
    }

    public void setPlays(List<Play> plays) {
        this.plays = plays;
    }

    public List<HostRecap> getRecaps() {
        return recaps;
    }

    public void setRecaps(List<HostRecap> recaps) {
        this.recaps = recaps;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, String> getBuildMeta() {
        return buildMeta;
    }

    public void setBuildMeta(Map<String, String> buildMeta) {
        this.buildMeta = buildMeta;
    }

}
