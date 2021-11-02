package zad1;

//Własne wyjątki
class NieMaEnumaTakiego extends Exception {


    private static final long serialVersionUID = 42;

    public String getMessage() {
        return " W pliku z parametrami podano błędną instrukcję ";
    }
}
