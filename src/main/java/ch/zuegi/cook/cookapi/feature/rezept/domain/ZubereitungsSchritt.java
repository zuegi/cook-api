package ch.zuegi.cook.cookapi.feature.rezept.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class ZubereitungsSchritt {
    String zubereitungsSchritt;

    public void aendereZubereitungsSchritt(String beschreibung) {
        this.zubereitungsSchritt = beschreibung;
    }
}
