package ch.zuegi.cook.cookapi;


import ch.zuegi.cook.cookapi.shared.persistence.DataRoot;
import lombok.extern.slf4j.Slf4j;
import one.microstream.afs.nio.types.NioFileSystem;
import one.microstream.reflect.ClassLoaderProvider;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.walk;
import static java.util.Collections.reverseOrder;

@Slf4j
@TestConfiguration
@Profile({"integrationtest"})
public class MicrostreamTestConfig {

    @Bean
    public EmbeddedStorageManager storageManager() {

        File tempFolder = new File("./target/junit/storage");
        if (tempFolder.exists()) {
            try {
                walk(tempFolder.toPath()).sorted(reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                log.warn(e.getMessage());
            }
        }

        NioFileSystem fileSystem = NioFileSystem.New();
        EmbeddedStorageManager storageManager = EmbeddedStorage.Foundation(fileSystem.ensureDirectoryPath(tempFolder.getPath()))
                .onConnectionFoundation(cf -> cf.setClassLoaderProvider(ClassLoaderProvider.New(
                        Thread.currentThread().getContextClassLoader())))
                .start();


        if (storageManager.root() == null) {
            log.info("No database found  - creating a new one");
            storageManager.setRoot(new DataRoot());
            storageManager.storeRoot();
        }

        return storageManager;
    }

}

