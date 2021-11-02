package zad1;

enum Instrukcje {
    L, P, I, W, J;

    static Instrukcje getFromString(char t) throws NieMaEnumaTakiego {
        Instrukcje nowa;
        if (t == 'l')
            nowa = Instrukcje.L;
        else if (t == 'p')
            nowa = Instrukcje.P;
        else if (t == 'i')
            nowa = Instrukcje.I;
        else if (t == 'w')
            nowa = Instrukcje.W;
        else if (t == 'j')
            nowa = Instrukcje.J;
        else
            throw new NieMaEnumaTakiego();
        return nowa;
    }
}
