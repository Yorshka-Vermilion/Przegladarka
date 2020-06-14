package company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;;


public class PasekAdresu extends JTextField implements FocusListener {
    String[] poleTekstowe = new String[1024];
    public boolean focused;
    public PasekAdresu(int rozmiar){
        setPreferredSize(new Dimension(rozmiar, 30));
        setEditable(true);
        addFocusListener(this);
    }

    public void zapiszPole(int strona){
        poleTekstowe[strona] = getText();
    }

    public void zapiszPole(String tekst, int strona){
        poleTekstowe[strona] = tekst;
    }

    public String wezPole(int strona){
        return this.poleTekstowe[strona];
    }

    @Override
    public void focusGained(FocusEvent e) {
        this.focused = true;
    }

    @Override
    public void focusLost(FocusEvent e) {
        this.focused = false;
    }
}

