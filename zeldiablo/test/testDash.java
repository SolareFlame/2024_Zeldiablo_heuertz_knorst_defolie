import gameLaby.laby.Labyrinthe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class testDash {
    private Labyrinthe laby;

    @BeforeEach
    public void setup() throws IOException {
        laby = new Labyrinthe("zeldiablo/labySimple/laby0.txt");
    }

    @Test
    public void test_dash2deCase_apresCooldown() throws InterruptedException {
        int initialX = laby.pj.getX();
        int initialY = laby.pj.getY();
        laby.dashDe2Cases();
        Thread.sleep(3100);
        laby.dashDe2Cases();
        assertEquals(false, laby.pj.etrePresent(initialX, initialY));
    }

    @Test
    public void test_dash2deCase_pendantCooldown() {
        //dash 1 fois
        laby.dashDe2Cases();
        //les nouvelles pos du perso
        int newX = laby.pj.getX();
        int newY = laby.pj.getY();
        //dash une 2eme fois mais le cooldown n'est pas encore fini
        laby.dashDe2Cases();
        // Check if the player's position has not changed after the second dash action
        assertEquals(true, laby.pj.etrePresent(newX, newY));
    }

}
