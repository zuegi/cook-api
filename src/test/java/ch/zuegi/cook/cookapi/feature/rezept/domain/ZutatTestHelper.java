package ch.zuegi.cook.cookapi.feature.rezept.domain;

public class ZutatTestHelper {

    public final static Zutat BUTTER = Zutat.erstelle("Butter", 500.0d, Einheit.GRAMM);
    public final static Zutat PUDERZUCKER = Zutat.erstelle("Puderzucker", 250.0d, Einheit.GRAMM);
    public final static Zutat MEHL = Zutat.erstelle("Mehl", 1000.0d, Einheit.GRAMM);
    public final static Zutat EIER = Zutat.erstelle("Eier", 3d, Einheit.STUECK);
    public final static Zutat VANILLEZUCKER = Zutat.erstelle("Vanillezucker", 4d, Einheit.GRAMM);
    public final static Zutat BACKPULVER = Zutat.erstelle("Backpulver", 4d, Einheit.TEELOEFFEL);
    public final static Zutat MILCH = Zutat.erstelle("Milch", 2d, Einheit.ESSLOEFFEL);
    public final static Zutat ZITRONE = Zutat.erstelle("Zitrone", 1d, Einheit.SPRITZER);
}
