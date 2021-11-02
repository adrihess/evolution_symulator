package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Plansza {
    private final Pole[][] tablica;
    private int ile_aktualnie_robow;

    public Pole[][] getTablica() {
        return tablica;
    }

    public int getIle_aktualnie_robow() {
        return ile_aktualnie_robow;
    }

    public void setIle_aktualnie_robow(int ile_aktualnie_robow) {
        this.ile_aktualnie_robow = ile_aktualnie_robow;
    }

    Plansza(Parametry params, String katalog) throws FileNotFoundException, NieprawidlowaPlansza, NieprawidloweParametry {
        try {
            this.ile_aktualnie_robow = params.getVariables().get("pocz_ile_robów");
        } catch (NullPointerException wyjatek) {
            throw new NieprawidloweParametry();
        }


        File plik2 = new File(katalog);
        Scanner scan2 = new Scanner(plik2);
        int rozm_x = 0;
        int rozm_y = 0;
        boolean pierwsza = true;
        while (scan2.hasNextLine()) {
            String temp = scan2.nextLine();
            if (pierwsza)
                rozm_x = temp.length();
            rozm_y++;
            pierwsza = false;

            if (temp.length() != rozm_x) {
                throw new NieprawidlowaPlansza();
            }

        }
        params.setRozmiar_planszy_x(rozm_x);
        params.setRozmiar_planszy_y(rozm_y);

        this.tablica = new Pole[params.getRozmiar_planszy_x()][];
        for (int i = 0; i < params.getRozmiar_planszy_x(); i++) {
            this.tablica[i] = new Pole[params.getRozmiar_planszy_y()];
        }

        File plik = new File(katalog);
        Scanner scan = new Scanner(plik);
        String linia;

        for (int i = 0; i < params.getRozmiar_planszy_y(); i++) {

            linia = scan.nextLine();
            for (int j = 0; j < params.getRozmiar_planszy_x(); j++) {

                int ile_rosnie_jedzenie;
                int ile_daje_jedzenie;
                try {
                    ile_rosnie_jedzenie = params.getVariables().get("ile_rośnie_jedzenie");
                    ile_daje_jedzenie = params.getVariables().get("ile_daje_jedzenie");
                } catch (NullPointerException wyjatek) {
                    throw new NieprawidloweParametry();
                }

                this.tablica[j][i] = new Pole(ile_rosnie_jedzenie, ile_daje_jedzenie);

                if (linia.charAt(j) == 'x')
                    this.tablica[j][i].ustawCzyZywieniowe(true);

                else if (linia.charAt(j) == ' ')
                    this.tablica[j][i].ustawCzyZywieniowe(false);

                else {
                    throw new NieprawidlowaPlansza();
                }

            }

        }

    }

    void rozmiesc_roby(Parametry params, Plansza plansza) throws NieprawidloweParametry {
        Rob.ustawStanPoczatkowy(params);
        int energia = params.getVariables().get("pocz_energia");
        Random rand = new Random();
        int x_poz;
        int y_poz;
        for (int i = 0; i < plansza.ile_aktualnie_robow; i++) {
            x_poz = rand.nextInt(params.getRozmiar_planszy_x());
            y_poz = rand.nextInt(params.getRozmiar_planszy_y());


            ArrayList<Instrukcje> program = new ArrayList<>();
            for (int k = params.getPocz_progr().length - 1; k >= 0; k--) {
                program.add(params.getPocz_progr()[k]);
            }


            Rob robak = new Rob(program, energia);

            plansza.tablica[x_poz][y_poz].getRoby().add(robak);
        }
    }
}
