package ee.elinyo.teamcity.plugins.ansible.agent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import jetbrains.buildServer.util.FileUtil;

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
        String workingDir = getWorkingDirectory().getPath();
        List<String> args = Collections.emptyList();
        String customScript = getCustomScriptExecutable(config);
        return new SimpleProgramCommandLine(getProvidedEnvironmetVariables(),
                workingDir,
                customScript, args);
    }

    private String getCustomScriptExecutable(AnsibleRunConfig config) throws RunBuildException {
        String content = null;
        File scriptFile = null;
        if (config.getSourceCode() != null) {
            content = config.getSourceCode().replace("\r\n", "\n").replace("\r", "\n");
        }
        if (StringUtil.isEmptyOrSpaces(content)) {
            throw new RunBuildException("Custom script source code cannot be empty");
        }
        try {
            scriptFile = File.createTempFile("ansible_custom_exe", null, getBuildTempDirectory());
            FileUtil.writeFileAndReportErrors(scriptFile, content);
        } catch (IOException e) {
            throw new RunBuildException("Failed to create a tmp file for custom ansible execution script");
        }
        boolean executable = scriptFile.setExecutable(true, true);
        if (!executable) {
            throw new RunBuildException("Failed to set executable permissions to " + scriptFile.getAbsolutePath());
        }
        return scriptFile.getAbsolutePath();
    }

    @NotNull
    @Override
    public List<ProcessListener> getListeners() {
        List<ProcessListener> listeners = new ArrayList<ProcessListener>(super.getListeners());
        listeners.add(new AnsibleOutputListener(getBuild(), getRunnerContext(), artifactsWatcher));
        return listeners;
    }

}
