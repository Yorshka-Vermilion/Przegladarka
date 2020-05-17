package company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Okno implements ActionListener{
    JFrame frame;
    PasekAdresu pasekAdresu;
    public Okno(){
        frame = new JFrame("Przeglada");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Dodanie pasku adresu
        pasekAdresu = new PasekAdresu(frame.getWidth());
        pasekAdresu.addActionListener(this);
        frame.add(pasekAdresu);

    }

    public void actionPerformed(ActionEvent event){
        if(event.getSource() == pasekAdresu){
            pasekAdresu.zapiszPole();
            System.out.println(pasekAdresu.wezPole());
        }
    }




}