package ch.zuegi.cook.cookapi.feature.rezept.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ZutatTestHelper {

    public final static Zutat BUTTER = Zutat.erstelle("Butter", 25.0d, Einheit.GRAMM);
    public final static Zutat PUDERZUCKER = Zutat.erstelle("Puderzucker", 250.0d, Einheit.GRAMM);
    public final static Zutat MEHL = Zutat.erstelle("Mehl", 50.0d, Einheit.GRAMM);
    public final static Zutat EIER = Zutat.erstelle("Eier", 1d, Einheit.STUECK);
    public final static Zutat VANILLEZUCKER = Zutat.erstelle("Vanillezucker", 4d, Einheit.GRAMM);
    public final static Zutat BACKPULVER = Zutat.erstelle("Backpulver", 4d, Einheit.TEELOEFFEL);
    public final static Zutat MILCH = Zutat.erstelle("Milch", 50d, Einheit.MILLILITER);
    public final static Zutat ZITRONE = Zutat.erstelle("Zitrone", 1d, Einheit.SPRITZER);
     public final static Zutat SALZ = Zutat.erstelle("Salz", 0.5d, Einheit.TEELOEFFEL);
     public final static Zutat WASSER = Zutat.erstelle("Wasser", 50d, Einheit.MILLILITER);

    public static List<Zutat> createZutatenListeFuerOmeletten() {
        return new ArrayList<>(Arrays.asList(MEHL, EIER, SALZ, WASSER, MILCH));
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
