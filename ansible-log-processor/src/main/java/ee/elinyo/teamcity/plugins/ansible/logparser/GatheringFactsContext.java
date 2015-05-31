package ee.elinyo.teamcity.plugins.ansible.logparser;

import ee.elinyo.teamcity.plugins.ansible.logparser.domain.HostResult;
import ee.elinyo.teamcity.plugins.ansible.logparser.domain.Play;

public class GatheringFactsContext extends LogProcessingContext {
    
    private final Play currentPlay;
    
    public GatheringFactsContext(Play play) {
        this.currentPlay = play;
    }

    @Override
    public void process(String line) {
        HostResult hr = HostResult.fromOutputLine(line);
        if (hr != null) {
            currentPlay.getFacts().add(hr);
        } else if (!currentPlay.getFacts().isEmpty()) {
            currentPlay.getFacts().get(currentPlay.getFacts().size() - 1).getDetails().add(line);
        }
    }

}
