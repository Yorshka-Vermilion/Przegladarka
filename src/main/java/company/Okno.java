package company;
import javax.swing.*;
import java.awt.*;


public class Okno {
    JFrame frame;
    Container WebMap;
    public Okno(){
        frame = new JFrame("Przeglada");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        PasekAdresu addressBar = new PasekAdresu(frame);
    }
}