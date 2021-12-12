package ch.zuegi.cook.cookapi.feature.rezept.domain;

import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationError;
import ch.zuegi.cook.cookapi.shared.exception.BusinessValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

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
    void rezept_erstelle_valid() {
        // given
        int anzahlPersonen = 1;
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String rezeptName = "Omelette";
        RezeptId rezeptId = RezeptId.generate();
        // when
        Rezept rezept = Rezept.erstelle(repository, rezeptId, rezeptName, zutatenListeFuerOmeletten, zubereitung);
        // then
        verify(rezept.getRepository(), times(1)).findById(rezeptId);
        verify(rezept.getRepository(), times(1)).persist(rezept);
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
    void rezept_aendere_name_valid() {
        int anzahlPersonen = 1;
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String rezeptName = "Omelette";
        RezeptId rezeptId = RezeptId.generate();
        // when
        Rezept rezept = Rezept.erstelle(repository, rezeptId, rezeptName, zutatenListeFuerOmeletten, zubereitung);
        // then
        // then
        verify(rezept.getRepository(), times(1)).findById(rezeptId);
        verify(rezept.getRepository(), times(1)).persist(rezept);
        assertThat(rezept.getName(), is(rezeptName));
        assertThat(rezept.getRezeptId(), is(rezeptId));
        assertThat(rezept.zutaten, hasSize(zutatenListeFuerOmeletten.size()));
        assertThat(rezept.getZubereitung().getBeschreibungen(), hasSize(zubereitung.getBeschreibungen().size()));
        assertOmeletten(rezept, anzahlPersonen);

        String neuerRezeptName = "Amelette (Thurgau)";
        rezept.aendereName(neuerRezeptName);
        verify(rezept.getRepository(), times(2)).persist(rezept);

        assertThat(rezept.getName(), is(neuerRezeptName));
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
        Rezept rezept = erstelleOmelettenRezept();
        String omeletten = "Omeletten";

        int anzahlPersonen = 4;
        rezept.berechneMengenFuer(anzahlPersonen);

        assertThat(rezept.getName(), is(omeletten));
        assertOmeletten(rezept, anzahlPersonen);

    }

    @Test
    void Rezept_erhoehe_anzahl_personen_0_invalid() {
        Rezept rezept = erstelleOmelettenRezept();

        int anzahlPersonen = 0;
        assertThatExceptionOfType(BusinessValidationException.class)
                .isThrownBy(() -> rezept.berechneMengenFuer(anzahlPersonen))
                .withMessage(BusinessValidationError.REZEPT_ANZAHL_PERSONEN_DARF_NICHT_0_SEIN);
    }

    @Test
    void rezept_ergaenze_zutat_valid() {
        // given
        Rezept rezept = erstelleOmelettenRezept();
        int index = 2;
        Zutat zucker = Zutat.erstelle(ZUTATID_ZUCKER, "Zucker", 1d, Einheit.PRISE);
        // whene
        rezept.ergaenzeZutat(index, zucker);
        // then
        verify(rezept.getRepository(), times(2)).persist(rezept);
        assertOmelettenMitZucker(rezept, 1);

    }

    @Test
    void rezept_entferne_zutat_valid() {
        // given
        Rezept rezept = erstelleOmelettenRezept();
        int index = 2;
        Zutat zucker = Zutat.erstelle(ZUTATID_ZUCKER, "Zucker", 1d, Einheit.PRISE);
        rezept.ergaenzeZutat(index, zucker);

        assertOmelettenMitZucker(rezept, 1);
        // when
        rezept.entferneZutat(index);
        // then
        verify(rezept.getRepository(), times(3)).persist(rezept);
        assertOmeletten(rezept, 1);
    }

    @Test
    void rezept_update_zutat_valid() {
        // given
        Rezept rezept = erstelleOmelettenRezept();

        // when
        rezept.aendereZutatName(ZUTATID_SALZ, "Meersalz");
        rezept.aendereZutatMenge(ZUTATID_SALZ, 1d);
        rezept.aendereZutatEinheit(ZUTATID_SALZ, Einheit.PRISE);

        // then
        verify(rezept.getRepository(), times(4)).persist(rezept);
        assertThat(rezept.getZutaten().get(2).getName(), is("Meersalz"));
        assertThat(rezept.getZutaten().get(2).getMenge(), is(1d));
        assertThat(rezept.getZutaten().get(2).getEinheit(), is(Einheit.PRISE));
    }

    @Test
    void rezept_aendere_zutat_position_valid() {
        // given
        int anzahlPersonen = 1;
        Rezept rezept = erstelleOmelettenRezept();
        Zutat salz = rezept.getZutaten().get(2);

        // when
        rezept.aendereZutatPosition(4, ZUTATID_SALZ);
        // then
        verify(rezept.getRepository(), times(2)).persist(rezept);
        assertThat(rezept.getZutaten(), contains(
                Zutat.erstelle(ZUTATID_MEHL, "Mehl", anzahlPersonen * 50d, Einheit.GRAMM),
                Zutat.erstelle(ZUTATID_EIER, "Eier", anzahlPersonen * 1d, Einheit.STUECK),
                Zutat.erstelle(ZUTATID_WASSER, "Wasser", anzahlPersonen * 50d, Einheit.MILLILITER),
                Zutat.erstelle(ZUTATID_MILCH, "Milch", anzahlPersonen * 50d, Einheit.MILLILITER),
                Zutat.erstelle(ZUTATID_SALZ, "Salz", anzahlPersonen * 0.5d, Einheit.TEELOEFFEL))
        );
    }

    @Test
    void rezept_aendere_zutat_position_invalid() {
        // given
        int anzahlPersonen = 1;
        Rezept rezept = erstelleOmelettenRezept();
        Zutat salz = rezept.getZutaten().get(2);

        // when
        rezept.aendereZutatPosition(4, ZUTATID_SALZ);
        // then
        verify(rezept.getRepository(), times(2)).persist(rezept);
        assertThat(rezept.getZutaten(), not(contains(
                Zutat.erstelle(ZUTATID_SALZ, "Salz", anzahlPersonen * 0.5d, Einheit.TEELOEFFEL),
                Zutat.erstelle(ZUTATID_MEHL, "Mehl", anzahlPersonen * 50d, Einheit.GRAMM),
                Zutat.erstelle(ZUTATID_EIER, "Eier", anzahlPersonen * 1d, Einheit.STUECK),
                Zutat.erstelle(ZUTATID_WASSER, "Wasser", anzahlPersonen * 50d, Einheit.MILLILITER),
                Zutat.erstelle(ZUTATID_MILCH, "Milch", anzahlPersonen * 50d, Einheit.MILLILITER)))
        );
    }

    @Test
    void rezept_aendere_2_zutat_position_valid() {
        // given
        Rezept rezept = erstelleOmelettenRezept();

        rezept.aendereZutatPosition(0, ZUTATID_SALZ);
        rezept.aendereZutatPosition(4, ZUTATID_MEHL);

        verify(rezept.getRepository(), times(3)).persist(rezept);
        assertThat(rezept.getZutaten(), contains(
                Zutat.erstelle(ZUTATID_SALZ, "Salz", 0.5d, Einheit.TEELOEFFEL),
                Zutat.erstelle(ZUTATID_EIER, "Eier", 1d, Einheit.STUECK),
                Zutat.erstelle(ZUTATID_WASSER, "Wasser", 50d, Einheit.MILLILITER),
                Zutat.erstelle(ZUTATID_MILCH, "Milch", 50d, Einheit.MILLILITER),
                Zutat.erstelle(ZUTATID_MEHL, "Mehl", 50d, Einheit.GRAMM))
                );
    }

    @Test
    void rezept_aendere_invalide_zutat_position_invalid() {
        Rezept rezept = erstelleOmelettenRezept();

        Zutat salz = rezept.getZutaten().get(2);
        assertThatExceptionOfType(BusinessValidationException.class)
                .isThrownBy(() -> rezept.aendereZutatPosition(16, ZUTATID_SALZ))
                .withMessage(BusinessValidationError.ZUTAT_INDEX_EXISTIERT_NICHT);
        ;
    }

    private Rezept erstelleOmelettenRezept() {
        List<Zutat> zutatenListeFuerOmeletten = createZutatenListeFuerOmeletten();
        Zubereitung zubereitung = createZubereitungOmelette();
        String omeletten = "Omeletten";
        RezeptId rezeptId = RezeptId.generate();
        Rezept rezept = Rezept.erstelle(repository, rezeptId, omeletten, zutatenListeFuerOmeletten, zubereitung);
        // then
        verify(rezept.getRepository(), times(1)).findById(rezeptId);
        verify(rezept.getRepository(), times(1)).persist(rezept);
        return rezept;
    }

    @Test
    void Rezept_ergaenze_zubereitung_valid() {
        Rezept rezept = erstelleOmelettenRezept();

        int index = 2;
        ZubereitungsSchritt beschreibung = new ZubereitungsSchritt("Mach es mit Liebe");
        rezept.ergaenzeZubereitung(index, beschreibung);

        assertZubereitungMitLiebe(rezept);
        assertThat(rezept.getZubereitung().getBeschreibungen().get(2), is(beschreibung));

    }

    @Test
    void rezept_entferne_zubereitungsbeschreibung_valid() {
        Rezept rezept = erstelleOmelettenRezept();

        int index = 2;
        ZubereitungsSchritt beschreibung = new ZubereitungsSchritt("Mach es mit Liebe");
        rezept.ergaenzeZubereitung(index, beschreibung);

        assertZubereitungMitLiebe(rezept);
        assertThat(rezept.getZubereitung().getBeschreibungen().get(2), is(beschreibung));

        rezept.entferneZubereitung(index);
        assertZubereitung(rezept);
    }

    @Disabled("Test mit Zubereitungen müssen gefixed werden")
    @Test
    void rezept_update_zubereitung_valid() {
        Rezept rezept = erstelleOmelettenRezept();
        String machEsMitLiebe = "Mach es mit Liebe";
        ZubereitungsSchritt schritt = rezept.getZubereitung().getBeschreibungen().get(2);
        schritt.aendereZubereitungsSchritt(machEsMitLiebe);


        verify(rezept.getRepository()).persist(rezept);

        assertThat(rezept.getZubereitung().getBeschreibungen().get(2).getZubereitungsSchritt(), is(machEsMitLiebe));
    }

    @Test
    void rezept_entferne_valid() {
        Rezept rezept = erstelleOmelettenRezept();
        rezept.entferne();
        verify(rezept.getRepository()).remove(rezept);
    }

}
