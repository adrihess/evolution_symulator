package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

class Parametry {
    private Instrukcje[] spis_instr;
    private Instrukcje[] pocz_progr;
    private int rozmiar_planszy_x;
    private int rozmiar_planszy_y;
    private final Map<String, Integer> variables;

    public int getRozmiar_planszy_y() {
        return rozmiar_planszy_y;
    }

    public Instrukcje[] getPocz_progr() {
        return pocz_progr;
    }

    public void setRozmiar_planszy_y(int rozmiar_planszy_y) {
        this.rozmiar_planszy_y = rozmiar_planszy_y;
    }

    public int getRozmiar_planszy_x() {
        return rozmiar_planszy_x;
    }

    public void setRozmiar_planszy_x(int rozmiar_planszy_x) {
        this.rozmiar_planszy_x = rozmiar_planszy_x;
    }


    public Map<String, Integer> getVariables() {
        return variables;
    }

    Parametry(String katalog) throws FileNotFoundException, NieMaEnumaTakiego, NieprawidloweParametry {
        File plik = new File(katalog);
        Scanner scan = new Scanner(plik);

        this.pocz_progr = new Instrukcje[0];
        this.spis_instr = new Instrukcje[0];

        String temp_par;
        int temp_var;
        int liczba_parametrow = 0;
        this.variables = new HashMap<String, Integer>(15);

        while (scan.hasNextLine()) {

            try {
                temp_par = scan.next();
            } catch (NoSuchElementException error) {
                throw new NieprawidloweParametry();

            }

            if (temp_par.equals("spis_instr")) {
                String temp = scan.next();
                this.spis_instr = new Instrukcje[temp.length()];
                for (int i = 0; i < temp.length(); i++) {
                    this.spis_instr[i] = Instrukcje.getFromString(temp.charAt(i));
                }

            } else if (temp_par.equals("pocz_progr")) {
                String temp = scan.next();
                this.pocz_progr = new Instrukcje[temp.length()];
                for (int i = 0; i < temp.length(); i++) {
                    this.pocz_progr[i] = Instrukcje.getFromString(temp.charAt(i));
                }

            } else {
                try {
                    temp_var = scan.nextInt();
                } catch (NoSuchElementException error) {
                    throw new NieprawidloweParametry();
                }
                variables.put(temp_par, temp_var);

            }
            liczba_parametrow++;
        }

        if (liczba_parametrow != 15) {
            throw new NieprawidloweParametry();
        }
    }

}
