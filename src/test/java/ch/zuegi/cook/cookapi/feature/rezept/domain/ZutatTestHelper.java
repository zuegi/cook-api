package ch.zuegi.cook.cookapi.feature.rezept.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ZutatTestHelper {

    public static Zutat erstelleZutat(String name, Double menge,Einheit einheit) {
        return Zutat.erstelle(name, menge, einheit);
    }

    public static List<Zutat> createZutatenListeFuerOmeletten() {
        return new ArrayList<>(Arrays.asList(
                Zutat.erstelle("Mehl", 50d, Einheit.GRAMM),
                Zutat.erstelle("Eier", 1d, Einheit.STUECK),
                Zutat.erstelle("Salz", 0.5d, Einheit.TEELOEFFEL),
                Zutat.erstelle("Wasser", 50d, Einheit.MILLILITER),
                Zutat.erstelle("Milch", 50d, Einheit.MILLILITER)
        ));
    }

    public static void assertOmeletten(Rezept rezept, int anzahlPersonen) {
        assertThat(rezept.getZutaten(), containsInAnyOrder(
                Zutat.erstelle("Mehl", anzahlPersonen * 50d, Einheit.GRAMM),
                Zutat.erstelle("Eier", anzahlPersonen * 1d, Einheit.STUECK),
                Zutat.erstelle("Salz", anzahlPersonen * 0.5d, Einheit.TEELOEFFEL),
                Zutat.erstelle("Wasser", anzahlPersonen * 50d, Einheit.MILLILITER),
                Zutat.erstelle("Milch", anzahlPersonen * 50d, Einheit.MILLILITER)));
    }

    public static void assertOmelettenMitZucker(Rezept rezept, int anzahlPersonen) {
        assertThat(rezept.getZutaten(), containsInAnyOrder(
                Zutat.erstelle("Mehl", anzahlPersonen * 50d, Einheit.GRAMM),
                Zutat.erstelle("Eier", anzahlPersonen * 1d, Einheit.STUECK),
                Zutat.erstelle("Zucker", 1d, Einheit.PRISE),
                Zutat.erstelle("Salz", anzahlPersonen * 0.5d, Einheit.TEELOEFFEL),
                Zutat.erstelle("Wasser", anzahlPersonen * 50d, Einheit.MILLILITER),
                Zutat.erstelle("Milch", anzahlPersonen * 50d, Einheit.MILLILITER)));
    }
}
