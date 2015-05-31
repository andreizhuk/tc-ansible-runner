package ee.elinyo.teamcity.plugins.ansible.logparser;

import ee.elinyo.teamcity.plugins.ansible.logparser.domain.Play;
import ee.elinyo.teamcity.plugins.ansible.logparser.domain.Playbook;
import ee.elinyo.teamcity.plugins.ansible.logparser.domain.Task;
import ee.elinyo.teamcity.plugins.utils.Pair;

public class AnsibleOutputProcessor {
    
    private Playbook playbook;
    
    private LogProcessingContext currentContext;
    
    private Play currentPlay;
    private Task currentTask;
    
    public void onLine(String line) {
        if (!isIgnore(line)) {
            Pair<Long, String> undecorated = DateDecorator.undecorate(line);
            if (undecorated != null && !isIgnore(undecorated.getRight())) {
                process(undecorated.getLeft(), undecorated.getRight());
            }
        }
    }
    
    private void process(long date, String line) {
        if (playbook == null) {
            playbook = new Playbook();
            playbook.setStartedAt(date);
        }
        
        if (AnsibleLogUtils.isTaskStart(line) || AnsibleLogUtils.isNotified(line)) {
            nextTask(line, date);
            currentContext = new TaskContext(currentTask, currentPlay);
        } else if (AnsibleLogUtils.isPlayStart(line)) {
            nextPlay(line, date);
        } else if (AnsibleLogUtils.isGatheringFactStart(line)) {
            currentContext = new GatheringFactsContext(currentPlay);
        } else if (AnsibleLogUtils.isRecapStart(line)) {
            recap(date);
            currentContext = new RecapContext(playbook);
        } else if (AnsibleLogUtils.isFatal(line)) {
            playbook.setFatalMessage(line);
        } else if (AnsibleLogUtils.isPlaySkip(line)) {
            currentPlay.setSkipped(true);
        } else if (currentContext != null) {
            currentContext.process(line);
        }
    }
    
    private void nextPlay(String line, long date) {
        Play newPlay = Play.fromOutputLine(line, date);
        if (currentPlay != null) {
            currentPlay.setFinishedAt(date);
        }
        if (currentTask != null) {
            currentTask.setFinishedAt(date);
        }
        currentTask = null;
        currentPlay = newPlay;
        playbook.getPlays().add(currentPlay);
    }
    
    private void nextTask(String line, long date) {
        Task newTask = Task.fromOutputLine(line, date);
        if (currentTask != null) {
            currentTask.setFinishedAt(date);
        }
        currentTask = newTask;
    }
    
    private void recap(long date) {
        if (currentTask != null) {
            currentTask.setFinishedAt(date);
        }
        currentTask = null;
        if (currentPlay != null) {
            currentPlay.setFinishedAt(date);
        }
        currentPlay = null;
        playbook.setFinishedAt(date);
    }
    
    private boolean isIgnore(String line) {
        return line == null || line.trim().isEmpty() ;
    }
    
    public Playbook getResults() {
        return playbook;
    }

}
