package company;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Okno implements ActionListener {
    JFrame frame;
    Panel spod, panel, panel2;
    PasekAdresu pasekAdresu;
    JFXPanel jfxPanel;
    JTabbedPane tabbedPane;
    Button odswiez,next,prev;
    private WebView[] tablicaWebView;
    private int aktualnyWebView;
    public Okno(){
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        this.tablicaWebView = new WebView[100];
        aktualnyWebView = 0;

        frame = new JFrame("Przeglada");
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));

        zainicjujLayout(width, height); // Inicjacja layoutu przeglądarki

        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event){
        if(event.getSource() == pasekAdresu && pasekAdresu.focused){
            pasekAdresu.zapiszPole(tabbedPane.getSelectedIndex());
            wyszukaj(pasekAdresu.wezPole(tabbedPane.getSelectedIndex()));
        }
        else if(event.getSource() == tablicaWebView[aktualnyWebView]){
            System.out.println("ELO");
        }
    }

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



        jfxPanel = new JFXPanel();
        //panel2.add(jfxPanel);

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
        // Koniec kart


        //Dodanie AL

        odswiez.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){

            }
        });




        // Zagnieżdzenie paneli
        panel2.add(tabbedPane);
        frame.add(spod);
        spod.add(panel);
        panel.add(prev);
        panel.add(next);
        panel.add(pasekAdresu);
        panel.add(odswiez);
        spod.add(panel2);
        /* Debbug
        aktualnyWebView = tabbedPane.getSelectedIndex();
        System.out.println("Aktualny webView : " + aktualnyWebView);
        */
    }

    void wyszukaj(String url){
        aktualnyWebView = tabbedPane.getSelectedIndex();
        if(url.contains(".")) {
            if(url.contains("https://")){
                zaladuj(url);
            }else if(url.contains("http://")){
                zaladuj(url);
            }else {
                zaladuj("https://" + url);
            }
        } else {
            wyszukajWGoogle(url);
        }
        System.out.println("WV: " + aktualnyWebView);
    } // Funkcja sprawdza url i wykonuje odpowiednią operacje zaladowania strony

    void zaladuj(String url){
        Platform.runLater(() -> {
            tablicaWebView[aktualnyWebView].getEngine().load(url);
        });
    } // Laduje stronę o podanym url

    void wyszukajWGoogle(String fraza){
        Platform.runLater(() -> {
            tablicaWebView[aktualnyWebView].getEngine().load("http://google.com/search?q=" + fraza);
        });
    } // Szuka fraze w gooogle


    // Zmienia URL kart przeglądarki oraz paska adresu przy otwieraniu nowych stron
    void aktualizujURL(){
        tablicaWebView[aktualnyWebView].getEngine().getLoadWorker().stateProperty().addListener(new javafx.beans.value.ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue value, Worker.State stary, Worker.State nowy) {
                if (nowy == Worker.State.SUCCEEDED) {
                    pasekAdresu.zapiszPole(tablicaWebView[aktualnyWebView].getEngine().getLocation(),tabbedPane.getSelectedIndex());
                    pasekAdresu.setText(tablicaWebView[aktualnyWebView].getEngine().getLocation());
                    tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),pasekAdresu.poleTekstowe[tabbedPane.getSelectedIndex()]);
                }
            }
        });
    }

    void nowaKarta(int width, int height){
        JFXPanel nowyJFX = new JFXPanel();
        Platform.runLater(() -> {
            aktualnyWebView++;
            tablicaWebView[aktualnyWebView] = new WebView();
            nowyJFX.setPreferredSize(new Dimension(width,height));
            nowyJFX.setScene(new Scene(tablicaWebView[aktualnyWebView]));
            tablicaWebView[aktualnyWebView].getEngine().load("http://www.google.com/");
            tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),tablicaWebView[aktualnyWebView].getEngine().getLocation());
            pasekAdresu.setText(tablicaWebView[aktualnyWebView].getEngine().getLocation());
            pasekAdresu.poleTekstowe[tabbedPane.getSelectedIndex()] = tablicaWebView[aktualnyWebView].getEngine().getLocation();
            tabbedPane.addChangeListener(this::zmianaKarty);
        });
        System.out.println("DOdano, wv: " + aktualnyWebView);
        tabbedPane.add(nowyJFX);
    }

    //Zmiana URL paska adresu przy przełączaniu się  między zakładkami
    void zmianaKarty(ChangeEvent event){
        if(event.getSource() == tabbedPane && pasekAdresu.poleTekstowe[tabbedPane.getSelectedIndex()]!=null){
            pasekAdresu.setText(pasekAdresu.poleTekstowe[tabbedPane.getSelectedIndex()]);
            aktualnyWebView = tabbedPane.getSelectedIndex();
            System.out.println("Nr karty: " + aktualnyWebView);
        }
    }
}