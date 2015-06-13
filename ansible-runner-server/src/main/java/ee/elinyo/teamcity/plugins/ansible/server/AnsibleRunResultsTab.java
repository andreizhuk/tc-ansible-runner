package ee.elinyo.teamcity.plugins.ansible.server;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.ViewLogTab;

import org.jetbrains.annotations.NotNull;

import ee.elinyo.teamcity.plugins.ansible.common.AnsibleRunnerConstants;

public class AnsibleRunResultsTab extends ViewLogTab {
    
    public AnsibleRunResultsTab(@NotNull PagePlaces pagePlaces, @NotNull SBuildServer server, @NotNull PluginDescriptor descriptor) {
        super("", "", pagePlaces, server);
        setTabTitle(getTitle());
        setPluginName(getClass().getSimpleName());
        setIncludeUrl(getJspPage(descriptor));
//        addCssFile(descriptor.getPluginResourcesPath("css/style.css"));
//        addJsFile(descriptor.getPluginResourcesPath("js/angular.min.js"));
//        addJsFile(descriptor.getPluginResourcesPath("js/angular-app.js"));
    }

    private String getTitle() {
        return "Ansible Run";
    }

    private String getJspPage(PluginDescriptor descriptor) {
        return descriptor.getPluginResourcesPath("resultsTab.jsp");
    }

    @Override
    protected void fillModel(Map<String, Object> model,
            HttpServletRequest request, SBuild build) {
        // TODO Auto-generated method stub
    }
    
    protected boolean isAvailable(@NotNull final HttpServletRequest request, @NotNull final SBuild build) {
        return build.getBuildType().getRunnerTypes().contains(AnsibleRunnerConstants.RUN_TYPE);
    }

}
