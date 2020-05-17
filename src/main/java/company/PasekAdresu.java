package company;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class PasekAdresu {
    public static int number = 0;
    public String addressHistory[] = new String[1024];
    JTextField poleTekstowe;

    public PasekAdresu(JFrame frame){
        poleTekstowe = new JTextField("");
        frame.add(poleTekstowe);
        poleTekstowe.setSize(frame.getWidth(),30);
        poleTekstowe.setEditable(true);

        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        poleTekstowe.registerKeyboardAction(EnterEvent,enter,JComponent.WHEN_FOCUSED);

    }

    ActionListener EnterEvent = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
            addressHistory[number++] = poleTekstowe.getText();
        }
    };

}

