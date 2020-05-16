package company;
import javax.swing.*;
import java.awt.*;


public class Window{
    JFrame frame;
    Container WebMap;
    public Window(){
        frame = new JFrame("Przeglada");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        BrowserWebBar addressBar = new BrowserWebBar(frame);
    }
}




