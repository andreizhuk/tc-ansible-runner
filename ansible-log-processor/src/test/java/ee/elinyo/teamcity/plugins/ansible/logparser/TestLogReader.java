package ee.elinyo.teamcity.plugins.ansible.logparser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ee.elinyo.teamcity.plugins.ansible.logparser.domain.Playbook;

public class TestLogReader {

    public static Playbook getPlaybook(String filename) {
        AnsibleOutputProcessor logProcessor = new AnsibleOutputProcessor();
        Playbook result = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                TestLogReader.class.getResourceAsStream(filename)))) {
            String strLine = "";
            while ((strLine = br.readLine()) != null) {
                logProcessor.onLine(strLine);
            }
            result = logProcessor.getResults();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
