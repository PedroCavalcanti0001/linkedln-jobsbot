package me.pedroeugenio.linkedlnjobsbot.scraper;

import me.pedroeugenio.linkedlnjobsbot.config.AppConfig;
import me.pedroeugenio.linkedlnjobsbot.enums.MomentFilterEnum;
import me.pedroeugenio.linkedlnjobsbot.enums.SortEnum;
import me.pedroeugenio.linkedlnjobsbot.models.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

class JobsConstants {
    protected static final Properties PROPERTIES = AppConfig.getSingleton();
    protected static final Logger LOGGER = LogManager.getLogger(JobsSearch.class.getName());
    protected final static String BASE_URL = "http://www.linkedin.com/jobs/search/?currentJobId=1";
    protected final static Map<String, String> HEADERS = new HashMap<String, String>() {{
        put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        put("accept-language", "pt-BR,pt;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        put("^cookie", "bcookie=^\\^v=2");
        put("^cache-control", "max-age=0");
        put("^sec-ch-ua", "^\\^Microsoft");
        put("sec-ch-ua-mobile", "?0");
        put("^sec-ch-ua-platform", "Windows");
        put("sec-fetch-dest", "document");
        put("sec-fetch-mode", "navigate");
        put("sec-fetch-site", "none");
        put("sec-fetch-user", "?1");
        put("upgrade-insecure-requests", "1");
        put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 Edg/123.0.0.0");
    }};
    protected final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 Edg/123.0.0.0";
    protected static final SortEnum SORT = SortEnum.valueOf(PROPERTIES.getOrder().toUpperCase());
}