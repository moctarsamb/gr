package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway")
public class FeignConfiguration {

    /**
     * Set the Feign specific log level to log client REST requests
     */
    @Bean
    feign.Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.BASIC;
    }
}
