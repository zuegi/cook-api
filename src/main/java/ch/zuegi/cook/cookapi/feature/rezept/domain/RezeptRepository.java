package ch.zuegi.cook.cookapi.feature.rezept.domain;

public interface RezeptRepository {
    Rezept findById(RezeptId repository);

    void add(Rezept rezept);

    void persist(Rezept rezept);

    void remove(Rezept rezept);
}
