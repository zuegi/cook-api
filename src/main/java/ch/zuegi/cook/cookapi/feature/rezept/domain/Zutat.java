package ch.zuegi.cook.cookapi.feature.rezept.domain;

import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationError;
import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationException;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import static ch.zuegi.cook.cookapi.shared.Validations.validateNotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Zutat {
    ZutatId zutatId;
    String name;
    Double menge;
    Einheit einheit;

    public static Zutat erstelle(ZutatId zutatId, String name, Double menge, Einheit einheit) {
        validateNotNull(zutatId, BusinessValidationError.ZUTAT_ZUTATID_IST_ZWINGEND);
        Zutat zutat = new Zutat(zutatId, name, menge, einheit);
        zutat.validiere();
        return zutat;
    }

    public void berechneMenge(int anzahlPersonen) {
        this.menge = this.menge * anzahlPersonen;
    }

    public void aendereName(String name) {
        this.name = name;
        this.validiere();
    }

    public void aendereMenge(Double menge) {
        this.menge = menge;
        this.validiere();
    }

    public void aendereEinheit(Einheit einheit) {
        this.einheit = einheit;
        this.validiere();
    }

    public void validiere() {
        if (StringUtils.isBlank(name)) {
            throw new BusinessValidationException(BusinessValidationError.ZUTAT_NAME_IST_ZWINGEND);
        }
        validateNotNull(menge, BusinessValidationError.ZUTAT_MENGE_IST_ZWINGEND);
        validateNotNull(einheit, BusinessValidationError.ZUTAT_EINHEIT_IST_ZWINGEND);

    }
}
