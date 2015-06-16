# tc-ansible-runner
TeamCity build runner plugin for running *ansible-playbook*.
Adds 'Ansible' runner type to new build step creation dialog and 'Ansible Log' tab to build result pages.

Developed and tested with TeamCity 9.0.4
## Usage Guide
### Agent requirements
* Runs on Linux based agents only
* The plugin assumes that ansible is installed on an agent host and doesn't perform any auto-discovery of ansible-playbook executable

### Installation
Follow the standard TeamCity plugin installation procedure:

1. Download the latest release of ansible-runner-dist.zip from [GitHub](https://github.com/andreizhuk/tc-ansible-runner/releases)
1. Shutdown the TeamCity server.
1. drop the ansible-runner-dist.zip into *<TeamCity Data Directory>/plugins* directory
1. Start the TeamCity server - the plugin will be available in the Plugins List in the Administration area.

### Add new build step
1. Add new build step to a build configuration
1. Select 'Ansible' in the runner type drop-down
1. Specify ansible-playbook executable name (if available on agent PATH) or absolute path in the 'Command' field, mandatory
1. Specify playbook file in the 'Playbook' field, mandatory
1. Specify inventory file in the 'Inventory' field, mandatory
1. You can optionally pass other ansible-playbook arguments in the 'Options' field

### View results
Open "Ansible Log" tab for a selected build.
The report resembles ansible native colored output and adds filtering possibilities

[Sceenshot](README/AnsibleLog-screen.png)

### Troubleshooting
The plugin generates hidden artifacts in *.teamcity/ansible-run* directory. Please consider attaching its content along with build log excerpts when reporting an issue on github

## Missing features
* verbose output is not displayed, although recognized by the plugin
* async tasks (haven't tested)
* total recap styling
* ignored failure handling
* host detailed output is displayed in a simple JS alert
* exact failure reason on build results 'Overview' tab
* task duration statistics

## Implementation overview
The plugin consists of several components:
* [ansible-log-processor](ansible-log-processor) - parses ansible-playbook standard out and builds an object model out of that, the implementation is TeamCity agnostic
* [ansible-runner-agent](ansible-runner-agent) - the TeamCity agent side extension, captures ansible-playbook standard output, builds a model using [ansible-log-processor](ansible-log-processor) and serializes it into JSON, which is published as a build artifact
* [ansible-runner-server](ansible-runner-server) - the TeamCity server side extension, provides user interface for runner step configuration and 'Ansible Log' report tab. The report is built in client's browser from the JSON produced by the agent extension
* [ansible-runner-common](ansible-runner-common) - utilities and constants shared between TeamCity server and agent extension

### Build
```
mvn clean package
```
