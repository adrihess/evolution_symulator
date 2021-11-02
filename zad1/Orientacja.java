package zad1;

/*
 * Plik z parametrami nie powinien zawierać znaku '\n' na końcu pliku
 * Wszystkie klasy mogłyby by być public i znajdować się w osobnych plikach
 * Jednak kod był pisany w Intellij i wygodniej było pisać w jednym pliku
 * Klasa Pole mogła by być abstrakcyjna i dziedziczyć by po niej mogły klasy
 * PoleZywieniowe i PolePuste, jednak uznałem, że tutaj dziedziczenie jest zbędne
 * W rozwiązaniu zakładam, że spis_instr zawsze jest pełny i zawiera wszystkie instrukcje
 * To jest pliwj i nie sprawdzam czy któraś z nich nie występuje w początkowym programie
 * Zostało zaimplementowanie wyświetlania pojawiania się i umierania nowych robów
 * Można to wyłączyć zakomentowywując odpowiednio linijki: 580, 687 */
enum Orientacja {
    L, P, G, D
}
