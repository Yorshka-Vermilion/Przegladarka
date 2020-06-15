package company;

/***
 * Klasa zawierająca dane oraz niezbędne funkcje działania historii przeglądarki
 */

public class Historia {
    /** Tablica typu String przechowywująca adresy stron internetowych w pamięci przeglądarki */
    public String tab[];
    /** Zmienna mająca za zadanie wskazywać na obecną pozycje strony internetowej w tablicy */
    public int index;
    /***
     * Konstruktor domyślny historii, ma za zadanie utworzenie nowej tablicy przechowywującej strony internetowe oraz zerujący jej indeks
     */
    public Historia(){
        tab = new String[1024];
        index = 0;
    }

    /***
     * Funkcja odpowiadająca za dodanie strony internetowej do historii przeglądarki
     * @param url Adres internetowy strony wpisanej w pasku adresu
     */
    public void dodaj(String url){
        tab[index] = url;
        index++;
    }

    /***
     * Funkcja odpowiadająca za wyczyszczenie całej historii przeglądarki
     */
    public void wyczysc(){
        for(int i = 0;i < index+1; i++){
            tab[i] = null;
        }
        index = 0;
    }

    /***
     * Funkcja odpowiadająca za wypisanie historii przeglądarki w konsoli programu
     */
    public void wypisz(){
        for(int i = 0;i < index ; i++){
            System.out.println(tab[i]);
        }
    }
}