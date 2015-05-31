package ee.elinyo.teamcity.plugins.ansible.logparser.domain;

import java.util.ArrayList;
import java.util.List;

public class Playbook {

    private long startedAt;
    private long finishedAt;
    private List<Play> plays = new ArrayList<Play>();
    private List<HostRecap> recaps = new ArrayList<HostRecap>();
    private String fatalMessage;

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

    public String getFatalMessage() {
        return fatalMessage;
    }

    public void setFatalMessage(String fatalMessage) {
        this.fatalMessage = fatalMessage;
    }

}
