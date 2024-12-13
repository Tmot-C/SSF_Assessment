package vttp.batch5.ssf.noticeboard.utils;

import java.util.Arrays;
import java.util.List;

public interface Constants {
    
    String redisKey = "ssf_Assessment";

    String url = "https://publishing-production-d35a.up.railway.app/";

    String template = "Template";

    String delimiter = "CUSTOM_DELIMITER_PATTERN";

    List<String> eventNames = Arrays.asList("Christmas Eve Party", "Round Singapore Cycling", "Intro to SCRATCH", "JB Shopping !");
    
}