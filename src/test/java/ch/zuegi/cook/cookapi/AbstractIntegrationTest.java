package ch.zuegi.cook.cookapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"integrationtest"})
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
@Import(MicrostreamTestConfig.class)
@Slf4j
@DirtiesContext
public class AbstractIntegrationTest {

}
