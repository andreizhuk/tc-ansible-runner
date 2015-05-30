package ee.elinyo.teamcity.plugins.ansible.agent;

import java.util.ArrayList;
import java.util.List;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProcessListener;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.agent.runner.SimpleProgramCommandLine;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.text.StringUtil;

import ee.elinyo.teamcity.plugins.ansible.common.AnsibleCommand;
import ee.elinyo.teamcity.plugins.ansible.common.AnsibleRunConfig;

public class AnsibleRunService extends BuildServiceAdapter {
    
    protected static final Logger LOG = Logger.getInstance(AnsibleRunService.class.getName());
    
    private ArtifactsWatcher artifactsWatcher;

    public AnsibleRunService(ArtifactsWatcher artifactsWatcher) {
        this.artifactsWatcher = artifactsWatcher;
    }

    @Override
    public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {
        AnsibleRunConfig config = new AnsibleRunConfig(getRunnerParameters());
        if (LOG.isDebugEnabled()) {
            LOG.debug("Going to run ansible with parameters: " + config.toString());
        }
        if (AnsibleCommand.CUSTOM_SCRIPT.equals(config.getCommandType())) {
            return makeCustomScriptCommand(config);
        }
        return makeExecutableCommand(config);
    }
    
    private ProgramCommandLine makeExecutableCommand(AnsibleRunConfig config)
            throws RunBuildException {
        String workingDir = getWorkingDirectory().getPath();
        List<String> args = new ArrayList<String>();
        if (!StringUtil.isEmptyOrSpaces(config.getInventory())) {
            args.add("-i");
            args.add(config.getInventory());
        }
        if (StringUtil.isEmptyOrSpaces(config.getPlaybook())) {
            throw new RunBuildException("Ansible playbook should be specified");
        }
        args.add(config.getPlaybook());
        if (!StringUtil.isEmptyOrSpaces(config.getOptions())) {
            String opts = config.getOptions().replace("\n", "").replace("\r", "");
            args.add(opts);
        }
        return new SimpleProgramCommandLine(getEnvironmentVariables(),
                workingDir,
               config.getExecutable(), args);
    }

    private ProgramCommandLine makeCustomScriptCommand(AnsibleRunConfig config) throws RunBuildException {
        String workingDir = getWorkingDirectory().getPath();
        LOG.warn("Not Implemented");
        throw new RunBuildException("Ansible customs script is not imlemented");
    }

    @NotNull
    @Override
    public List<ProcessListener> getListeners() {
        List<ProcessListener> listeners = new ArrayList<ProcessListener>(super.getListeners());
        listeners.add(new AnsibleOutputListener(getBuild(), artifactsWatcher));
        return listeners;
    }

}
