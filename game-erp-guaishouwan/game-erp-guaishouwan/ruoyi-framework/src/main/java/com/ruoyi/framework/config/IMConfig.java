package com.ruoyi.framework.config;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//IM配置
@Configuration
public class IMConfig {

    @Bean
    public EMService service() {

        EMProperties properties = EMProperties.builder()
                .setAppkey("1177260622193188#guaishouwan")
                .setClientId("YXA6ivoT3ZenQZW_XltZZH4Gpw")
                .setClientSecret("YXA6GtueB9d6r8oxitV92rLlKuxjMuM")
                .build();

        return new EMService(properties);
    }
}
