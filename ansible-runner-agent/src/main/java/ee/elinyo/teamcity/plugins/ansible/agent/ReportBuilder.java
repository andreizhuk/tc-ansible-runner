package ee.elinyo.teamcity.plugins.ansible.agent;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import ee.elinyo.teamcity.plugins.ansible.logparser.domain.Playbook;

public class ReportBuilder {
    
    public static void buildJsonReport(List<Playbook> playbooks, File resultFile) throws Exception {
        JsonFactory jf = new MappingJsonFactory();
        try (JsonGenerator jg = jf.createGenerator(resultFile, JsonEncoding.UTF8)) {
            jg.writeStartObject();
            jg.writeFieldName("playbooks");
            jg.writeObject(playbooks);
            jg.writeEndObject();
        } catch (Exception e) {
            throw e;
        }
        
    }

}
