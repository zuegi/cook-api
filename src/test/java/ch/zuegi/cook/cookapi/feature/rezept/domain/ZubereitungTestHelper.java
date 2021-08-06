package ch.zuegi.cook.cookapi.feature.rezept.domain;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ZubereitungTestHelper {

    public static Zubereitung createZubereitungOmelette() {

        return Zubereitung.erstelle(new ArrayList<>(Arrays.asList(
                "Mehl, Salz, Eier, Milch und Wasser in einer Schüssel vermischen.",
                "Bei Zimmertemperatur 30 min ruhen lassen.",
                "Die Butter in einer beschichteten Bratpfanne erhitzen.",
                "Mit einer Schöpfkelle Teig für jeweils 1 Omelette hineingeben und gleichmässig verlaufen lassen.",
                "Omelette bei mittlerer Hitze beidseitig goldgelb braten.",
                "So weiterfahren, bis aus allem Teig Omeletten gemacht sind.")));

    }

    public static void assertZubereitung(Rezept rezept) {
        assertThat(rezept.getZubereitung().getBeschreibungen(), containsInAnyOrder(
                "Mehl, Salz, Eier, Milch und Wasser in einer Schüssel vermischen.",
                "Bei Zimmertemperatur 30 min ruhen lassen.",
                "Die Butter in einer beschichteten Bratpfanne erhitzen.",
                "Mit einer Schöpfkelle Teig für jeweils 1 Omelette hineingeben und gleichmässig verlaufen lassen.",
                "Omelette bei mittlerer Hitze beidseitig goldgelb braten.",
                "So weiterfahren, bis aus allem Teig Omeletten gemacht sind."

        ));
    }

    public static void assertZubereitungMitLiebe(Rezept rezept) {
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
}
