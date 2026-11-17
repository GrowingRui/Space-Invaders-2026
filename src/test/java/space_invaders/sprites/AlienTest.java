package space_invaders.sprites;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertEquals;

public class AlienTest {

    @Test
    void testALIEN1() {
        Alien a = new Alien(100, 100);
        assertEquals(100, a.getX());
        assertEquals(100, a.getY());
    }

    @Test
    void testALIEN2() {
        Alien a = new Alien(400, 100);
        assertEquals(358, a.getX());
        assertEquals(100, a.getY());
    }

    @Test
    void testALIEN3() {
        Alien a = new Alien(-10, 100);
        assertEquals(0, a.getX());
        assertEquals(100, a.getY());
    }

    @Test
    void testALIEN4() {
        Alien a = new Alien(100, 400);
        assertEquals(100, a.getX());
        assertEquals(350, a.getY());
    }

    @Test
    void testALIEN5() {
        Alien a = new Alien(100, -10);
        assertEquals(100, a.getX());
        assertEquals(0, a.getY());
    }
}
