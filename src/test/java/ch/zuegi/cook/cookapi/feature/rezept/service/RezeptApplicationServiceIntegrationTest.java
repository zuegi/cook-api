package ch.zuegi.cook.cookapi.feature.rezept.service;

import ch.zuegi.cook.cookapi.AbstractIntegrationTest;
import ch.zuegi.cook.cookapi.feature.rezept.domain.Rezept;
import ch.zuegi.cook.cookapi.feature.rezept.domain.RezeptId;
import ch.zuegi.cook.cookapi.feature.rezept.infra.rest.ErstelleRezept;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static ch.zuegi.cook.cookapi.feature.rezept.domain.ZubereitungTestHelper.assertZubereitung;
import static ch.zuegi.cook.cookapi.feature.rezept.domain.ZubereitungTestHelper.createZubereitungOmelette;
import static ch.zuegi.cook.cookapi.feature.rezept.domain.ZutatTestHelper.assertOmeletten;
import static ch.zuegi.cook.cookapi.feature.rezept.domain.ZutatTestHelper.createZutatenListeFuerOmeletten;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class RezeptApplicationServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    RezeptApplicationService service;

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

}
