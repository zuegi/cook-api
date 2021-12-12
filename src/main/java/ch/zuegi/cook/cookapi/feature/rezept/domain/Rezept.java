package ch.zuegi.cook.cookapi.feature.rezept.domain;

import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationError;
import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationException;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static ch.zuegi.cook.cookapi.shared.Validations.validateNotNull;
import static ch.zuegi.cook.cookapi.shared.Validations.validateNull;
import static ch.zuegi.cook.cookapi.shared.utils.StreamUtil.getNullSafeStream;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Rezept {

    transient RezeptRepository repository;
    RezeptId rezeptId;
    String name;
    List<Zutat> zutaten;
    Zubereitung zubereitung;

    public static Rezept erstelle(RezeptRepository repository, RezeptId rezeptId, String name, List<Zutat> zutaten, Zubereitung zubereitung) {
        validateNotNull(rezeptId, BusinessValidationError.REZEPT_REZEPTID_IST_ZWINGEND);
        validateNotNull(repository, BusinessValidationError.REPISOTRY_NICHT_DEFINIERT);
        validateNull(repository.findById(rezeptId), BusinessValidationError.REZEPTID_BEREITS_VERWENDET);

        Rezept rezept = new Rezept();
        rezept.repository = repository;
        rezept.rezeptId = rezeptId;
        rezept.name = name;
        rezept.zutaten = zutaten;
        rezept.zubereitung = zubereitung;
        rezept.validiere();
        // speichere das Rezept
        rezept.erstelle();
        return rezept;
    }

    private void erstelle() {
        this.validiere();
        repository.add(this);
    }

    private void speichere() {
        this.validiere();
        repository.persist(this);
    }

    public static Rezept findeMitRezeptId(RezeptRepository repository, RezeptId rezeptId) {
        validateNotNull(rezeptId, BusinessValidationError.REZEPT_REZEPTID_IST_ZWINGEND);
        validateNotNull(repository, BusinessValidationError.REPISOTRY_NICHT_DEFINIERT);
        Rezept rezept = repository.findById(rezeptId);
        rezept.ergaenzeRepository(repository);
        return rezept;
    }

    void ergaenzeRepository(RezeptRepository repository) {
        this.repository = repository;
        this.validiere();
    }

    public static List<Rezept> findeAlleRezepte(RezeptRepository repository) {
        validateNotNull(repository, BusinessValidationError.REPISOTRY_NICHT_DEFINIERT);
        List<Rezept> all = repository.findAll();
        all.forEach(rezept -> rezept.ergaenzeRepository(repository));
        return all;
    }


    public void aendereName(String name) {
        this.name = name;
        validiereUndSpeichere();
    }

    public void validiere() {
        if (StringUtils.isBlank(this.name)) {
            throw new BusinessValidationException(BusinessValidationError.REZEPT_NAME_IST_ZWINGEND);
        }
        validateNotNull(repository, BusinessValidationError.REPISOTRY_NICHT_DEFINIERT);
        getNullSafeStream(this.zutaten).forEach(Zutat::validiere);
        this.getZubereitung().validiere();
    }

    public void entferne() {
        repository.remove(this);
    }

    public void berechneMengenFuer(int anzahlPersonen) {
        if (anzahlPersonen == 0) {
            throw new BusinessValidationException(BusinessValidationError.REZEPT_ANZAHL_PERSONEN_DARF_NICHT_0_SEIN);
        }
        getNullSafeStream(this.zutaten).forEach(zutat -> zutat.berechneMenge(anzahlPersonen));
    }

    public void ergaenzeZubereitung(int index, ZubereitungsSchritt beschreibung) {
        this.getZubereitung().ergaenze(index, beschreibung);
    }

    public void entferneZubereitung(int index) {
        this.getZubereitung().entferne(index);
    }

    public void ergaenzeZutat(int index, Zutat zucker) {
        this.getZutaten().add(index, zucker);
        validiereUndSpeichere();
    }

    public void entferneZutat(int index) {
        this.getZutaten().remove(index);
        validiereUndSpeichere();
    }


    public void aendereZutatName(ZutatId zutatId, String name) {
        validateNotNull(zutatId, BusinessValidationError.ZUTAT_ZUTATID_IST_ZWINGEND);
        findZutatById(zutatId).aendereName(name);
        validiereUndSpeichere();
    }


    public void aendereZutatMenge(ZutatId zutatId, double menge) {
        validateNotNull(zutatId, BusinessValidationError.ZUTAT_ZUTATID_IST_ZWINGEND);
        findZutatById(zutatId).aendereMenge(menge);
        validiereUndSpeichere();
    }

    private void validiereUndSpeichere() {
        this.validiere();
        this.speichere();
    }

    public void aendereZutatEinheit(ZutatId zutatId, Einheit einheit) {
        validateNotNull(zutatId, BusinessValidationError.ZUTAT_ZUTATID_IST_ZWINGEND);
        findZutatById(zutatId).aendereEinheit(einheit);
        validiereUndSpeichere();
    }

    public void aendereZutatPosition(int newIndex, ZutatId zutatId) {
        if (newIndex > this.getZutaten().size()) {
            throw new BusinessValidationException(BusinessValidationError.ZUTAT_INDEX_EXISTIERT_NICHT);
        }
        validateNotNull(zutatId, BusinessValidationError.ZUTAT_ZUTATID_IST_ZWINGEND);

        Zutat zutat = findZutatById(zutatId);

        OptionalInt indexOpt = IntStream.range(0, this.getZutaten().size())
                .filter(index -> zutat.equals(this.getZutaten().get(index)))
                .findFirst();

        if (indexOpt.isPresent()) {
            this.getZutaten().remove(indexOpt.getAsInt());
            this.getZutaten().add(newIndex, zutat);
        }
        validiereUndSpeichere();
    }

    private Zutat findZutatById(ZutatId zutatId) {
        return this.getZutaten().stream().filter(zutat -> zutat.getZutatId().equals(zutatId)).findFirst().orElseThrow(() ->
                new BusinessValidationException(BusinessValidationError.ZUTAT_MIT_ZUTATID_NICHT_GEFUNDEN)
        );
    }
}
