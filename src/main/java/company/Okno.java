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
    WebView webView;
    JFXPanel jfxPanel;
    JTabbedPane tabbedPane;
    WebView nowyWV;
    public Okno(){
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

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
        else if(event.getSource() == webView){
            System.out.println("ELO");
        }
    }

    void zainicjujLayout(int width, int height){
        // Dodanie pasku adresu
        pasekAdresu = new PasekAdresu(width);
        pasekAdresu.addActionListener(this);

        // Dodanie układu paneli
        spod = new Panel(width,height,Color.BLUE,true);
        panel = new Panel(width, 40, Color.RED, false);
        panel2 = new Panel(width, height-40, Color.GRAY, false);


        jfxPanel = new JFXPanel();
        //panel2.add(jfxPanel);

        Platform.runLater(() -> {
            webView = new WebView();
            jfxPanel.setPreferredSize(new Dimension(panel2.getWidth(),panel2.getHeight()));
            jfxPanel.setScene(new Scene(webView));
            webView.getEngine().load("http://www.google.com/");
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


        panel2.add(tabbedPane);

        // Zagnieżdzenie paneli
        frame.add(spod);
        spod.add(panel);
        panel.add(pasekAdresu);
        spod.add(panel2);
    }

    void wyszukaj(String url){
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
    } // Funkcja sprawdza url i wykonuje odpowiednią operacje zaladowania strony

    void zaladuj(String url){
        Platform.runLater(() -> {
            webView.getEngine().load(url);
        });
    } // Laduje stronę o podanym url

    void wyszukajWGoogle(String fraza){
        Platform.runLater(() -> {
            webView.getEngine().load("http://google.com/search?q=" + fraza);
        });
    } // Szuka fraze w gooogle


    // Zmienia URL kart przeglądarki oraz paska adresu przy otwieraniu nowych stron
    void aktualizujURL(){
        webView.getEngine().getLoadWorker().stateProperty().addListener(new javafx.beans.value.ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue value, Worker.State stary, Worker.State nowy) {
                if (nowy == Worker.State.SUCCEEDED) {
                    pasekAdresu.zapiszPole(webView.getEngine().getLocation(),tabbedPane.getSelectedIndex());
                    pasekAdresu.setText(webView.getEngine().getLocation());
                    tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),pasekAdresu.poleTekstowe[tabbedPane.getSelectedIndex()]);
                }
            }
        });
    }

    void nowaKarta(int width, int height){
        JFXPanel nowyJFX = new JFXPanel();
        Platform.runLater(() -> {
            nowyWV = new WebView();
            nowyJFX.setPreferredSize(new Dimension(width,height));
            nowyJFX.setScene(new Scene(nowyWV));
            nowyWV.getEngine().load("http://www.google.com/");
            tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),nowyWV.getEngine().getLocation());
            pasekAdresu.setText(nowyWV.getEngine().getLocation());
            pasekAdresu.poleTekstowe[tabbedPane.getSelectedIndex()] = nowyWV.getEngine().getLocation();
            tabbedPane.addChangeListener(this::zmianaKarty);
        });
        tabbedPane.add(nowyJFX);
    }

    //Zmiana URL paska adresu przy przełączaniu się  między zakładkami
    void zmianaKarty(ChangeEvent event){
        if(event.getSource() == tabbedPane && pasekAdresu.poleTekstowe[tabbedPane.getSelectedIndex()]!=null){
            pasekAdresu.setText(pasekAdresu.poleTekstowe[tabbedPane.getSelectedIndex()]);
        }
    }
}