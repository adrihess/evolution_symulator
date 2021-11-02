package zad1;

class NieprawidlowaPlansza extends Exception {

    private static final long serialVersionUID = 43;

    public String getMessage() {
        return " Plik z planszą zawiera niedozwolone znaki lub niedozwoloną wielkość planszy ";
    }
}
