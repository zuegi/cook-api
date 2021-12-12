package ch.zuegi.cook.cookapi.feature.rezept.domain;

import java.util.Arrays;
import java.util.LinkedList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ZubereitungTestHelper {

    public static final String MEHL_SALZ_EIER_MILCH_UND_WASSER_IN_EINER_SCHÜSSEL_VERMISCHEN = "Mehl, Salz, Eier, Milch und Wasser in einer Schüssel vermischen.";
    public static final String BEI_ZIMMERTEMPERATUR_30_MIN_RUHEN_LASSEN = "Bei Zimmertemperatur 30 min ruhen lassen.";
    public static final String DIE_BUTTER_IN_EINER_BESCHICHTETEN_BRATPFANNE_ERHITZEN = "Die Butter in einer beschichteten Bratpfanne erhitzen.";
    public static final String MIT_EINER_SCHÖPFKELLE_TEIG_FÜR_JEWEILS_1_OMELETTE_HINEINGEBEN_UND_GLEICHMÄSSIG_VERLAUFEN_LASSEN = "Mit einer Schöpfkelle Teig für jeweils 1 Omelette hineingeben und gleichmässig verlaufen lassen.";
    public static final String OMELETTE_BEI_MITTLERER_HITZE_BEIDSEITIG_GOLDGELB_BRATEN = "Omelette bei mittlerer Hitze beidseitig goldgelb braten.";
    public static final String SO_WEITERFAHREN_BIS_AUS_ALLEM_TEIG_OMELETTEN_GEMACHT_SIND = "So weiterfahren, bis aus allem Teig Omeletten gemacht sind.";

    public static Zubereitung createZubereitungOmelette() {

        return Zubereitung.erstelle(new LinkedList<>(Arrays.asList(
                new ZubereitungsSchritt(MEHL_SALZ_EIER_MILCH_UND_WASSER_IN_EINER_SCHÜSSEL_VERMISCHEN),
                new ZubereitungsSchritt(BEI_ZIMMERTEMPERATUR_30_MIN_RUHEN_LASSEN),
                new ZubereitungsSchritt(DIE_BUTTER_IN_EINER_BESCHICHTETEN_BRATPFANNE_ERHITZEN),
                new ZubereitungsSchritt(MIT_EINER_SCHÖPFKELLE_TEIG_FÜR_JEWEILS_1_OMELETTE_HINEINGEBEN_UND_GLEICHMÄSSIG_VERLAUFEN_LASSEN),
                new ZubereitungsSchritt(OMELETTE_BEI_MITTLERER_HITZE_BEIDSEITIG_GOLDGELB_BRATEN),
                new ZubereitungsSchritt(SO_WEITERFAHREN_BIS_AUS_ALLEM_TEIG_OMELETTEN_GEMACHT_SIND))));

    }

    public static void assertZubereitung(Rezept rezept) {
        assertThat(rezept.getZubereitung().getBeschreibungen(), containsInAnyOrder(
                new ZubereitungsSchritt("Mehl, Salz, Eier, Milch und Wasser in einer Schüssel vermischen."),
                new ZubereitungsSchritt("Bei Zimmertemperatur 30 min ruhen lassen."),
                new ZubereitungsSchritt("Die Butter in einer beschichteten Bratpfanne erhitzen."),
                new ZubereitungsSchritt("Mit einer Schöpfkelle Teig für jeweils 1 Omelette hineingeben und gleichmässig verlaufen lassen."),
                new ZubereitungsSchritt("Omelette bei mittlerer Hitze beidseitig goldgelb braten."),
                new ZubereitungsSchritt("So weiterfahren, bis aus allem Teig Omeletten gemacht sind.")

        ));
    }

    public static void assertZubereitungMitLiebe(Rezept rezept) {
        assertThat(rezept.getZubereitung().getBeschreibungen(), containsInAnyOrder(
                new ZubereitungsSchritt("Mehl, Salz, Eier, Milch und Wasser in einer Schüssel vermischen."),
                new ZubereitungsSchritt("Bei Zimmertemperatur 30 min ruhen lassen."),
                new ZubereitungsSchritt("Mach es mit Liebe"),
                new ZubereitungsSchritt("Die Butter in einer beschichteten Bratpfanne erhitzen."),
                new ZubereitungsSchritt("Mit einer Schöpfkelle Teig für jeweils 1 Omelette hineingeben und gleichmässig verlaufen lassen."),
                new ZubereitungsSchritt("Omelette bei mittlerer Hitze beidseitig goldgelb braten."),
                new ZubereitungsSchritt("So weiterfahren, bis aus allem Teig Omeletten gemacht sind.")
        ));
    }
}
