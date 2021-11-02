package zad1;


import java.io.FileNotFoundException;



public class Symulacja {

    public static void main(String[] args)  throws FileNotFoundException, NieMaEnumaTakiego, NieprawidlowaPlansza, NieprawidloweParametry, NieprawidloweParametryProgramu {
        if(args.length != 2) {
            throw new NieprawidloweParametryProgramu();
            }

        String jeden = args[0];
        String dwa = args[1];

        Parametry params = new Parametry(dwa);
        Plansza plansza = new Plansza(params, jeden);
        plansza.rozmiesc_roby(params, plansza);
        System.out.println("========================= Zaczynamy symulację robów =========================");
        System.out.println("========================= Roby działają losowo, chcą przeżyć =========================");
        System.out.println("========================= Czy im się to uda? Zobaczymy =========================");
        Symulacja.symulacja(params, plansza);
        System.out.println("========================= Symulacja przebiegła pomyślnie, części robów udało się przezyć, Gratulacje dla wyewoluowanych =========================");

    }

    private static void symulacja(Parametry params, Plansza plansza) throws NieprawidloweParametry {
        int ileTur;

        int coIleWypisz;
        int koszt_tury;

        try {
                 ileTur = params.getVariables().get("ile_tur");
                coIleWypisz = params.getVariables().get("co_ile_wypisz");
                koszt_tury= params.getVariables().get("koszt_tury");}
        catch (NullPointerException wyjatek) {
            throw new NieprawidloweParametry();
        }

        int zaIleWypisz = coIleWypisz;
        int ileByloTur = ileTur;
        Rob obslugiwany;

        Symulacja.wypiszStaty(plansza, ileTur, ileByloTur, params);

        while(ileTur > 0) {
            int robx;
            int roby;
            //Działa ileś tur dla każdego pola z planszy
            for(int i=0; i< params.getRozmiar_planszy_x(); i++) {
                for(int j=0; j<params.getRozmiar_planszy_y(); j++) {
                    //Każdy rob na polu z planszy zostanie "obsłużony"
                    for(int w=0; w<plansza.getTablica()[i][j].getRoby().size(); w++) {
                        //pobranie roba
                        obslugiwany = plansza.getTablica()[i][j].getRoby().remove(w);
                        obslugiwany.setRuszony(true);
                        plansza.setIle_aktualnie_robow(plansza.getIle_aktualnie_robow()-1);

                        obslugiwany.setEnergia(obslugiwany.getEnergia()-koszt_tury);

                        if(obslugiwany.czySieDzieli(obslugiwany)) {
                            Rob nowy = obslugiwany.PodzieiIMutuj(obslugiwany);
                            plansza.getTablica()[i][j].getRoby().add(nowy);
                            plansza.setIle_aktualnie_robow(plansza.getIle_aktualnie_robow()+1);
                            System.out.println("========================= Uwaga: powstaje rob =========================");
                        }

                        robx = i;
                        roby = j;

                        //pobrane jego instruckcji
                        if(!obslugiwany.isRuszony() && obslugiwany.getEnergia() > 0) {

                            //wykonujemy instrukcje
                            for (int instr =0 ; instr < obslugiwany.getProgram().size() && obslugiwany.getEnergia()>0; instr++) {
                                Instrukcje instrukcja = obslugiwany.getProgram().get(instr);

                                if (instrukcja == Instrukcje.P || instrukcja == Instrukcje.L) {
                                    skrecanieRobem(obslugiwany, instrukcja);

                                    obslugiwany.setEnergia(obslugiwany.getEnergia()-1);


                                } else if (instrukcja == Instrukcje.I) {
                                    int new_x = robx;
                                    int new_y = roby;
                                    if (obslugiwany.getOrientacja() == Orientacja.G) {
                                        new_y = modul(roby + 1, params.getRozmiar_planszy_y());
                                    } else if (obslugiwany.getOrientacja() == Orientacja.P) {
                                        new_x = modul(robx + 1, params.getRozmiar_planszy_x());
                                    } else if (obslugiwany.getOrientacja() == Orientacja.D) {
                                        new_y = modul(roby - 1, params.getRozmiar_planszy_y());
                                    } else if (obslugiwany.getOrientacja() == Orientacja.L) {
                                        new_x = modul(robx - 1, params.getRozmiar_planszy_x());
                                    }

                                    //Zmiana miejsca roba
                                    robx = new_x;
                                    roby = new_y;

                                    if (plansza.getTablica()[new_x][new_y].isCzyZywieniowe()) {
                                        obslugiwany.setEnergia(obslugiwany.getEnergia()  +  plansza.getTablica()[new_x][new_y].getIle_daje());
                                        plansza.getTablica()[new_x][new_y].zjedzono(plansza.getTablica()[new_x][new_y]);
                                    }
                                    obslugiwany.setEnergia(obslugiwany.getEnergia()-1);


                                } else if (instrukcja == Instrukcje.W) {
                                    if (plansza.getTablica()[robx][modul(roby + 1, params.getRozmiar_planszy_y())].isCzyZywieniowe())
                                        obslugiwany.setOrientacja(Orientacja.G);
                                    else if (plansza.getTablica()[modul(robx + 1, params.getRozmiar_planszy_x())][roby].isCzyZywieniowe())
                                        obslugiwany.setOrientacja(Orientacja.P);
                                    else if (plansza.getTablica()[modul(robx - 1, params.getRozmiar_planszy_x())][roby].isCzyZywieniowe())
                                        obslugiwany.setOrientacja(Orientacja.D);
                                    else if (plansza.getTablica()[robx][modul(roby - 1, params.getRozmiar_planszy_y())].isCzyZywieniowe())
                                        obslugiwany.setOrientacja(Orientacja.L);

                                    obslugiwany.setEnergia(obslugiwany.getEnergia()-1);

                                } else if (instrukcja == Instrukcje.J) {

                                    int new_x = robx;
                                    int new_y = roby;

                                    if (plansza.getTablica()[robx][modul(roby + 1, params.getRozmiar_planszy_y())].isCzyZywieniowe())
                                        new_y = modul(new_y + 1, params.getRozmiar_planszy_y());
                                    else if (plansza.getTablica()[modul(robx + 1, params.getRozmiar_planszy_x())][roby].isCzyZywieniowe())
                                        new_x = modul(new_x + 1, params.getRozmiar_planszy_x());
                                    else if (plansza.getTablica()[modul(robx - 1, params.getRozmiar_planszy_x())][roby].isCzyZywieniowe())
                                        new_x = modul(new_x - 1, params.getRozmiar_planszy_x());
                                    else if (plansza.getTablica()[robx][modul(roby - 1, params.getRozmiar_planszy_y())].isCzyZywieniowe())
                                        new_y = modul(new_y - 1, params.getRozmiar_planszy_y());

                                    else if (plansza.getTablica()[modul(robx + 1, params.getRozmiar_planszy_x())][modul(roby + 1, params.getRozmiar_planszy_y())].isCzyZywieniowe()) {
                                        new_x = modul(new_x + 1, params.getRozmiar_planszy_x());
                                        new_y = modul(new_y + 1, params.getRozmiar_planszy_y());
                                    } else if (plansza.getTablica()[modul(robx - 1, params.getRozmiar_planszy_x())][modul( roby + 1, params.getRozmiar_planszy_y())].isCzyZywieniowe()) {
                                        new_x = modul(new_x - 1, params.getRozmiar_planszy_x());
                                        new_y = modul(new_y + 1, params.getRozmiar_planszy_y());
                                    } else if (plansza.getTablica()[modul(robx - 1, params.getRozmiar_planszy_x())][modul(roby - 1, params.getRozmiar_planszy_y())].isCzyZywieniowe()) {
                                        new_x = modul(new_x - 1, params.getRozmiar_planszy_x());
                                        new_y = modul(new_y - 1, params.getRozmiar_planszy_y());
                                    } else if (plansza.getTablica()[modul(robx + 1, params.getRozmiar_planszy_x())][modul(roby - 1, params.getRozmiar_planszy_y())].isCzyZywieniowe()) {
                                        new_x = modul(new_x + 1, params.getRozmiar_planszy_x());
                                        new_y = modul(new_y - 1, params.getRozmiar_planszy_y());
                                    }

                                    if(robx != new_x || roby != new_y) {
                                        robx = new_x;
                                        roby = new_y;

                                        obslugiwany.setEnergia(obslugiwany.getEnergia() + plansza.getTablica()[new_x][new_y].getIle_daje());
                                        plansza.getTablica()[new_x][new_y].zjedzono(plansza.getTablica()[new_x][new_y]);}

                                    obslugiwany.setEnergia(obslugiwany.getEnergia()-1);

                                }
                            }




                        }

                        if(obslugiwany.getEnergia() > 0) {
                            obslugiwany.setRuszony(true);
                            plansza.getTablica()[robx][roby].getRoby().add(obslugiwany);
                            plansza.setIle_aktualnie_robow(plansza.getIle_aktualnie_robow()+1);
                        }
                        else {
                            System.out.println("========================= Uwaga: umiera rob =========================");
                        }

                    }

                }
            }

            ileTur--;
            zaIleWypisz--;

            Symulacja.wypiszStaty(plansza, ileTur, ileByloTur, params);

            if(zaIleWypisz==0) {
                Symulacja.wypiszdokladnaStatystyke(plansza, ileTur, ileByloTur, params);
                zaIleWypisz=coIleWypisz;}

        }

    }
    private static void wypiszdokladnaStatystyke(Plansza plansza, int ile_tur, int cal_tur, Parametry params) {
        int nrRoba=1;
        System.out.println("========================= Dokładne statystyki symulacji na ture "+(cal_tur - ile_tur)+" =========================");
        for(int i=0; i< params.getRozmiar_planszy_x();i++) {
            for(int j=0; j< params.getRozmiar_planszy_y();j++) {
                for(int k=0; k< plansza.getTablica()[i][j].getRoby().size(); k++) {
                    Rob obslugiwany = plansza.getTablica()[i][j].getRoby().get(k);

                    System.out.println("rob"+nrRoba+" znajduje się na polu w rzędzie "+i+" i kolumnie "+j+", ma "+obslugiwany.getEnergia()+
                            " energii, żyje "+obslugiwany.getWiek()+ " tur, jego program ma długość: "+obslugiwany.getProgram().size()+" intrukcji");
                    nrRoba++;

                }

            }
        }
        System.out.println("========================= Koniec dokładnych statystyk =========================");
    }

