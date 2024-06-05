import gameLaby.laby.Monstre;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;


public class testMonstre{

    @Test
    public void test_prise_degat() {
        Monstre monstre = new Monstre(0, 0);
        monstre.subirDegat();
        assertEquals(2, monstre.getPv());
    }

    @Test
    public void test_mort() {
        Monstre monstre = new Monstre(0, 0);
        monstre.subirDegat();
        monstre.subirDegat();
        monstre.subirDegat();
        assertEquals(0, monstre.getPv());
    }

    @Test
    public void test_mort_et_reattaque() {
        Monstre monstre = new Monstre(0, 0);
        try {
            monstre.subirDegat();
            monstre.subirDegat();
            monstre.subirDegat();
            monstre.subirDegat();
            monstre.subirDegat();
            fail("Erreur non levée");  // le monstre est supprimé de la liste dès qu'il meurt
        } catch (Error e) {
            assertEquals(0, monstre.getPv());
        }
    }

}
