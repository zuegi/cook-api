package ch.zuegi.cook.cookapi.feature.rezept.domain;

import java.util.Arrays;
import java.util.LinkedList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ZubereitungTestHelper {

    public static Zubereitung createZubereitungOmelette() {

        return Zubereitung.erstelle(new LinkedList<>(Arrays.asList(
                new ZubereitungsSchritt("Mehl, Salz, Eier, Milch und Wasser in einer Schüssel vermischen."),
                new ZubereitungsSchritt("Bei Zimmertemperatur 30 min ruhen lassen."),
                new ZubereitungsSchritt("Die Butter in einer beschichteten Bratpfanne erhitzen."),
                new ZubereitungsSchritt("Mit einer Schöpfkelle Teig für jeweils 1 Omelette hineingeben und gleichmässig verlaufen lassen."),
                new ZubereitungsSchritt("Omelette bei mittlerer Hitze beidseitig goldgelb braten."),
                new ZubereitungsSchritt("So weiterfahren, bis aus allem Teig Omeletten gemacht sind."))));

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
