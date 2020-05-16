package company;
import javax.swing.*;

public class Okno{
    JFrame frame;
    public Okno(){
        JFrame frame = new JFrame("Przegladarka");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
