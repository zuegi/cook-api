package ch.zuegi.cook.cookapi.feature.rezept.service;

import ch.zuegi.cook.cookapi.feature.rezept.domain.Rezept;
import ch.zuegi.cook.cookapi.feature.rezept.domain.RezeptId;
import ch.zuegi.cook.cookapi.feature.rezept.infra.repository.RezeptRepositoryImpl;
import ch.zuegi.cook.cookapi.feature.rezept.infra.rest.ErstelleRezept;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RezeptApplicationService {

    RezeptRepositoryImpl repository;

    public void erstelleRezept(ErstelleRezept erstelleRezpet) {
        Rezept.erstelle(repository, erstelleRezpet.getRezeptId(), erstelleRezpet.getName(), erstelleRezpet.getZutaten(), erstelleRezpet.getZubereitung());
    }

    public Rezept findByRezeptId(RezeptId rezeptId) {
        return repository.findById(rezeptId);
    }
}
