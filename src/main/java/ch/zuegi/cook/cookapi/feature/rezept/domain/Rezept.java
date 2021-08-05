package ch.zuegi.cook.cookapi.feature.rezept.domain;

import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationError;
import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationException;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import static ch.zuegi.cook.cookapi.shared.utils.StreamUtil.getNullSafeStream;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Rezept {

    String name;
    List<Zutat> zutaten;
    Zubereitung zubereitung;

    public static Rezept erstelle(String name, List<Zutat> zutaten, Zubereitung zubereitung) {
        Rezept rezept = new Rezept();
        rezept.name = name;
        rezept.zutaten = zutaten;
        rezept.zubereitung = zubereitung;
        rezept.validiere();
        return rezept;
    }

    public void validiere() {
        if (StringUtils.isBlank(this.name)) {
            throw new BusinessValidationException(BusinessValidationError.REZEPT_NAME_IST_ZWINGEND);
        }
        getNullSafeStream(this.zutaten).forEach(Zutat::validiere);
        this.getZubereitung().validiere();
    }

    public void berechneMengenFuer(int anzahlPersonen) {
        getNullSafeStream(this.zutaten).forEach(zutat -> zutat.berechneMenge(anzahlPersonen));
    }
}
