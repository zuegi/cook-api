package ch.zuegi.cook.cookapi.feature.rezept.domain;

import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationError;
import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ch.zuegi.cook.cookapi.feature.rezept.domain.ZubereitungTestHelper.createZubereitungOmelette;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class RezeptTest {

    @Test
    void Rezept_erstelle_valid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String rezeptName = "Omelette";
        Rezept rezept = Rezept.erstelle(rezeptName, zutatenListeFuerOmeletten, zubereitung);

        int anzahlPersonen = 1;
        assertThat(rezept.getName(), is(rezeptName));
        assertThat(rezept.zutaten, hasSize(zutatenListeFuerOmeletten.size()));
        assertThat(rezept.getZubereitung().getBeschreibungen(), hasSize(zubereitung.getBeschreibungen().size()));
        assertOmeletten(rezept, anzahlPersonen);
    }

    @Test
    void rezept_erstelle_name_invalid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        assertThatExceptionOfType(BusinessValidationException.class)
                .isThrownBy(() -> Rezept.erstelle(null, zutatenListeFuerOmeletten, zubereitung))
                .withMessage(BusinessValidationError.REZEPT_NAME_IST_ZWINGEND);
    }


    @Test
    void Rezept_erhoehe_anzahl_personen_valid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String omeletten = "Omeletten";
        Rezept rezept = Rezept.erstelle(omeletten, zutatenListeFuerOmeletten, zubereitung);

        int anzahlPersonen = 4;
        rezept.berechneMengenFuer(anzahlPersonen);
        assertThat(rezept.getName(), is(omeletten));
        assertOmeletten(rezept, anzahlPersonen);

    }

    @Test
    void rezept_ergaenze_zutat_valid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String omeletten = "Omeletten";
        Rezept rezept = Rezept.erstelle(omeletten, zutatenListeFuerOmeletten, zubereitung);

        int index = 2;
        Zutat zucker = Zutat.erstelle("Zucker", 1d, Einheit.PRISE);
        rezept.ergaenzeZutat(index, zucker);

        assertOmelettenMitZucker(rezept, 1);

    }
    @Test
    void rezept_entferne_zutat_valid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String omeletten = "Omeletten";
        Rezept rezept = Rezept.erstelle(omeletten, zutatenListeFuerOmeletten, zubereitung);

        int index = 2;
        Zutat zucker = Zutat.erstelle("Zucker", 1d, Einheit.PRISE);
        rezept.ergaenzeZutat(index, zucker);

        assertOmelettenMitZucker(rezept, 1);

        rezept.entferneZutat(index);
        assertOmeletten(rezept,1);
    }

    @Test
    void Rezept_ergaenze_zubereitung_valid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String omeletten = "Omeletten";
        Rezept rezept = Rezept.erstelle(omeletten, zutatenListeFuerOmeletten, zubereitung);

        int index = 2;
        String beschreibung = "Mach es mit Liebe";
        rezept.ergaenzeZubereitung(index, beschreibung);

        assertZubereitungMitLiebe(rezept);
        assertThat(rezept.getZubereitung().getBeschreibungen().get(2), is(beschreibung));

    }

    @Test
    void rezept_entferne_zubereitungsbeschreibung_valid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String omeletten = "Omeletten";
        Rezept rezept = Rezept.erstelle(omeletten, zutatenListeFuerOmeletten, zubereitung);

        int index = 2;
        String beschreibung = "Mach es mit Liebe";
        rezept.ergaenzeZubereitung(index, beschreibung);

        assertZubereitungMitLiebe(rezept);
        assertThat(rezept.getZubereitung().getBeschreibungen().get(2), is(beschreibung));

        rezept.entferneZubereitung(index);
        assertZubereitung(rezept);
    }

    private void assertZubereitung(Rezept rezept) {
        assertThat(rezept.getZubereitung().getBeschreibungen(), containsInAnyOrder(
                "Mehl, Salz, Eier, Milch und Wasser in einer Schüssel vermischen.",
                "Bei Zimmertemperatur 30 min ruhen lassen.",
                "Die Butter in einer beschichteten Bratpfanne erhitzen.",
                "Mit einer Schöpfkelle Teig für jeweils 1 Omelette hineingeben und gleichmässig verlaufen lassen.",
                "Omelette bei mittlerer Hitze beidseitig goldgelb braten.",
                "So weiterfahren, bis aus allem Teig Omeletten gemacht sind."

        ));
    }

    private void assertZubereitungMitLiebe(Rezept rezept) {
        assertThat(rezept.getZubereitung().getBeschreibungen(), containsInAnyOrder(
                "Mehl, Salz, Eier, Milch und Wasser in einer Schüssel vermischen.",
                "Bei Zimmertemperatur 30 min ruhen lassen.",
                "Mach es mit Liebe",
                "Die Butter in einer beschichteten Bratpfanne erhitzen.",
                "Mit einer Schöpfkelle Teig für jeweils 1 Omelette hineingeben und gleichmässig verlaufen lassen.",
                "Omelette bei mittlerer Hitze beidseitig goldgelb braten.",
                "So weiterfahren, bis aus allem Teig Omeletten gemacht sind."

        ));
    }


    private List<Zutat> createZutatenListeFuerOmeletten() {
        Zutat mehl = Zutat.erstelle("Mehl", 50d, Einheit.GRAMM);
        Zutat eier = Zutat.erstelle("Eier", 1d, Einheit.STUECK);
        Zutat salz = Zutat.erstelle("Salz", 0.5d, Einheit.TEELOEFFEL);
        Zutat wasser = Zutat.erstelle("Wasser", 50d, Einheit.MILLILITER);
        Zutat milch = Zutat.erstelle("Milch", 50d, Einheit.MILLILITER);
        return new ArrayList<>(Arrays.asList(mehl, eier, salz, wasser, milch));
    }


    private void assertOmeletten(Rezept rezept, int anzahlPersonen) {
        assertThat(rezept.getZutaten(), containsInAnyOrder(
                Zutat.erstelle("Mehl", anzahlPersonen * 50d, Einheit.GRAMM),
                Zutat.erstelle("Eier", anzahlPersonen * 1d, Einheit.STUECK),
                Zutat.erstelle("Salz", anzahlPersonen * 0.5d, Einheit.TEELOEFFEL),
                Zutat.erstelle("Wasser", anzahlPersonen * 50d, Einheit.MILLILITER),
                Zutat.erstelle("Milch", anzahlPersonen * 50d, Einheit.MILLILITER)));
    }

    private void assertOmelettenMitZucker(Rezept rezept, int anzahlPersonen) {
        assertThat(rezept.getZutaten(), containsInAnyOrder(
                Zutat.erstelle("Mehl", anzahlPersonen * 50d, Einheit.GRAMM),
                Zutat.erstelle("Eier", anzahlPersonen * 1d, Einheit.STUECK),
                Zutat.erstelle("Zucker", 1d, Einheit.PRISE),
                Zutat.erstelle("Salz", anzahlPersonen * 0.5d, Einheit.TEELOEFFEL),
                Zutat.erstelle("Wasser", anzahlPersonen * 50d, Einheit.MILLILITER),
                Zutat.erstelle("Milch", anzahlPersonen * 50d, Einheit.MILLILITER)));
    }

}
