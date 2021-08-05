package ch.zuegi.cook.cookapi.feature.rezept.domain;

import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationError;
import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationException;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Zutat {
    String name;
    Double menge;
    Einheit einheit;

    public static Zutat erstelle(String name, Double menge, Einheit einheit) {
        Zutat zutat = new Zutat(name, menge, einheit);
        zutat.validiere();
        return zutat;
    }

    public void berechneMenge(int anzahlPersonen) {
        this.menge = this.menge * anzahlPersonen;
    }

    public void validiere() {
        if (StringUtils.isBlank(name)) {
            throw new BusinessValidationException(BusinessValidationError.ZUTAT_NAME_IST_ZWINGEND);
        }
        if (menge == null) {
            throw new BusinessValidationException(BusinessValidationError.ZUTAT_MENGE_IST_ZWINGEND);
        }
        if (einheit == null) {
            throw new BusinessValidationException(BusinessValidationError.ZUTAT_EINHEIT_IST_ZWINGEND);
        }

    }
}
