package company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.security.Key;


public class PasekAdresu {
    public static int number = 0;
    public String addressHistory[] = new String[1024];
    JTextField addressBar;

    public PasekAdresu(JFrame frame){
        addressBar = new JTextField("");
        frame.add(addressBar);
        addressBar.setSize(frame.getWidth(),30);
        addressBar.setEditable(true);

        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        addressBar.registerKeyboardAction(EnterEvent,enter,JComponent.WHEN_FOCUSED);

    }

    ActionListener EnterEvent = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
            addressHistory[number++] = addressBar.getText();
        }
    };

}

