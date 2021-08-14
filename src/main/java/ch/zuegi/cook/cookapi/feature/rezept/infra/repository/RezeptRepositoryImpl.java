package ch.zuegi.cook.cookapi.feature.rezept.infra.repository;

import ch.zuegi.cook.cookapi.MeineRefactoringKlasse;
import ch.zuegi.cook.cookapi.feature.rezept.domain.Rezept;
import ch.zuegi.cook.cookapi.feature.rezept.domain.RezeptId;
import ch.zuegi.cook.cookapi.feature.rezept.domain.RezeptRepository;
import ch.zuegi.cook.cookapi.shared.persistence.Persistence;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class RezeptRepositoryImpl implements RezeptRepository {

    private Persistence persistence;

    @Override
    public Rezept findById(RezeptId rezeptId) {
        return persistence.dataRoot().getRezeptList().stream()
                .filter(rezept -> rezept.getRezeptId().equals(rezeptId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void add(Rezept rezept) {
        persistence.dataRoot().getRezeptList().add(rezept);
        persistence.store(persistence.dataRoot().getRezeptList());
    }

    @Override
    public void persist(Rezept rezept) {
        persistence.store(rezept);
    }

    @Override
    public void remove(Rezept rezept) {
        persistence.dataRoot().getRezeptList().remove(rezept);
        persistence.store(persistence.dataRoot().getRezeptList());
    }

    @Override
    public List<Rezept> findAll() {
        return persistence.dataRoot().getRezeptList();
    }

    public void add(MeineRefactoringKlasse meineRefactoringKlasse) {
         persistence.dataRoot().getRefactoringKlasseList().add(meineRefactoringKlasse);
        persistence.store(persistence.dataRoot().getRefactoringKlasseList());
    }
}
