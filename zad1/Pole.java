package zad1;

import java.util.ArrayList;

class Pole {
    private final ArrayList<Rob> roby;
    private final int ile_rosnie;
    private final int ile_daje;
    private int wyrosnie_za;
    private boolean czyZywieniowe;
    private boolean czyRegeneruje;

    public ArrayList<Rob> getRoby() {
        return roby;
    }

    public boolean isCzyZywieniowe() {
        return czyZywieniowe;
    }

    public int getIle_daje() {
        return ile_daje;
    }

    Pole(int ile_rosnie, int ile_daje) {
        this.ile_rosnie = ile_rosnie;
        this.ile_daje = ile_daje;
        this.wyrosnie_za = ile_rosnie;

        this.roby = new ArrayList<>();
    }

    void sprawdzPole() {
        if (czyRegeneruje && wyrosnie_za <= 0) {
            czyZywieniowe = true;
        } else if (czyRegeneruje)
            wyrosnie_za -= 1;

    }

    void zjedzono(Pole pol) {
        pol.czyZywieniowe = false;
        pol.wyrosnie_za = pol.ile_rosnie;
    }

    void ustawCzyZywieniowe(boolean czy) {
        this.czyZywieniowe = czy;
        this.czyRegeneruje = czy;
    }

}
