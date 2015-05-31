package ee.elinyo.teamcity.plugins.ansible.logparser;

import org.junit.Assert;
import org.junit.Test;

import ee.elinyo.teamcity.plugins.ansible.logparser.domain.Playbook;

public class SimpleTest {
    
    @Test
    public void testParser() {
        Playbook p = TestLogReader.getPlaybook("/sample.log");
        Assert.assertNotNull(p);
        Assert.assertEquals(1, p.getPlays().size());
        Assert.assertEquals(4, p.getPlays().get(0).getTasks().size());
    }

}
