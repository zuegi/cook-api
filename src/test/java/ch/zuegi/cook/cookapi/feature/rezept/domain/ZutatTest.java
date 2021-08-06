package ch.zuegi.cook.cookapi.feature.rezept.domain;

import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationError;
import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
        assertThatExceptionOfType(BusinessValidationException.class)
                .isThrownBy(() -> Zutat.erstelle(null, 50d, Einheit.GRAMM))
                .withMessage(BusinessValidationError.ZUTAT_NAME_IST_ZWINGEND);
    }

    @Test
    void Zutat_erstelle_menge_null_invalid() {
        assertThatExceptionOfType(BusinessValidationException.class)
                .isThrownBy(() -> Zutat.erstelle("Mehl", null, Einheit.GRAMM))
                .withMessage(BusinessValidationError.ZUTAT_MENGE_IST_ZWINGEND);
    }

    @Test
    void Zutat_erstelle_einheit_null_invalid() {
        assertThatExceptionOfType(BusinessValidationException.class)
                .isThrownBy(() -> Zutat.erstelle("Mehl", 2d, null))
                .withMessage(BusinessValidationError.ZUTAT_EINHEIT_IST_ZWINGEND);
    }

}
