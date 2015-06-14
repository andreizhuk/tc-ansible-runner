package ee.elinyo.teamcity.plugins.ansible.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import jetbrains.buildServer.util.EventDispatcher;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.diagnostic.Logger;

import ee.elinyo.teamcity.plugins.ansible.common.AnsibleRunnerConstants;
import ee.elinyo.teamcity.plugins.ansible.logparser.AnsibleOutputProcessor;
import ee.elinyo.teamcity.plugins.ansible.logparser.domain.Playbook;

public class AnsibleReportArtifatcsProvider extends AgentLifeCycleAdapter {
    
    private static final Logger LOG = Logger.getInstance(AnsibleReportArtifatcsProvider.class.getName());
    
    private static final String ACTIVITY_NAME = "Processing ansible output";
    private static final String ACTIVITY_TYPE = "ansible_run_report";
    
    private ArtifactsWatcher artifactsWatcher;

    public AnsibleReportArtifatcsProvider(
            @NotNull ArtifactsWatcher artifactsWatcher,
            @NotNull EventDispatcher<AgentLifeCycleListener> agentDispatcher) {
        agentDispatcher.addListener(this);
        this.artifactsWatcher = artifactsWatcher;
    }
    
    public void beforeBuildFinish(@NotNull final AgentRunningBuild build, @NotNull final BuildFinishedStatus buildStatus) {
        String tmpDirPath = build.getSharedConfigParameters().get(AnsibleRunnerConstants.ARTIFACTS_TMP_DIR_KEY);
        if (tmpDirPath == null) {
            return;
        }
        File tmpDir = new File(tmpDirPath);
        if (tmpDir.exists() && tmpDir.isDirectory()) {
            File[] rawFiles = tmpDir.listFiles();
            if (rawFiles == null || rawFiles.length == 0) {
                build.getBuildLogger().warning("Build defines ansible-run tmp directory but it doesn't contain any files");
            } else {
                generateReport(build, rawFiles);
            }
        } else {
            build.getBuildLogger().warning("Build defines ansible-run tmp directory but it doesn't exist");
        }
    }
    
    private void generateReport(AgentRunningBuild build, File[] rawFiles) {
        build.getBuildLogger().activityStarted(ACTIVITY_NAME, ACTIVITY_TYPE);
        try {
            build.getBuildLogger().message("Generating ansible report artifacts...");
            List<Playbook> playbooks = new ArrayList<Playbook>();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Ansible reports will be generated for the following raw files: ");
                for (File f : rawFiles) {
                    LOG.debug(f.getAbsolutePath());
                }
            }
            for (File f: rawFiles) {
                Playbook p = getPlaybook(f);
                if (p == null) {
                    build.getBuildLogger().warning("Failed to generate report. Check agent logs for more details");
                } else {
                    playbooks.add(p);
                }
            }
            provideArtifacts(playbooks, build);
        } finally {
            build.getBuildLogger().activityFinished(ACTIVITY_NAME, ACTIVITY_TYPE);
        }
    }
    
    private void provideArtifacts(List<Playbook> playbooks, AgentRunningBuild build) {
        File tmpReport = new File(build.getBuildTempDirectory(), AnsibleRunnerConstants.ARTIFACTS_JSON_REPORT);
        try {
            tmpReport.createNewFile();
            ReportBuilder.buildJsonReport(playbooks, tmpReport);
            build.getBuildLogger().message("Uploading artifact...");
            artifactsWatcher.addNewArtifactsPath(tmpReport.getAbsolutePath() + "=>" + AnsibleRunnerConstants.ARTIFACTS_BASE_DIR);
        } catch (Exception e) {
            LOG.error("Failed to create a file to write ansible run report", e);
            build.getBuildLogger().warning("Failed to generate report. Check agent logs for more details");
        }
    }

    private Playbook getPlaybook(File rawLog) {
        AnsibleOutputProcessor logProcessor = new AnsibleOutputProcessor();
        Playbook result = null;
        try (BufferedReader br = new BufferedReader(new FileReader(rawLog))) {
            String strLine = "";
            while( (strLine = br.readLine()) != null){
                logProcessor.onLine(strLine);
            }
            result = logProcessor.getResults();
        } catch (Exception e) {
            LOG.error("Failed to generate ansible run report", e);
        } 
        return result;
    }

}
