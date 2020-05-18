package company;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Okno implements ActionListener{
    JFrame frame;
    Panel spod, panel, panel2;
    PasekAdresu pasekAdresu;
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
            pasekAdresu.zapiszPole();
            System.out.println(pasekAdresu.wezPole());
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

        JFXPanel jfxPanel = new JFXPanel();
        panel2.add(jfxPanel);

        Platform.runLater(() -> {
            WebView webView = new WebView();
            jfxPanel.setPreferredSize(new Dimension(panel2.getWidth(),panel2.getHeight()));
            jfxPanel.setScene(new Scene(webView));
            webView.getEngine().load("http://www.google.com/");
        });

        // Zagnieżdzenie paneli
        frame.add(spod);
        spod.add(panel);
        panel.add(pasekAdresu);
        spod.add(panel2);
    }


}