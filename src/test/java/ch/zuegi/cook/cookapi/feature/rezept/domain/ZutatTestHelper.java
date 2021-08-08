package ch.zuegi.cook.cookapi.feature.rezept.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ZutatTestHelper {

    public static final ZutatId ZUTATID_MEHL = ZutatId.generate();
    public static final ZutatId ZUTATID_EIER = ZutatId.generate();
    public static final ZutatId ZUTATID_SALZ = ZutatId.generate();
    public static final ZutatId ZUTATID_WASSER = ZutatId.generate();
    public static final ZutatId ZUTATID_MILCH = ZutatId.generate();
    public static final ZutatId ZUTATID_ZUCKER = ZutatId.generate();

    public static Zutat erstelleZutat(ZutatId zutatId, String name, Double menge,Einheit einheit) {
        return Zutat.erstelle(zutatId, name, menge, einheit);
    }

    public static List<Zutat> createZutatenListeFuerOmeletten() {
        return new ArrayList<>(Arrays.asList(
                Zutat.erstelle(ZUTATID_MEHL, "Mehl", 50d, Einheit.GRAMM),
                Zutat.erstelle(ZUTATID_EIER, "Eier", 1d, Einheit.STUECK),
                Zutat.erstelle(ZUTATID_SALZ, "Salz", 0.5d, Einheit.TEELOEFFEL),
                Zutat.erstelle(ZUTATID_WASSER, "Wasser", 50d, Einheit.MILLILITER),
                Zutat.erstelle(ZUTATID_MILCH, "Milch", 50d, Einheit.MILLILITER)
        ));
    }

    public static void assertOmeletten(Rezept rezept, int anzahlPersonen) {
        assertThat(rezept.getZutaten(), containsInAnyOrder(
                Zutat.erstelle(ZUTATID_MEHL, "Mehl", anzahlPersonen * 50d, Einheit.GRAMM),
                Zutat.erstelle(ZUTATID_EIER, "Eier", anzahlPersonen * 1d, Einheit.STUECK),
                Zutat.erstelle(ZUTATID_SALZ, "Salz", anzahlPersonen * 0.5d, Einheit.TEELOEFFEL),
                Zutat.erstelle(ZUTATID_WASSER, "Wasser", anzahlPersonen * 50d, Einheit.MILLILITER),
                Zutat.erstelle(ZUTATID_MILCH,"Milch", anzahlPersonen * 50d, Einheit.MILLILITER)));
    }

    public static void assertOmelettenMitZucker(Rezept rezept, int anzahlPersonen) {
        assertThat(rezept.getZutaten(), containsInAnyOrder(
                Zutat.erstelle(ZUTATID_MEHL, "Mehl", anzahlPersonen * 50d, Einheit.GRAMM),
                Zutat.erstelle(ZUTATID_EIER,"Eier", anzahlPersonen * 1d, Einheit.STUECK),
                Zutat.erstelle(ZUTATID_ZUCKER, "Zucker", 1d, Einheit.PRISE),
                Zutat.erstelle(ZUTATID_SALZ,"Salz", anzahlPersonen * 0.5d, Einheit.TEELOEFFEL),
                Zutat.erstelle(ZUTATID_WASSER,"Wasser", anzahlPersonen * 50d, Einheit.MILLILITER),
                Zutat.erstelle(ZUTATID_MILCH,"Milch", anzahlPersonen * 50d, Einheit.MILLILITER)));
    }
}
