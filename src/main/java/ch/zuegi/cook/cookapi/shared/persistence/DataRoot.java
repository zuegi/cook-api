package ch.zuegi.cook.cookapi.shared.persistence;

import ch.zuegi.cook.cookapi.MeineRefactoringKlasse;
import ch.zuegi.cook.cookapi.feature.rezept.domain.Rezept;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
public class DataRoot {
    private List<Rezept> rezeptList  = new ArrayList<>();
    private List<MeineRefactoringKlasse> refactoringKlasseList = new ArrayList<>();
}
