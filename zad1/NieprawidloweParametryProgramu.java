package zad1;

class NieprawidloweParametryProgramu extends Exception {

    private static final long serialVersionUID = 45;

    public String getMessage() {
        return " Podczas uruchamiania programu poleceniem java, nie podano poprawnej liczby lub nazwy parametrów dostępu do plików ";
    }
}
