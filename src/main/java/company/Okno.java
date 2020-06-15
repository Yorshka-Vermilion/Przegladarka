package company;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * Klasa Okna w której wykonywane są wszystkie akcje związane z architektrurą okna przeglądarki
 */

public class Okno implements ActionListener {
    /** Zmienna odpowiadająca wyświetlającemu się oknu przeglądarki */
    JFrame frame;
    /** Zmienna odpowiadjąca za odpowiednie rozstawienie elementów przeglądarki w oknie, odpowiednie pozycjonowanie */
    Panel spod, panel, panel2;
    /** Jest to pasek wyświetlający się u góry okna przeglądarki */
    PasekAdresu pasekAdresu;
    /** Panel zawierający w sobie wszystkie karty zakładek  */
    JFXPanel jfxPanel;
    /** Panel zawierający w sobioe wszystkie karty zakładek z podziałem na poszczególne karty */
    JTabbedPane tabbedPane;
    /** Przyciski odpowiadające za konkretne funkcje */
    Button odswiez,next,prev,hist;
    /** Przechowuje dane na temat historii przeglądania */
    Historia historia;
    /** Zmienna klasy WebView w formie tablicy, pozwala na wyświetlanie zróżnicowanych stron zależnie od karty */
    WebView[] tablicaWebView;
    /** Wykorzystywana do kontroli stron tablicy WebView */
    int aktualnyWebView;
    /** Wykorzystywana przy kontroli cofania stron internetowych w karcie przeglądarki */
    int cofnij;
    /***
     *Konstruktor domyślny okna, uruchamia okno przeglądarki oraz ustawia jego podstawowe parametry
     */
    public Okno(){
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(); /** Pobiera informacje co do rozmiaru monitora */
        int width = gd.getDisplayMode().getWidth(); /** Zmienna zawierająca szerokość okna przeglądarki */
        int height = gd.getDisplayMode().getHeight(); /** Zmienna zawierająca wysokość okna przeglądarki */

        this.tablicaWebView = new WebView[100];
        aktualnyWebView = 0;

        this.historia = new Historia();

        frame = new JFrame("Przeglada");
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));

        zainicjujLayout(width, height); // Inicjacja layoutu przeglądarki

        frame.pack();
        frame.setVisible(true);

        cofnij = 2;
    }

    /***
     * Funkcja odpowiada za reagowanie na zdarzenia zachodzące w obrębie programu
     * @param event Zmienna przechowująca kolejke zdarzeń programu
     */
    public void actionPerformed(ActionEvent event){
        if(event.getSource() == pasekAdresu && pasekAdresu.focused){
            pasekAdresu.zapiszPole(tabbedPane.getSelectedIndex());
            wyszukaj(pasekAdresu.wezPole(tabbedPane.getSelectedIndex()));
        }
    }

    /***
     * Funkcja odpowiada za ustawienie wszystkich elementów przeglądarki w oknie programu oraz za ustalanie wartości domyślnych nowych kart.
     * Odpowiada również za nadanie funkcjonalności poszczególnym przyciskom.
     * @param width Zmienna opisująca szerokość okna przeglądarki
     * @param height Zmienna opisująca wysokość okna przeglądarki
     */
    void zainicjujLayout(int width, int height){
        // Dodanie pasku adresu
        pasekAdresu = new PasekAdresu(width-150);
        pasekAdresu.addActionListener(this);

        // Dodanie układu paneli
        spod = new Panel(width,height,Color.BLUE,true); // Panel na spodzie
        panel = new Panel(width, 40, Color.RED, false); // Panel u gory
        panel2 = new Panel(width, height-40, Color.GRAY, false); // Panel na dole

        // Dodanie przyciskow
        next = new Button("->");
        prev  = new Button("<-");
        odswiez = new Button("%");
        hist = new Button("#");

        jfxPanel = new JFXPanel();

        Platform.runLater(() -> {
            tablicaWebView[aktualnyWebView] = new WebView();
            jfxPanel.setPreferredSize(new Dimension(panel2.getWidth(),panel2.getHeight()));
            jfxPanel.setScene(new Scene(tablicaWebView[aktualnyWebView]));
            tablicaWebView[aktualnyWebView].getEngine().load("http://www.google.com/");
            aktualizujURL();
        });

        // Inizjalizacja kart
        tabbedPane = new JTabbedPane();
        tabbedPane.add(jfxPanel);
        nowaKarta(width,height-40);

        tabbedPane.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                if(tabbedPane.getSelectedIndex() == tabbedPane.getTabCount()-1){
                    nowaKarta(width, height-40);
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-2);
                    System.out.println("Dodaje karte");
                    return;
                }
            }
        });


        // Funkcja odpowiadająca za odświeżanie strony
        odswiez.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        tablicaWebView[tabbedPane.getSelectedIndex()].getEngine().reload();
                    }
                });
            }
        });


        prev.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(historia.index - cofnij > -1) {
                            System.out.println(historia.tab[historia.index - cofnij]);
                            zaladuj(historia.tab[historia.index - cofnij], false);
                            cofnij++;
                        }
                    }
                });
            }
        });

        next.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(cofnij > 2){
                            cofnij--;
                            System.out.println(historia.tab[historia.index - cofnij]);
                            zaladuj(historia.tab[historia.index - cofnij], false);
                        }
                    }
                });
            }
        });

        hist.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historia.wypisz();
            }
        }));

        // Zagnieżdzenie paneli
        panel2.add(tabbedPane);
        frame.add(spod);
        spod.add(panel);
        panel.add(prev);
        panel.add(next);
        panel.add(hist);
        panel.add(pasekAdresu);
        panel.add(odswiez);
        spod.add(panel2);
        /* Debbug
        aktualnyWebView = tabbedPane.getSelectedIndex();
        System.out.println("Aktualny webView : " + aktualnyWebView);
        */
    }

    /***
     * Funkcja odpowiada za sprawdzanie zawartości wpisanego url w pasku adresu danej karty oraz na tej podstawie podejmuje decyzje o sposobie załadowania strony
     * @param url Adres internetowy strony wpisanej w pasku adresu
     */
    void wyszukaj(String url){
        aktualnyWebView = tabbedPane.getSelectedIndex();
        if(url.contains(".")) {
            if(url.contains("https://")){
                zaladuj(url,true);
            }else if(url.contains("http://")){
                zaladuj(url, true);
            }else {
                zaladuj("https://" + url, true);
            }
        } else {
            wyszukajWGoogle(url, true);
        }
        //System.out.println("WV: " + aktualnyWebView);
    } // Funkcja sprawdza url i wykonuje odpowiednią operacje zaladowania strony

    /***
     * Funkcja odpowiada za załadowanie strony internetowej w przeglądarce, na podstawie adresu przekazanego w pasku adresu oraz za dodanie strony do historii przeglądarki
     * @param url Adres internetowy strony wpisanej w pasku adresu
     * @param zapis Wskazuje czy dana strona ma zostać zapisana do histroii przeglądania
     */
    void zaladuj(String url, boolean zapis){
        if(zapis)historia.dodaj(url);
        Platform.runLater(() -> {
            tablicaWebView[aktualnyWebView].getEngine().load(url);
        });
    } // Laduje stronę o podanym url

    /***
     * Funkcja odpowiada za załadowanie frazy w google, w sytuacji gdy wpisana przez użytkownika fraza nie była stroną internetową lub nie zgadzała się z
     * @param fraza Fraza wpisana w pasku adresu, która nie została potraktowana jako adres strony internetowej
     * @param zapis Wskazuje czy dana strona ma zostać zapisana do histroii przeglądania
     */
    void wyszukajWGoogle(String fraza, boolean zapis){
        if(zapis) historia.dodaj("http://google.com/search?q=" + fraza);
        Platform.runLater(() -> {
            tablicaWebView[aktualnyWebView].getEngine().load("http://google.com/search?q=" + fraza);
        });
    } // Szuka fraze w gooogle


    /***
     * Funkcja jest odpowiedzialna za aktualizowanie paska adresu w sytuacji wpisania nowej strony internetowej, wraz z podziałem na zakładki przeglądarki
     */
    // Zmienia URL kart przeglądarki oraz paska adresu przy otwieraniu nowych stron
    void aktualizujURL(){
        tablicaWebView[aktualnyWebView].getEngine().getLoadWorker().stateProperty().addListener(new javafx.beans.value.ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue value, Worker.State stary, Worker.State nowy) {
                if (nowy == Worker.State.SUCCEEDED) {
                    pasekAdresu.zapiszPole(tablicaWebView[aktualnyWebView].getEngine().getLocation(),tabbedPane.getSelectedIndex());
                    pasekAdresu.setText(tablicaWebView[aktualnyWebView].getEngine().getLocation());
                    tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),pasekAdresu.poleTekstowe[tabbedPane.getSelectedIndex()]);
                    historia.dodaj(tablicaWebView[aktualnyWebView].getEngine().getLocation());
                }
            }
        });
    }

    /***
     * Funkcja tworzy nową karte w zakładkach przeglądarki oraz ustawia jej wszystkie niezbędne atrybuty do działania
     * @param width Zmienna opisująca szerokość zakładki przeglądarki
     * @param height Zmienna opisująca wysokość zakładki przeglądarki
     */
    void nowaKarta(int width, int height){
        JFXPanel nowyJFX = new JFXPanel();
        Platform.runLater(() -> {
            aktualnyWebView = szukajOstatniej();
            System.out.println("Webview to jest : " + aktualnyWebView);
            tablicaWebView[aktualnyWebView] = new WebView();
            nowyJFX.setPreferredSize(new Dimension(width,height));
            nowyJFX.setScene(new Scene(tablicaWebView[aktualnyWebView]));
            tablicaWebView[aktualnyWebView].getEngine().load("http://www.google.com/");
            tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),tablicaWebView[aktualnyWebView].getEngine().getLocation());
            pasekAdresu.setText(tablicaWebView[aktualnyWebView].getEngine().getLocation());
            System.out.println(tablicaWebView[aktualnyWebView].getEngine().getLocation());
            pasekAdresu.poleTekstowe[tabbedPane.getSelectedIndex()] = tablicaWebView[aktualnyWebView].getEngine().getLocation();
            aktualizujURL();
            tabbedPane.addChangeListener(this::zmianaKarty);
        });
        //System.out.println("DOdano, wv: " + aktualnyWebView);
        tabbedPane.add(nowyJFX);
    }

    /**
     * Funkcja jest odpowiedzialna za aktualizowanie paska adresu w zależności od zakładki na której się obecnie znajdujemy
     * @param event Zmienna przechowująca kolejke zdarzeń programu
     */
    //Zmiana URL paska adresu przy przełączaniu się  między zakładkami
    void zmianaKarty(ChangeEvent event){
        if(event.getSource() == tabbedPane && pasekAdresu.poleTekstowe[tabbedPane.getSelectedIndex()]!=null){
            pasekAdresu.setText(pasekAdresu.poleTekstowe[tabbedPane.getSelectedIndex()]);
            aktualnyWebView = tabbedPane.getSelectedIndex();
            //System.out.println("Nr karty: " + aktualnyWebView);
        }
    }

    /**
     * Funkcja przeszukuje tablicę kart przeglądarki w poszukiwaniu pierwszego indeksu, który nie ma przypisanego żadnego adresu
     * @return Zwraca wartość typu Int określającą w indeks karty, w której można wyświetlić stronę
     */
    //Wyszukuje ostatniego wolnego panelu w kartach
    int szukajOstatniej(){
        int i=0;
        while(tablicaWebView[i]!=null)
            i++;
        return i;
    }
}