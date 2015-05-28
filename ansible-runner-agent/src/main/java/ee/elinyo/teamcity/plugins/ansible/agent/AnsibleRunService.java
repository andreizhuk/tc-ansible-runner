package ee.elinyo.teamcity.plugins.ansible.agent;

import java.util.ArrayList;
import java.util.List;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.agent.runner.SimpleProgramCommandLine;
import ee.elinyo.teamcity.plugins.ansible.common.AnsibleRunnerConstants;

public class AnsibleRunService extends BuildServiceAdapter {

    @Override
    public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {
        final String workingDir = getWorkingDirectory().getPath();
        
        StringBuilder arg = new StringBuilder("");
        arg.append(getRunnerParameters().get(AnsibleRunnerConstants.PLAYBOOK_FILE_KEY));
        arg.append(getRunnerParameters().get(AnsibleRunnerConstants.COMMAND_TYPE_KEY));
        arg.append(getRunnerParameters().get(AnsibleRunnerConstants.SOURCE_CODE_KEY));
        
        List<String> args = new ArrayList<String>();
        args.add("/c");
        args.add("echo");
        args.add(arg.toString());
        
        return new SimpleProgramCommandLine(getEnvironmentVariables(), workingDir, "C:\\Users\\Andrei\\Desktop\\ansible_logs\\printer.bat", args);
    }

}
