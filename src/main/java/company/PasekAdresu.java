package company;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class PasekAdresu extends JTextField{
    String poleTekstowe;
    public PasekAdresu(int rozmiar){
        setSize(rozmiar,30);
        setEditable(true);
    }

    public void zapiszPole(){
        poleTekstowe = getText();
    }
    public String wezPole(){
        return this.poleTekstowe;
    }
}

