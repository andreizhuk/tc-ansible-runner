package ee.elinyo.teamcity.plugins.ansible.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProcessListener;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.agent.runner.SimpleProgramCommandLine;
import jetbrains.buildServer.runner.CommandLineArgumentsUtil;

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
        StringBuilder args = new StringBuilder("");
        if (!StringUtil.isEmptyOrSpaces(config.getInventory())) {
            args.append("-i ").append(config.getInventory());
        }
        if (StringUtil.isEmptyOrSpaces(config.getPlaybook())) {
            throw new RunBuildException("Ansible playbook should be specified");
        }
        args.append(" ").append(config.getPlaybook());
        if (!StringUtil.isEmptyOrSpaces(config.getOptions())) {
            args.append(" ").append(config.getOptions());
        }
        return new SimpleProgramCommandLine(getProvidedEnvironmetVariables(),
                workingDir,
               config.getExecutable(), CommandLineArgumentsUtil.extractArguments(args.toString()));
    }
    
    private Map<String, String> getProvidedEnvironmetVariables() {
        Map<String, String> vars = new HashMap<String, String>(getEnvironmentVariables());
        vars.put("ANSIBLE_NOCOLOR", "1");
        return vars;
    }

    private ProgramCommandLine makeCustomScriptCommand(AnsibleRunConfig config) throws RunBuildException {
       // String workingDir = getWorkingDirectory().getPath();
        LOG.warn("Not Implemented");
        throw new RunBuildException("Ansible customs script is not imlemented");
    }

    @NotNull
    @Override
    public List<ProcessListener> getListeners() {
        List<ProcessListener> listeners = new ArrayList<ProcessListener>(super.getListeners());
        listeners.add(new AnsibleOutputListener(getBuild(), getRunnerContext(), artifactsWatcher));
        return listeners;
    }

}