    private static void wypiszStaty(Plansza plansza, int ile_tur, int cal_tur, Parametry params) {
        int pol_z_zyw=0;

        int min_rob=Integer.MAX_VALUE;
        double sr_rob=0;
        int max_rob=0;

        int min_energ=Integer.MAX_VALUE;
        double sr_energ=0;
        int max_energ=0;

        int min_wiek=Integer.MAX_VALUE;
        double sred_wiek=0;
        int max_wiek=0;

        if(plansza.getIle_aktualnie_robow() == 0) {
            min_energ=0;
            min_rob=0;
            min_wiek=0;
        }

        for(int i=0; i< params.getRozmiar_planszy_x(); i++) {
            for(int j=0; j< params.getRozmiar_planszy_y(); j++) {
                for(int k=0; k< plansza.getTablica()[i][j].getRoby().size(); k++) {
                    plansza.getTablica()[i][j].sprawdzPole();
                    Rob sprawdzany = plansza.getTablica()[i][j].getRoby().get(k);

                    if(sprawdzany.getEnergia() <= 0) {
                        plansza.getTablica()[i][j].getRoby().remove(sprawdzany);
                        plansza.setIle_aktualnie_robow(plansza.getIle_aktualnie_robow()-1);
                    }
                    else {
                        sprawdzany.setRuszony(false);
                        sprawdzany.setWiek(sprawdzany.getWiek()+1);

                        sr_rob += plansza.getTablica()[i][j].getRoby().get(k).getProgram().size();

                        sr_energ += plansza.getTablica()[i][j].getRoby().get(k).getEnergia();

                        sred_wiek += plansza.getTablica()[i][j].getRoby().get(k).getWiek();

                        max_energ = Integer.max(sprawdzany.getEnergia(), max_energ);
                        min_energ = Integer.min(sprawdzany.getEnergia(), min_energ);

                        max_rob = Integer.max(sprawdzany.getProgram().size(), max_rob);
                        min_rob = Integer.min(sprawdzany.getProgram().size(), min_rob);

                        max_wiek = Integer.max(sprawdzany.getWiek(), max_wiek);
                        min_wiek = Integer.min(sprawdzany.getWiek(), min_wiek);}

                }

                if(plansza.getIle_aktualnie_robow() == 0) {
                    System.out.println("========================= Wszytskie roby umarły, sumylacja zakończona przedwcześnie =========================");
                    System.exit(1);
                }

                if(plansza.getTablica()[i][j].isCzyZywieniowe())
                    pol_z_zyw++;
            }
        }

        System.out.println((cal_tur - ile_tur) + ", rob: " + plansza.getIle_aktualnie_robow() + ", żyw: " + pol_z_zyw + " " +
                ", prg: " + min_rob + "/" + (sr_rob / plansza.getIle_aktualnie_robow()) + "/" + max_rob +
                ", energ: " + min_energ + "/" + sr_energ / plansza.getIle_aktualnie_robow() + "/" + max_energ +
                ", wiek: " + min_wiek + "/" + sred_wiek / plansza.getIle_aktualnie_robow() + "/" + max_wiek);

    }

    private static int modul(int a, int b) {
        return Math.floorMod(a, b);

    }

    private static void skrecanieRobem(Rob robak, Instrukcje temp) {
        Orientacja temporary = robak.getOrientacja();
        switch(temporary) {

            case G:
                if (temp == Instrukcje.P)
                    robak.setOrientacja(Orientacja.P);
                else
                    robak.setOrientacja(Orientacja.L);

                break;
            case P:
                if (temp == Instrukcje.P)
                    robak.setOrientacja(Orientacja.D);
                else
                    robak.setOrientacja(Orientacja.G);

                break;
            case D :
                if (temp == Instrukcje.P)
                    robak.setOrientacja(Orientacja.L);
                else
                    robak.setOrientacja(Orientacja.P);
                break;
            case L:
                if (temp == Instrukcje.P)
                    robak.setOrientacja(Orientacja.G);
                else
                    robak.setOrientacja(Orientacja.D);

                break;
        }
    }
}