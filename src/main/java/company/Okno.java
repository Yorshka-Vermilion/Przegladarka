package company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Okno implements ActionListener{
    JFrame frame;
    JPanel panel;
    JPanel panel2;
    JPanel spod;
    PasekAdresu pasekAdresu;
    public Okno(){
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();


        frame = new JFrame("Przeglada");
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        // Dodanie pasku adresu
        pasekAdresu = new PasekAdresu(width);
        pasekAdresu.addActionListener(this);


        spod = new JPanel();
        spod.setPreferredSize(new Dimension(width, height));
        spod.setBackground(Color.BLUE);
        spod.setLayout(new FlowLayout(FlowLayout.CENTER));


        panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, 40));
        panel.setBounds(0,0,width,40);
        panel.setBackground(Color.RED);


        panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(width, height-40));
        panel2.setBackground(Color.GRAY);



        frame.add(spod);
        spod.add(panel);
        panel.add(pasekAdresu);

        spod.add(panel2);

        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event){
        if(event.getSource() == pasekAdresu && pasekAdresu.focused){
            pasekAdresu.zapiszPole();
            System.out.println(pasekAdresu.wezPole());
        }
    }




}