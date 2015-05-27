package ee.elinyo.teamcity.plugins.ansible.agent;

import java.util.ArrayList;
import java.util.List;

import ee.elinyo.teamcity.plugins.ansible.common.AnsibleRunnerConstants;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.agent.runner.SimpleProgramCommandLine;

public class AnsibleRunService extends BuildServiceAdapter {

    @Override
    public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {
        final String workingDir = getWorkingDirectory().getPath();
        
        List<String> arguments = new ArrayList<String>();
        arguments.add(getRunnerParameters().get(AnsibleRunnerConstants.PLAYBOOK_FILE_KEY));
        arguments.add(getRunnerParameters().get(AnsibleRunnerConstants.COMMAND_TYPE_KEY));
        arguments.add(getRunnerParameters().get(AnsibleRunnerConstants.SOURCE_CODE_KEY));
        
        return new SimpleProgramCommandLine(getEnvironmentVariables(), workingDir, "echo", arguments);
    }

}
