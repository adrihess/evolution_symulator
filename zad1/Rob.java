package zad1;

import java.util.ArrayList;
import java.util.Random;

class Rob implements losuj {

    private boolean ruszony = false;
    private int wiek = 0;
    private int energia;
    private static int pr_powielania;
    private static int ulamek_energii_rodzica;
    private static int pr_usuniecia_instr;
    private static int pr_dodania_instr;
    private static int pr_zmiany_instr;
    private static int limit_powielania;
    private Orientacja orientacja;

    public Orientacja getOrientacja() {
        return orientacja;
    }

    public void setOrientacja(Orientacja orientacja) {
        this.orientacja = orientacja;
    }

    private final ArrayList<Instrukcje> program;

    public ArrayList<Instrukcje> getProgram() {
        return program;
    }

    public void setRuszony(boolean ruszony) {
        this.ruszony = ruszony;
    }

    public void setWiek(int wiek) {
        this.wiek = wiek;
    }

    public int getWiek() {
        return wiek;
    }

    public boolean isRuszony() {
        return ruszony;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public static int getLimit_powielania() {
        return limit_powielania;
    }

    public static int getPr_powielania() {
        return pr_powielania;
    }

    public static int getPr_usuniecia_instr() {
        return pr_usuniecia_instr;
    }

    public static int getPr_dodania_instr() {
        return pr_dodania_instr;
    }

    public static int getPr_zmiany_instr() {
        return pr_zmiany_instr;
    }

    public static int getUlamek_energii_rodzica() {
        return ulamek_energii_rodzica;
    }

    boolean czySieDzieli(Rob temp) {
        Random rand = new Random();
        int los = rand.nextInt(100);
        return los < Rob.getPr_powielania() && temp.energia > Rob.getLimit_powielania();

    }

    Rob PodzieiIMutuj(Rob temp) throws ClassCastException {
        //klonowanie
        ArrayList<Instrukcje> nowyPr = new ArrayList<>();
        for (int i = 0; i < temp.program.size(); i++) {
            Instrukcje dwa = temp.program.get(i);
            nowyPr.add(dwa);
        }

        //tworzenie nowego roba i odejmowanie energii
        Rob nowyRob = new Rob(nowyPr, Rob.getUlamek_energii_rodzica());

        temp.energia = temp.energia - Rob.getUlamek_energii_rodzica();

        Random rand = new Random();
        int los = rand.nextInt(100);

        //Usuwanie instrukcji
        if (los < Rob.getPr_usuniecia_instr() && nowyRob.program.size() > 0) {
            nowyRob.program.remove(nowyRob.program.size() - 1);
        }

        //Dodatanie instrukcji
        los = rand.nextInt(100);
        if (los < Rob.getPr_dodania_instr()) {
            Instrukcje nowaInst = this.losujInstrukcje();
            nowyRob.program.add(nowaInst);
        }

        //Zmiana instrukcji
        los = rand.nextInt(100);
        if (los < Rob.getPr_zmiany_instr() && nowyRob.program.size() > 0) {
            los = rand.nextInt(nowyRob.program.size());
            Instrukcje nowa = this.losujInstrukcje();
            nowyRob.program.set(los, nowa);
        }

        return nowyRob;
    }

    public Instrukcje losujInstrukcje() {
        Random rand = new Random();
        int los = rand.nextInt(5);
        Instrukcje nowaInst;
        switch (los) {
            case 0:
                nowaInst = Instrukcje.P;
                break;

            case 1:
                nowaInst = Instrukcje.L;
                break;

            case 2:
                nowaInst = Instrukcje.J;
                break;

            case 3:
                nowaInst = Instrukcje.I;
                break;

            default:
                nowaInst = Instrukcje.W;
                break;
        }
        return nowaInst;
    }

    public Orientacja losujOrientacje() {
        Orientacja orientacja;
        Random rand = new Random();
        int los = rand.nextInt(4);
        switch (los) {
            case 0:
                orientacja = Orientacja.G;
                break;

            case 1:
                orientacja = Orientacja.P;
                break;

            case 2:
                orientacja = Orientacja.D;
                break;

            default:
                orientacja = Orientacja.L;
                break;
        }
        return orientacja;
    }

    Rob(ArrayList<Instrukcje> program, int energia) {
        this.program = program;
        this.energia = energia;
        this.orientacja = this.losujOrientacje();
    }

    static void ustawStanPoczatkowy(Parametry params) throws NieprawidloweParametry {
        try {
            Rob.pr_powielania = params.getVariables().get("pr_powielenia");
            Rob.ulamek_energii_rodzica = params.getVariables().get("ułamek_energii_rodzica");
            Rob.pr_usuniecia_instr = params.getVariables().get("pr_usunięcia_instr");
            Rob.pr_dodania_instr = params.getVariables().get("pr_dodania_instr");
            Rob.pr_zmiany_instr = params.getVariables().get("pr_zmiany_instr");
            Rob.limit_powielania = params.getVariables().get("limit_powielania");

        } catch (NullPointerException wyjatek) {
            throw new NieprawidloweParametry();
        }
    }
}
