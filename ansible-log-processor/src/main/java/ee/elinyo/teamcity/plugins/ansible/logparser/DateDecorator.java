package ee.elinyo.teamcity.plugins.ansible.logparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ee.elinyo.teamcity.plugins.utils.Pair;

public class DateDecorator {
    
    private static Pattern DATE_DECORATOR_PATTERN = Pattern
            .compile("^(\\d+)\\|(.+)?");
    
    public static String decorate(String line) {
        return System.currentTimeMillis() + "|" + line;
    }
    
    public static Pair<Long, String> undecorate(String line) {
        Matcher matcher = DATE_DECORATOR_PATTERN.matcher(line);
        if (!matcher.find() || matcher.groupCount() != 2) {
            return null;
        }
        String content = matcher.group(2);
        Long date = Long.valueOf(matcher.group(1));
        return new Pair<Long, String>(date, content);
    }

}
