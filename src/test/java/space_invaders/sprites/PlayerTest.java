package space_invaders.sprites;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    private Player p;

    @BeforeEach
    void setUp() {
        p = new Player();
    }

    private KeyEvent crearKeyEvent(int id, int keyCode) {
        Component component = new JPanel();
        return new KeyEvent(component, id, System.currentTimeMillis(), 0, keyCode, KeyEvent.CHAR_UNDEFINED);
    }


    @Test
    void testACT1() {
        p.x = 328;
        p.dx = 2;
        p.act();
        assertEquals(328, p.x);
    }

    @Test
    void testACTW2() {
        p.x = 3;
        p.dx = -2;
        p.act();
        assertEquals(2, p.x);
    }

    @Test
    void testACTW3() {
        p.x = 20000;
        p.dx = 0;
        p.act();
        assertEquals(328, p.x);
    }

    @Test
    void testKP1() {
        p.dx = 0;
        p.keyPressed(crearKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SPACE));
        assertEquals(0, p.dx);
    }

    @Test
    void testKP2() {
        p.dx = 0;
        p.keyPressed(crearKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT));
        assertEquals(-2, p.dx);
    }

    @Test
    void testKP3() {
        p.dx = 0;
        p.keyPressed(crearKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT));
        assertEquals(2, p.dx);
    }

    @Test
    void testKR1() {
        p.dx = 2;
        p.keyReleased(crearKeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_SPACE));
        assertEquals(2, p.dx);
    }

    @Test
    void testKR2() {
        p.dx = -2;
        p.keyReleased(crearKeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_LEFT));
        assertEquals(0, p.dx);
    }

    @Test
    void testKR3() {
        p.dx = 2;
        p.keyReleased(crearKeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_RIGHT));
        assertEquals(0, p.dx);
    }

    @Test
    void testINIT1() {
        assertEquals(179, p.getX());
        assertEquals(280, p.getY());
    }

}
