package company;
import javax.swing.*;
import java.awt.*;

/***
 * Klasa opisująca podstawowe parametry przy tworzeniu nowego panelu okna przeglądarki
 */
public class Panel extends JPanel {
    /***
     * Konstruktor klasy panel ustawiający podstawowy rozmiar, kolor oraz pozycje panelu w oknie przeglądarki
     * @param width Zmienna reprezentująca szerokość towrzonego panelu
     * @param height Zmienna reprezentująca wysokość towrzonego panelu
     * @param kolor Zmienna reprezentująca kolor tworzonego panelu
     * @param FLOW Zmienna definująca czy panel ma ustawiać się w sposób statyczny czy relatywny względem pozostałych elemntów okna
     */
    public Panel(int width, int height, Color kolor,boolean FLOW){
        setPreferredSize(new Dimension(width, height));
        setBackground(kolor);
        if(FLOW) setLayout(new FlowLayout(FlowLayout.CENTER));
    }
}