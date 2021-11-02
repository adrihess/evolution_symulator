package zad1;

class NieprawidloweParametry extends Exception {

    private static final long serialVersionUID = 44;

    public String getMessage() {
        return " Plik z parametrami zawiera błędną liczbę parametrów, zawiera je zwielokrotnione lub błędnych typów lub znak '\n' na ostatnim wierszu";
    }
}
