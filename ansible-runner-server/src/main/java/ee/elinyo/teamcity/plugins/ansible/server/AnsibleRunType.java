package ee.elinyo.teamcity.plugins.ansible.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.web.openapi.PluginDescriptor;

import org.jetbrains.annotations.NotNull;

import ee.elinyo.teamcity.plugins.ansible.common.AnsibleCommand;
import ee.elinyo.teamcity.plugins.ansible.common.AnsibleRunConfig;
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
        defaults.put(AnsibleRunnerConstants.COMMAND_TYPE_KEY, AnsibleCommand.EXECUTABLE.getValue());
        defaults.put(AnsibleRunnerConstants.EXECUTABLE_KEY, "ansible-playbook");
        return defaults;
    }

    @NotNull
    @Override
    public PropertiesProcessor getRunnerPropertiesProcessor() {
      return new PropertiesProcessor() {
        public Collection<InvalidProperty> process(final Map<String, String> properties) {
            List<InvalidProperty> errors = new ArrayList<InvalidProperty>();
            AnsibleRunConfig config = new AnsibleRunConfig(properties);
            if (AnsibleCommand.EXECUTABLE.equals(config.getCommandType())) {
                if (StringUtil.isEmptyOrSpaces(config.getExecutable())) {
                    errors.add(new InvalidProperty(AnsibleRunnerConstants.EXECUTABLE_KEY, "Cannot be empty"));
                }
                if (StringUtil.isEmptyOrSpaces(config.getPlaybook())) {
                    errors.add(new InvalidProperty(AnsibleRunnerConstants.PLAYBOOK_FILE_KEY, "Cannot be empty"));
                }
            } else if (AnsibleCommand.CUSTOM_SCRIPT.equals(config.getCommandType())) {
                if (StringUtil.isEmptyOrSpaces(config.getSourceCode())) {
                    errors.add(new InvalidProperty(AnsibleRunnerConstants.SOURCE_CODE_KEY, "Cannot be empty"));
                }
            }
          return errors;
        }
      };
    }
    
    @Override
    public String describeParameters(Map<String, String> params) {
        StringBuilder sb = new StringBuilder("");
        AnsibleRunConfig config = new AnsibleRunConfig(params);
        if (AnsibleCommand.CUSTOM_SCRIPT.equals(config.getCommandType())) {
            sb.append("Ansible by custom script");
        } else {
            sb.append(config.getExecutable()).append(" ").append(config.getPlaybook());
        }
        return sb.toString();
    } 


}
