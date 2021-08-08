package ch.zuegi.cook.cookapi.shared.exception;

public class BusinessValidationError {
    public static final String ZUTAT_NAME_IST_ZWINGEND = "Der Name der Zutat ist zwingen";
    public static final String ZUTAT_MENGE_IST_ZWINGEND = "Die Menge der Zutat ist zwingend";
    public static final String ZUTAT_EINHEIT_IST_ZWINGEND = "Die Einheit der Zutat ist zwingend";
    public static final String REZEPT_NAME_IST_ZWINGEND = "Der Name des Rezepts ist zwingend";
    public static final String ZUBEREITUNG_BESCHREIBUNG_IST_ZWINGEND = "Die Beschreibung der Zubereitung ist zwingend";
    public static final String REZEPT_REZEPTID_IST_ZWINGEND = "Die RezeptId des Rezepts ist zwingend";
    public static final String REZEPT_ANZAHL_PERSONEN_DARF_NICHT_0_SEIN = "Die Anzahl Personen darf nicht 0 sein";
    public static final String REZEPTID_BEREITS_VERWENDET = "Die RezeptId wird bereits verwendet";
    public static final String REPISOTRY_NICHT_DEFINIERT = "Das Rezept Repository ist nicht definiertz";
    public static final String ZUTAT_INDEX_EXISTIERT_NICHT = "Der Index in der Zutatenliste existiert nicht";
    public static final String ZUTAT_ZUTATID_IST_ZWINGEND = "Die ZutatId ist zwingend";
    public static final String ZUTAT_MIT_ZUTATID_NICHT_GEFUNDEN = "Zutat mit ZutatId nicht gefunden";
}
