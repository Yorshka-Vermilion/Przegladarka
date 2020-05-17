package company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;


public class PasekAdresu extends JTextField implements FocusListener {
    String poleTekstowe;
    public boolean focused;
    public PasekAdresu(int rozmiar){
        setPreferredSize(new Dimension(rozmiar, 30));
        setEditable(true);
        addFocusListener(this);
    }

    public void zapiszPole(){
        poleTekstowe = getText();
    }
    public String wezPole(){
        return this.poleTekstowe;
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

