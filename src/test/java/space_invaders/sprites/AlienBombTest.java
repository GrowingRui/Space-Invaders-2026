package space_invaders.sprites;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlienBombTest {

    private Alien.Bomb crearBomba(int x, int y) {
        Alien alien = new Alien(0, 0);
        return alien.new Bomb(x, y);
    }


    @Test
    public void testBOMB1() {
        Alien.Bomb b = crearBomba(100, 100);
        assertEquals(100, b.getX());
        assertEquals(100, b.getY());
    }

    @Test
    public void testBOMB2o() {
        Alien.Bomb b = crearBomba(400, 100);
        assertEquals(358, b.getX());
        assertEquals(100, b.getY());
    }

}
