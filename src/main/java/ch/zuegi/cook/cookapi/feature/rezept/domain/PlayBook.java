package ch.zuegi.cook.cookapi.feature.rezept.domain;


import lombok.*;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayBook {

    Rezept rezept;
    transient int step;

    public static PlayBook erstelle(Rezept rezept) {
        PlayBook playBook = new PlayBook();
        playBook.rezept = rezept;
        playBook.step = -1;

        return playBook;
    }

    public ZubereitungsSchritt next() {
        ZubereitungsSchritt zubereitungsSchritt = null;
        ++step;
        if (step >= 0 && step < rezept.getZubereitung().getBeschreibungen().size()) {
            zubereitungsSchritt = rezept.getZubereitung().getBeschreibungen().get(step);
        }
        return zubereitungsSchritt;

    }

    public ZubereitungsSchritt previous() {
        --step;
        ZubereitungsSchritt zubereitungsSchritt = null;
        if (step >= 0 &&  step < rezept.getZubereitung().getBeschreibungen().size()) {
            zubereitungsSchritt = rezept.getZubereitung().getBeschreibungen().get(step);
        }
        return zubereitungsSchritt;
    }
}
