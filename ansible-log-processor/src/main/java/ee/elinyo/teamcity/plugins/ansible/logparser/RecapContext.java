package ee.elinyo.teamcity.plugins.ansible.logparser;

import ee.elinyo.teamcity.plugins.ansible.logparser.domain.HostRecap;
import ee.elinyo.teamcity.plugins.ansible.logparser.domain.Playbook;

public class RecapContext extends LogProcessingContext {

    private final Playbook playbook;

    public RecapContext(Playbook playbook) {
        this.playbook = playbook;
    }

    @Override
    public void process(String line) {
        HostRecap hr = HostRecap.fromOutputLine(line);
        if (hr != null) {
            playbook.getRecaps().add(hr);
        }

    }

}
