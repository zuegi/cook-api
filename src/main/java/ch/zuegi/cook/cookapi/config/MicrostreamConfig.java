package ch.zuegi.cook.cookapi.config;


import ch.zuegi.cook.cookapi.shared.persistence.DataRoot;
import lombok.extern.slf4j.Slf4j;
import one.microstream.afs.nio.types.NioFileSystem;
import one.microstream.persistence.internal.InquiringLegacyTypeMappingResultor;
import one.microstream.persistence.types.PersistenceLegacyTypeMappingResultor;
import one.microstream.reflect.ClassLoaderProvider;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import one.microstream.storage.restservice.sparkjava.types.StorageRestServiceSparkJava;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import spark.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Configuration
@Profile("!integrationtest")
public class MicrostreamConfig {


    @Value("${app.microstream.rest-service-enabled}")
    private boolean restServiceEnabled;

    @Value("${app.microstream.rest-service-port}")
    private int restServicePort;

    @Value("${app.microstream.rest-service-instance-name}")
    private String restServiceInstancename;

    @Value("${app.microstream.store-location}")
    String location;

    @Bean
    public EmbeddedStorageManager storageManager() {

        URI uri = null;
        try {
            uri = ClassLoader.getSystemResource("microstream/refactorings.csv").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assert uri != null;
        URI finalUri = uri;

        NioFileSystem fileSystem = NioFileSystem.New();
        EmbeddedStorageManager storageManager = EmbeddedStorage.Foundation(fileSystem.ensureDirectoryPath(location))
                .onConnectionFoundation(cf -> {
                    cf.setClassLoaderProvider(ClassLoaderProvider.New(Thread.currentThread().getContextClassLoader()))
                            // TODO Refactoring microstream bzw. Klassen, Felder etc.
                            // siehe https://docs.microstream.one/manual/storage/legacy-type-mapping/index.html
//                            .setRefactoringMappingProvider(Persistence.RefactoringMapping(Paths.get(finalUri)))
                    .setLegacyTypeMappingResultor(
                            InquiringLegacyTypeMappingResultor.New(
                                    PersistenceLegacyTypeMappingResultor.New()
                            ));
                })
                .start();


        if (storageManager.root() == null) {
            log.info("No database found  - creating a new one");
            storageManager.setRoot(new DataRoot());
            storageManager.storeRoot();
        }

        if (restServiceEnabled) {
            StorageRestServiceSparkJava service = StorageRestServiceSparkJava.New(storageManager);
            service.setSparkService(
                    Service.ignite().port(restServicePort)
            );
            service.setInstanceName(restServiceInstancename);
            service.start();
        }
        return storageManager;
    }

}

