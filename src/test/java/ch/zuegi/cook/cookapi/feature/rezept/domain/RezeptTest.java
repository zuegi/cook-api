package ch.zuegi.cook.cookapi.feature.rezept.domain;

import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationError;
import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static ch.zuegi.cook.cookapi.feature.rezept.domain.ZubereitungTestHelper.*;
import static ch.zuegi.cook.cookapi.feature.rezept.domain.ZutatTestHelper.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RezeptTest {

    @Mock
    RezeptRepository repository;

    @BeforeEach
    void initMock() {
        assertNotNull(repository);
        /*
            Please note the use of Mockito.lenient() here. Mockito throws an UnsupportedStubbingException,
            when an initialised mock is not called by one of the test methods during execution.
            We can avoid this strict stub checking by using this method when initialising the mocks.
         */
        lenient().when(repository.findById(Mockito.any(RezeptId.class))).thenReturn(null);
    }

    @Test
    void Rezept_erstelle_valid() {
        // given
        int anzahlPersonen = 1;
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String rezeptName = "Omelette";
        RezeptId rezeptId = RezeptId.generate();
        // when
        Rezept rezept = Rezept.erstelle(repository, rezeptId, rezeptName, zutatenListeFuerOmeletten, zubereitung);
        // then
        verify(rezept.getRepository()).add(rezept);
        assertThat(rezept.getName(), is(rezeptName));
        assertThat(rezept.getRezeptId(), is(rezeptId));
        assertThat(rezept.zutaten, hasSize(zutatenListeFuerOmeletten.size()));
        assertThat(rezept.getZubereitung().getBeschreibungen(), hasSize(zubereitung.getBeschreibungen().size()));
        assertOmeletten(rezept, anzahlPersonen);
    }

    @Test
    void rezept_erstelle_name_invalid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        RezeptId rezeptId = RezeptId.generate();
        assertThatExceptionOfType(BusinessValidationException.class)
                .isThrownBy(() -> Rezept.erstelle(repository, rezeptId, null, zutatenListeFuerOmeletten, zubereitung))
                .withMessage(BusinessValidationError.REZEPT_NAME_IST_ZWINGEND);
    }

    @Test
    void rezept_erstelle_rezeptid_invalid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        assertThatExceptionOfType(BusinessValidationException.class)
                .isThrownBy(() -> Rezept.erstelle(repository, null, "name", zutatenListeFuerOmeletten, zubereitung))
                .withMessage(BusinessValidationError.REZEPT_REZEPTID_IST_ZWINGEND);
    }


    @Test
    void Rezept_erhoehe_anzahl_personen_valid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String omeletten = "Omeletten";
        RezeptId rezeptId = RezeptId.generate();
        Rezept rezept = Rezept.erstelle(repository, rezeptId, omeletten, zutatenListeFuerOmeletten, zubereitung);
        verify(rezept.getRepository()).add(rezept);

        int anzahlPersonen = 4;
        rezept.berechneMengenFuer(anzahlPersonen);

        assertThat(rezept.getName(), is(omeletten));
        assertOmeletten(rezept, anzahlPersonen);

    }

    @Test
    void Rezept_erhoehe_anzahl_personen_0_invalid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String omeletten = "Omeletten";
        RezeptId rezeptId = RezeptId.generate();
        Rezept rezept = Rezept.erstelle(repository, rezeptId, omeletten, zutatenListeFuerOmeletten, zubereitung);
        verify(rezept.getRepository()).add(rezept);

        int anzahlPersonen = 0;
        assertThatExceptionOfType(BusinessValidationException.class)
                .isThrownBy(() -> rezept.berechneMengenFuer(anzahlPersonen))
                .withMessage(BusinessValidationError.REZEPT_ANZAHL_PERSONEN_DARF_NICHT_0_SEIN);
    }

    @Test
    void rezept_ergaenze_zutat_valid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String omeletten = "Omeletten";
        RezeptId rezeptId = RezeptId.generate();
        Rezept rezept = Rezept.erstelle(repository, rezeptId, omeletten, zutatenListeFuerOmeletten, zubereitung);
        verify(rezept.getRepository()).add(rezept);

        int index = 2;
        Zutat zucker = Zutat.erstelle("Zucker", 1d, Einheit.PRISE);
        rezept.ergaenzeZutat(index, zucker);

        assertOmelettenMitZucker(rezept, 1);

    }

    @Test
    void rezept_entferne_zutat_valid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String omeletten = "Omeletten";
        RezeptId rezeptId = RezeptId.generate();
        Rezept rezept = Rezept.erstelle(repository, rezeptId, omeletten, zutatenListeFuerOmeletten, zubereitung);
        verify(rezept.getRepository()).add(rezept);

        int index = 2;
        Zutat zucker = Zutat.erstelle("Zucker", 1d, Einheit.PRISE);
        rezept.ergaenzeZutat(index, zucker);

        assertOmelettenMitZucker(rezept, 1);

        rezept.entferneZutat(index);
        assertOmeletten(rezept, 1);
    }

    @Test
    void Rezept_ergaenze_zubereitung_valid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String omeletten = "Omeletten";
        RezeptId rezeptId = RezeptId.generate();
        Rezept rezept = Rezept.erstelle(repository, rezeptId, omeletten, zutatenListeFuerOmeletten, zubereitung);
        verify(rezept.getRepository()).add(rezept);

        int index = 2;
        String beschreibung = "Mach es mit Liebe";
        rezept.ergaenzeZubereitung(index, beschreibung);

        assertZubereitungMitLiebe(rezept);
        assertThat(rezept.getZubereitung().getBeschreibungen().get(2), is(beschreibung));

    }

    @Test
    void rezept_entferne_zubereitungsbeschreibung_valid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String omeletten = "Omeletten";
        RezeptId rezeptId = RezeptId.generate();
        Rezept rezept = Rezept.erstelle(repository, rezeptId, omeletten, zutatenListeFuerOmeletten, zubereitung);
        verify(rezept.getRepository()).add(rezept);

        int index = 2;
        String beschreibung = "Mach es mit Liebe";
        rezept.ergaenzeZubereitung(index, beschreibung);

        assertZubereitungMitLiebe(rezept);
        assertThat(rezept.getZubereitung().getBeschreibungen().get(2), is(beschreibung));

        rezept.entferneZubereitung(index);
        assertZubereitung(rezept);
    }

    @Test
    void rezept_entferne_valid() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String omeletten = "Omeletten";
        RezeptId rezeptId = RezeptId.generate();
        Rezept rezept = Rezept.erstelle(repository, rezeptId, omeletten, zutatenListeFuerOmeletten, zubereitung);
        verify(rezept.getRepository()).add(rezept);

        rezept.entferne();
        verify(rezept.getRepository()).remove(rezept);
    }

}
