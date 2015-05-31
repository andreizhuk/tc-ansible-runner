package ee.elinyo.teamcity.plugins.ansible.logparser;

import ee.elinyo.teamcity.plugins.ansible.logparser.domain.HostResult;
import ee.elinyo.teamcity.plugins.ansible.logparser.domain.Play;
import ee.elinyo.teamcity.plugins.ansible.logparser.domain.Task;
import ee.elinyo.teamcity.plugins.ansible.logparser.domain.VerboseMessage;

public class TaskContext extends LogProcessingContext {
    
    private final Task currentTask;

    public TaskContext(Task task, Play play) {
        this.currentTask = task;
        play.getTasks().add(task);
    }

    @Override
    public void process(String line) {
        if (AnsibleLogUtils.isVerboseMsg(line)) {
            currentTask.getVerbose().add(VerboseMessage.fromOutputLine(line));
            return;
        }
        HostResult hr = HostResult.fromOutputLine(line);
        if (hr != null) {
            currentTask.getHosts().add(hr);
        } else if (!currentTask.getHosts().isEmpty()) {
            currentTask.getHosts().get(currentTask.getHosts().size() - 1).getDetails().add(line);
        }
    }

}
