package ee.elinyo.teamcity.plugins.ansible.logparser;

public abstract class LogProcessingContext {

    public abstract void process(String line);

}
