package company;
import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    public Panel(int width, int height, Color kolor,boolean FLOW){
        setPreferredSize(new Dimension(width, height));
        setBackground(kolor);
        if(FLOW) setLayout(new FlowLayout(FlowLayout.CENTER));
    }
}
