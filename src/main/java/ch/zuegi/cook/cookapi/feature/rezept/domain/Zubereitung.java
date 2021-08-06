package ch.zuegi.cook.cookapi.feature.rezept.domain;

import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationError;
import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode
@Getter
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

    public void ergaenze(int index, String beschreibung) {
        beschreibungen.add(index, beschreibung);
    }

    public void entferne(int index) {
        beschreibungen.remove(index);
    }
}
