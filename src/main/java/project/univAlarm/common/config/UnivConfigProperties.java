package project.univAlarm.common.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties(prefix = "univ-config")
public class UnivConfigProperties {
    private List<UnivConfig> univList;

    @Getter @Setter
    public static class UnivConfig{
        private String univName;
        private List<Campus> campuses;
        private String url;

        @Getter @Setter
        public static class Campus{
            private String defaultCrawler;
            private String name;
            private String url;
            private List<UrlEntry> urls;

            @Getter @Setter
            public static class UrlEntry{
                private String url;
                private String name;
                private boolean department = true;
            }
        }

    }
}
