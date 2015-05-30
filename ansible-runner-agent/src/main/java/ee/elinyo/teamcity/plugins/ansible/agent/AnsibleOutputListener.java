package ee.elinyo.teamcity.plugins.ansible.agent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import jetbrains.buildServer.agent.runner.ProcessListener;

public class AnsibleOutputListener implements ProcessListener {

    private final File resultsFile;

    public AnsibleOutputListener(AgentRunningBuild build, ArtifactsWatcher artifactsWatcher) {
        File buildDir = build.getBuildTempDirectory();
        resultsFile = new File(buildDir, "ansible-run.yml");
        try {
            resultsFile.createNewFile();
            artifactsWatcher.addNewArtifactsPath(resultsFile.getAbsolutePath() + "=>" + ".teamcity");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onStandardOutput(String text) {
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(resultsFile, true)))) {
            out.println(text);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorOutput(String text) {
        // TODO Auto-generated method stub

    }

    @Override
    public void processStarted(String programCommandLine, File workingDirectory) {
        System.out.println("Started ansible");

    }

    @Override
    public void processFinished(int exitCode) {
        System.out.println("finished ansible");
    }

}
