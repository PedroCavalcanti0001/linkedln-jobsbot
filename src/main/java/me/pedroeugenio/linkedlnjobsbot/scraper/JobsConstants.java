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
    protected final static String BASE_URL = "https://www.linkedin.com/jobs/search/?currentJobId=1";
    protected final static Map<String, String> HEADERS = new HashMap<String, String>() {{
        put("accept-language", "pt-BR,pt;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        put("accept-encoding", "gzip, deflate, br");
        put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/" +
                "signed-exchange;v=b3;q=0.9");
        put("pragma", "no-cache");
        put("sec-ch-ua", "\"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Microsoft Edge\";v=\"104\"");
        put("sec-ch-ua-mobile", "?0");
        put("sec-ch-ua-platform", "Windows");
        put("sec-fetch-dest", "document");
        put("sec-fetch-mode", "navigate");
        put("sec-fetch-site", "same-origin");
        put("sec-fetch-user", "?1");
        put("upgrade-insecure-requests", "1");
    }};
    protected final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.5112.102 Safari/537.36 Edg/104.0.1293.70";
    protected static final SortEnum SORT = SortEnum.valueOf(PROPERTIES.getOrder().toUpperCase());
    protected static final MomentFilterEnum MOMENT = MomentFilterEnum.DAY;
}