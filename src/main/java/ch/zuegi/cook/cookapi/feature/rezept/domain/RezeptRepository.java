package ch.zuegi.cook.cookapi.feature.rezept.domain;

import java.util.List;

public interface RezeptRepository {
    Rezept findById(RezeptId repository);

    void add(Rezept rezept);

    void persist(Rezept rezept);

    void remove(Rezept rezept);

    List<Rezept> findAll();
}
