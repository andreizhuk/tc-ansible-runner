package ee.elinyo.teamcity.plugins.ansible.server;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.web.openapi.PluginDescriptor;

import org.jetbrains.annotations.NotNull;

import ee.elinyo.teamcity.plugins.ansible.common.AnsibleRunnerConstants;

public class AnsibleRunType extends RunType {
    
    private PluginDescriptor pluginDescriptor;
    
    public AnsibleRunType(@NotNull final RunTypeRegistry reg, @NotNull final PluginDescriptor pluginDescriptor) {
        this.pluginDescriptor = pluginDescriptor;
        reg.registerRunType(this);
    }

    @Override
    public String getType() {
        return AnsibleRunnerConstants.RUN_TYPE;
    }

    @Override
    public String getDisplayName() {
        return "Ansible";
    }

    @Override
    public String getDescription() {
        return "Ansible Playbook runner";
    }

    @Override
    public String getEditRunnerParamsJspFilePath() {
        return pluginDescriptor.getPluginResourcesPath("editAnsibleRun.jsp");
    }

    @Override
    public String getViewRunnerParamsJspFilePath() {
        return pluginDescriptor.getPluginResourcesPath("viewAnsibleRun.jsp");
    }
    
    @NotNull
    @Override
    public Map<String, String> getDefaultRunnerProperties() {
        Map<String, String> defaults = new HashMap<String, String>();
        defaults.put(AnsibleRunnerConstants.COMMAND_TYPE_KEY, AnsibleRunBean.getInstance().getPlaybookCommandValue());
        return defaults;
    }

    @NotNull
    @Override
    public PropertiesProcessor getRunnerPropertiesProcessor() {
      return new PropertiesProcessor() {
        public Collection<InvalidProperty> process(final Map<String, String> properties) {
          return Collections.emptyList();
        }
      };
    }
    
    @Override
    public String describeParameters(Map<String, String> params) {
        StringBuilder sb = new StringBuilder("Ansible ");
        sb.append(params.get((AnsibleRunnerConstants.COMMAND_TYPE_KEY))).append("\n");
        sb.append(params.get(AnsibleRunnerConstants.PLAYBOOK_FILE_KEY));
        return sb.toString();
    } 


}
