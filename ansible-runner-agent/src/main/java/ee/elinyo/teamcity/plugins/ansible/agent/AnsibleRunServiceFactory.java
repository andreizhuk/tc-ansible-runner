package ee.elinyo.teamcity.plugins.ansible.agent;

import jetbrains.buildServer.agent.AgentBuildRunnerInfo;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import jetbrains.buildServer.agent.runner.CommandLineBuildService;
import jetbrains.buildServer.agent.runner.CommandLineBuildServiceFactory;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.util.SystemInfo;

import ee.elinyo.teamcity.plugins.ansible.common.AnsibleRunnerConstants;

public class AnsibleRunServiceFactory implements CommandLineBuildServiceFactory, AgentBuildRunnerInfo {
    
    private ArtifactsWatcher artifactsWatcher;
    
    public AnsibleRunServiceFactory(@NotNull ArtifactsWatcher artifactsWatcher) {
        this.artifactsWatcher = artifactsWatcher;
    }

    @Override
    public String getType() {
        return AnsibleRunnerConstants.RUN_TYPE;
    }

    @Override
    public boolean canRun(BuildAgentConfiguration agentConfiguration) {
        return SystemInfo.isLinux;
    }

    @Override
    public CommandLineBuildService createService() {
        return new AnsibleRunService(artifactsWatcher);
    }

    @Override
    public AgentBuildRunnerInfo getBuildRunnerInfo() {
        return this;
    }
}
