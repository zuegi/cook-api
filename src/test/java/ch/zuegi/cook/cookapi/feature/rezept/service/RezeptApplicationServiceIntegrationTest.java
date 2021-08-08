package ch.zuegi.cook.cookapi.feature.rezept.service;

import ch.zuegi.cook.cookapi.AbstractIntegrationTest;
import ch.zuegi.cook.cookapi.feature.rezept.domain.Rezept;
import ch.zuegi.cook.cookapi.feature.rezept.domain.RezeptId;
import ch.zuegi.cook.cookapi.feature.rezept.infra.rest.ErstelleRezept;
import ch.zuegi.cook.cookapi.shared.persistence.DataRoot;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static ch.zuegi.cook.cookapi.feature.rezept.domain.ZubereitungTestHelper.assertZubereitung;
import static ch.zuegi.cook.cookapi.feature.rezept.domain.ZubereitungTestHelper.createZubereitungOmelette;
import static ch.zuegi.cook.cookapi.feature.rezept.domain.ZutatTestHelper.assertOmeletten;
import static ch.zuegi.cook.cookapi.feature.rezept.domain.ZutatTestHelper.createZutatenListeFuerOmeletten;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RezeptApplicationServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    RezeptApplicationService service;

    @Autowired
    EmbeddedStorageManager storageManager;

    @AfterEach
    void clean() {
        storageManager.setRoot(new DataRoot());
        storageManager.storeRoot();
    }

    @Test
    void rezept_erstelle_valid() {

        RezeptId rezeptId = RezeptId.generate();
        String omeletten = "Omeletten";
        ErstelleRezept erstelleRezept = new ErstelleRezept(rezeptId, omeletten, createZutatenListeFuerOmeletten(), createZubereitungOmelette());
        service.erstelleRezept(erstelleRezept);

        Rezept rezept = service.findByRezeptId(rezeptId);

        assertThat(rezept.getRezeptId(), is(rezeptId));
        assertThat(rezept.getName(), is(omeletten));
        assertOmeletten(rezept, 1);
        assertZubereitung(rezept);
    }

    @Test
    void rezept_finde_alle_und_aendere_ein_name_valid() {
        RezeptId rezeptId = RezeptId.generate();
        String omeletten = "Omeletten";
        ErstelleRezept erstelleRezept = new ErstelleRezept(rezeptId, omeletten, createZutatenListeFuerOmeletten(), createZubereitungOmelette());
        service.erstelleRezept(erstelleRezept);

        String panCakes = "Pacnakes";
        RezeptId rezeptIdPanCake = RezeptId.generate();
        ErstelleRezept panCakeRezept = new ErstelleRezept(rezeptIdPanCake, panCakes, createZutatenListeFuerOmeletten(), createZubereitungOmelette());
        service.erstelleRezept(panCakeRezept);

        // then
        List<Rezept> rezepte = service.findeAlle();

        assertThat(rezepte, hasSize(2));
        rezepte.forEach(rezept -> assertNotNull(rezept.getRepository()));

        Rezept rezeptPacnakes = rezepte.stream().filter(rezept -> rezept.getName().equals(panCakes)).findFirst().get();
        String korrigierterRezeptName = "Pancakes";
        rezeptPacnakes.aendereName(korrigierterRezeptName);

        List<Rezept> rezeptList = service.findeAlle();
        assertThat(rezeptList.stream().anyMatch(rezept -> rezept.getName().equals(korrigierterRezeptName)), is(true));

    }

    @Test
    void rezept_finde_alle_valid() {
        RezeptId rezeptId = RezeptId.generate();
        String omeletten = "Omeletten";
        ErstelleRezept erstelleRezept = new ErstelleRezept(rezeptId, omeletten, createZutatenListeFuerOmeletten(), createZubereitungOmelette());
        service.erstelleRezept(erstelleRezept);

        String panCakes = "Pacnakes";
        RezeptId rezeptIdPanCake = RezeptId.generate();
        ErstelleRezept panCakeRezept = new ErstelleRezept(rezeptIdPanCake, panCakes, createZutatenListeFuerOmeletten(), createZubereitungOmelette());
        service.erstelleRezept(panCakeRezept);

        // then
        List<Rezept> rezepte = service.findeAlle();

        assertThat(rezepte, hasSize(2));
        rezepte.forEach(rezept -> assertNotNull(rezept.getRepository()));
    }

}
