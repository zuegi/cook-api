package ch.zuegi.cook.cookapi.feature.rezept.domain;

import lombok.Value;

import java.util.UUID;

@Value
public class ZutatId {
    UUID id;

    public static ZutatId generate() {
        return new ZutatId(UUID.randomUUID());
    }
}
