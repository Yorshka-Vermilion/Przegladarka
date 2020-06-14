import company.Historia;
import company.Okno;

import company.Panel;
import company.PasekAdresu;
import org.junit.jupiter.api.*;

import org.testng.annotations.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class JakisTest {
    @Test
    public void TestButt(){
        Button next;
        assertNotNull(next = new Button("->"));
    }
    @Test
    public void TestHist(){
        Historia hist;
        assertNotNull(hist = new Historia());

        hist.dodaj("Test");
        String[] testTab = new String[1024];
        testTab[0] = "Test";
        assertArrayEquals(hist.tab, testTab);
    }
    @Test
    public void TestPanel(){
        Panel pan;
        assertNotNull(pan = new Panel(10,10,Color.BLUE,true));
    }
    @Test
    public void TestPasek(){
        PasekAdresu pas;
        assertNotNull(pas = new PasekAdresu(19));
    }

}
