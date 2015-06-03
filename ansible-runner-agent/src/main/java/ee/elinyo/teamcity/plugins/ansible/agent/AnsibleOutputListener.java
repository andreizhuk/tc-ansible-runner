package ee.elinyo.teamcity.plugins.ansible.agent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import jetbrains.buildServer.agent.runner.ProcessListener;

import com.intellij.openapi.diagnostic.Logger;

import ee.elinyo.teamcity.plugins.ansible.common.AnsibleRunnerConstants;
import ee.elinyo.teamcity.plugins.ansible.logparser.AnsibleLogUtils;
import ee.elinyo.teamcity.plugins.ansible.logparser.DateDecorator;

public class AnsibleOutputListener implements ProcessListener {
    
    private static final Logger LOG = Logger.getInstance(AnsibleOutputListener.class.getName());

    private File rawFile;
    private PrintWriter rawFileWriter;
    private final ArtifactsWatcher artifactsWatcher;
    private final BuildRunnerContext buildRunnerContext;

    public AnsibleOutputListener(AgentRunningBuild build, BuildRunnerContext buildRunnerContext, ArtifactsWatcher artifactsWatcher) {
        this.artifactsWatcher = artifactsWatcher;
        this.buildRunnerContext = buildRunnerContext;
        File tmpDir = new File(build.getBuildTempDirectory(), "ansible-run");
        boolean exists = tmpDir.exists(); 
        if (!exists) {
            exists = tmpDir.mkdir();
        }
        if (!exists) {
            LOG.error("Cannot create a directory " + tmpDir.getAbsolutePath() + " for ansible run raw output storage");
            build.getBuildLogger().warning("Cannot create a directory for ansible run raw output storage. Ansible report will not be generated");
        } else {
            String rawFileName = "ansible-run-raw-" + buildRunnerContext.getId() + ".log";
            rawFile = new File(tmpDir, rawFileName);
            try {
                rawFile.createNewFile();
            } catch (IOException e) {
                LOG.error("Cannot create a file " + rawFileName + " for ansible run raw output writing ", e);
                build.getBuildLogger().warning("Cannot create a file for ansible run raw output writing. Ansible report will not be generated");
            }
            build.addSharedConfigParameter(AnsibleRunnerConstants.ARTIFACTS_TMP_DIR_KEY, tmpDir.getAbsolutePath());
        }
    }

    @Override
    public void onStandardOutput(String text) {
        if (rawFileWriter != null) {
            rawFileWriter.println(DateDecorator.decorate(text));
        }
    }

    @Override
    public void onErrorOutput(String text) {
        // TODO Auto-generated method stub
    }

    @Override
    public void processStarted(String programCommandLine, File workingDirectory) {
        if (rawFile != null) {
            try {
                rawFileWriter = new PrintWriter(rawFile);
                writeStartMeta();
            } catch (FileNotFoundException e) {
                LOG.error("Cannot find a file for ansible run raw output writing", e);
            }
        }
    }

    @Override
    public void processFinished(int exitCode) {
        if (rawFileWriter != null) {
            writeEndMeta(exitCode);
            rawFileWriter.close();
            artifactsWatcher.addNewArtifactsPath(rawFile.getAbsolutePath() + "=>" + AnsibleRunnerConstants.ARTIFACTS_BASE_DIR);
        }
    }
    
    private void writeStartMeta() {
        String runnerIdMsg = AnsibleLogUtils.getBuildMetaMessage(AnsibleRunnerConstants.RUNNER_ID_META_KEY, buildRunnerContext.getId());
        rawFileWriter.println(DateDecorator.decorate(runnerIdMsg));
        String stepNameMsg = AnsibleLogUtils.getBuildMetaMessage(AnsibleRunnerConstants.STEP_NAME_META_KEY, buildRunnerContext.getName());
        rawFileWriter.println(DateDecorator.decorate(stepNameMsg));
    }
    
    private void writeEndMeta(int exitCode) {
        String runnerIdMsg = AnsibleLogUtils.getBuildMetaMessage(AnsibleRunnerConstants.EXIT_CODE_META_KEY, String.valueOf(exitCode));
        rawFileWriter.println(DateDecorator.decorate(runnerIdMsg));
    }

}
