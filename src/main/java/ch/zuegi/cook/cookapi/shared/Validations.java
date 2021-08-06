package ch.zuegi.cook.cookapi.shared;

import ch.zuegi.cook.cookapi.feature.rezept.domain.Rezept;
import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationException;

public class Validations {

    public static void validateNotNull(Object b, String reason) {
        if (b == null) {
            throw new BusinessValidationException(reason);
        }
    }

    public static void validateNull(Object b, String reason) {
        if (b != null) {
            throw new BusinessValidationException(reason);
        }
    }
}
