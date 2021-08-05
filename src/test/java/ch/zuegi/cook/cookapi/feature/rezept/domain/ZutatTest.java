package ch.zuegi.cook.cookapi.feature.rezept.domain;

import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class ZutatTest {


    @Test
    void Zutat_erstelle_berechne_valide() {
        Zutat mehl = Zutat.erstelle("Mehl", 50d, Einheit.GRAMM);
        int anzahlPersonen = 4;
        mehl.berechneMenge(anzahlPersonen);
        assertThat(mehl.getName(), is("Mehl"));
        assertThat(mehl.getMenge(), is(200d));
    }

    @Test
    void Zutat_erstelle_name_null_invalid() {
        Assertions.assertThrows(BusinessValidationException.class, () -> { Zutat.erstelle(null, 50d, Einheit.GRAMM);});
    }

    @Test
    void Zutat_erstelle_menge_null_invalid() {
        Assertions.assertThrows(BusinessValidationException.class, () -> { Zutat.erstelle("Mehl", null, Einheit.GRAMM);});
    }

    @Test
    void Zutat_erstelle_einheit_null_invalid() {
        Assertions.assertThrows(BusinessValidationException.class, () -> { Zutat.erstelle("Mehl", 2d, null);});
    }

}
