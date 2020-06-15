package company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;;


/***
 * Klasa opisująca podstawowe funkcjonalności paska adresu przeglądarki internetowej
 */

public class PasekAdresu extends JTextField implements FocusListener {
    /** Tablica typu string przechowywująca obecny adres okna przeglądarki */
    String poleTekstowe[] = new String[1024];
    /** Zmienna wymagana przy implementacji FocusListenera, opisująca stan paska adresu */
    public boolean focused;

    /**
     * Konstruktor paska adresu ustalający jego podstawowe parametry działania
     * @param rozmiar Zmienna ustalająca szerokość paska adresu
     */
    public PasekAdresu(int rozmiar){
        setPreferredSize(new Dimension(rozmiar, 30));
        setEditable(true);
        addFocusListener(this);
    }

    /**
     Funkcja zapisująca dany adres strony z paska adresu do pamięci przeglądarki
     * @param strona Zmienna wskazująca z której z zakładek ma zostać pobrany adres strony internetowej
     */
    public void zapiszPole(int strona){
        poleTekstowe[strona] = getText();
    }

    /**
     * Funkcja zapisująca dany tekst strony z paska adresu do pamięci przeglądarki
     * @param tekst Zmienna, która ma zostać zapisana do pamięci przeglądarki internetowej
     * @param strona Zmienna wskazująca z której z zakładek ma zostać pobrany adres strony internetowej
     */
    public void zapiszPole(String tekst, int strona){
        poleTekstowe[strona] = tekst;
    }

    /**
     * Funkcja pobierajaca adres strony internetowej z pamięci przeglądarki
     * @param strona Zmienna wskazująca z której z zakładek ma zostać pobrany adres strony internetowej
     * @return Zwraca adres strony internetowej, z zakładki o podanym numerze
     */
    public String wezPole(int strona){
        return this.poleTekstowe[strona];
    }

    /**
     * Funkcja sprawdzająca kolejke zdarzeń programu pod kątem czy użytkownik skupia swoją uwage na pasku zadań
     * @param event Zmienna przechowująca kolejke zdarzeń programu
     */
    @Override
    public void focusGained(FocusEvent event) {
        this.focused = true;
    }

    /**
     * Funkcja sprawdzająca kolejke zdarzeń programu pod kątem czy użytkownik nie stracił skupienia uwagi na pasku zadań
     * @param event Zmienna przechowująca kolejke zdarzeń programu
     */
    @Override
    public void focusLost(FocusEvent event) {
        this.focused = false;
    }
}