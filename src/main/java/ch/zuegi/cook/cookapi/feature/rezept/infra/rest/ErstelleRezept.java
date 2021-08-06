package ch.zuegi.cook.cookapi.feature.rezept.infra.rest;

import ch.zuegi.cook.cookapi.feature.rezept.domain.RezeptId;
import ch.zuegi.cook.cookapi.feature.rezept.domain.Zubereitung;
import ch.zuegi.cook.cookapi.feature.rezept.domain.Zutat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErstelleRezept {
    RezeptId rezeptId;
    String name;
    List<Zutat> zutaten;
    Zubereitung zubereitung;
}
