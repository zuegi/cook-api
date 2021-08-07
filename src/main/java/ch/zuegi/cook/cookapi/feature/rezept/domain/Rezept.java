package ch.zuegi.cook.cookapi.feature.rezept.domain;

import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationError;
import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationException;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

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

    // repository hier einbinden und zuerst abfragen ob es diese Rezept id bereits gibt??????
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
        rezept.speichere();
        return rezept;
    }

    private void speichere() {
        this.repository.add(this);
    }

    public void update(RezeptRepository repository) {
        repository.persist(this);
    }


    public void validiere() {
        if (StringUtils.isBlank(this.name)) {
            throw new BusinessValidationException(BusinessValidationError.REZEPT_NAME_IST_ZWINGEND);
        }
        getNullSafeStream(this.zutaten).forEach(Zutat::validiere);
        this.getZubereitung().validiere();
    }

    public void berechneMengenFuer(int anzahlPersonen) {
        if (anzahlPersonen == 0) {
            throw new BusinessValidationException(BusinessValidationError.REZEPT_ANZAHL_PERSONEN_DARF_NICHT_0_SEIN);
        }
        getNullSafeStream(this.zutaten).forEach(zutat -> zutat.berechneMenge(anzahlPersonen));
    }

    public void ergaenzeZubereitung(int index, ZubereitungsSchritt beschreibung) {
        this.getZubereitung().ergaenze(index,beschreibung);
    }

    public void entferneZubereitung(int index) {
        this.getZubereitung().entferne(index);
    }

    public void ergaenzeZutat(int index, Zutat zucker) {
        this.getZutaten().add(index, zucker);
    }

    public void entferneZutat(int index) {
        this.getZutaten().remove(index);
    }

    public void entferne() {
        this.getRepository().remove(this);
    }
}
