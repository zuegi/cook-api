package ch.zuegi.cook.cookapi.feature.rezept.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class RezeptTest {

    @Test
    void Rezept_erstelle_valide() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String rezeptName = "Omelette";
        Rezept rezept = Rezept.erstelle(rezeptName, zutatenListeFuerOmeletten, zubereitung);

        int anzahlPersonen = 1;
        assertThat(rezept.getName(), is(rezeptName));
        assertThat(rezept.zutaten, hasSize(zutatenListeFuerOmeletten.size()));
        assertOmeletten(rezept, anzahlPersonen);
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


    private Zubereitung createZubereitungOmelette() {

        return Zubereitung.erstelle(Arrays.asList(
                "Mehl, Salz, Eier, Milch und Wasser in einer Schüssel vermischen.",
                "Bei Zimmertemperatur 30 min ruhen lassen.",
                "Die Butter in einer beschichteten Bratpfanne erhitzen.",
                "Mit einer Schöpfkelle Teig für jeweils 1 Omelette hineingeben und gleichmässig verlaufen lassen.",
                "Omelette bei mittlerer Hitze beidseitig goldgelb braten.",
                "So weiterfahren, bis aus allem Teig Omeletten gemacht sind."));

    }

    private List<Zutat> createZutatenListeFuerOmeletten() {
        Zutat mehl = Zutat.erstelle("Mehl", 50d, Einheit.GRAMM);
        Zutat eier = Zutat.erstelle("Eier", 1d, Einheit.STUECK);
        Zutat salz = Zutat.erstelle("Salz", 0.5d, Einheit.TEELOEFFEL);
        Zutat wasser = Zutat.erstelle("Wasser", 50d, Einheit.MILLILITER);
        Zutat milch = Zutat.erstelle("Milch", 50d, Einheit.MILLILITER);
        return Arrays.asList(mehl, eier, salz, wasser, milch);
    }

    private void assertOmeletten(Rezept rezept, int anzahlPersonen) {
        assertThat(rezept.getZutaten(), containsInAnyOrder(
                Zutat.erstelle("Mehl", anzahlPersonen * 50d, Einheit.GRAMM),
                Zutat.erstelle("Eier", anzahlPersonen * 1d, Einheit.STUECK),
                Zutat.erstelle("Salz", anzahlPersonen * 0.5d, Einheit.TEELOEFFEL),
                Zutat.erstelle("Wasser", anzahlPersonen * 50d, Einheit.MILLILITER),
                Zutat.erstelle("Milch", anzahlPersonen * 50d, Einheit.MILLILITER)));
    }

}
