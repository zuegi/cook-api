package ch.zuegi.cook.cookapi.config;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import one.microstream.storage.restservice.sparkjava.types.StorageRestServiceSparkJava;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spark.Service;

@Configuration
@ConditionalOnProperty(
        value="app.microstream.rest-service-enabled",
        havingValue = "true",
        matchIfMissing = false)
public class MicrostreamRestConfig {

    @Value("${app.microstream.rest-service-port}")
    private int restServicePort;

    @Value("${app.microstream.rest-service-instance-name}")
    private String restServiceInstancename;

    @Autowired
    private EmbeddedStorageManager storageManager;

    @Bean
    public StorageRestServiceSparkJava service() {

            StorageRestServiceSparkJava service = StorageRestServiceSparkJava.New(storageManager);
            service.setSparkService(
                    Service.ignite().port(restServicePort)
            );
            service.setInstanceName(restServiceInstancename);
            service.start();

            return service;
    }

}
