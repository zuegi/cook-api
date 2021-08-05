package ch.zuegi.cook.cookapi.feature.rezept.domain;

import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationError;
import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationException;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

@ToString
@EqualsAndHashCode
@Setter
public class Zubereitung {

    List<String> beschreibungen;

    public static Zubereitung erstelle(List<String> beschreibungen) {
        Zubereitung zubereitung = new Zubereitung();
        zubereitung.beschreibungen = beschreibungen;
        zubereitung.validiere();
        return zubereitung;
    }

    public void validiere() {
        if (this.beschreibungen.isEmpty()) {
            throw new BusinessValidationException(BusinessValidationError.ZUBEREITUNG_BESCHREIBUNG_IST_ZWINGEND);
        }
    }
}
