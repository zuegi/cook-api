package ch.zuegi.cook.cookapi.feature.rezept.domain;

import lombok.Value;

import java.util.UUID;

@Value
public class RezeptId {
    UUID id;

    public static RezeptId generate() {
        return new RezeptId(UUID.randomUUID());
    }
}
