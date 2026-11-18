package main;

import org.junit.jupiter.api.Test;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Shot;
import space_invaders.sprites.Sprite;

import javax.swing.Timer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {


    private Board crearBoard() throws Exception {
        Board board = new Board();
        Field timer = Board.class.getDeclaredField("timer");
        timer.setAccessible(true);
        ((Timer) timer.get(board)).stop();
        Field inGame = Board.class.getDeclaredField("inGame");
        inGame.setAccessible(true);
        inGame.set(board, true);
        Field message = Board.class.getDeclaredField("message");
        message.setAccessible(true);
        message.set(board, "Game Over");
        return board;
    }

    private void invokeUpdate(Board board) throws Exception {
        Method updateMethod = Board.class.getDeclaredMethod("update");
        updateMethod.setAccessible(true);
        updateMethod.invoke(board);
    }

    private void invokeUpdateShots(Board board) throws Exception {
        Method updateShotsMethod = Board.class.getDeclaredMethod("update_shots");
        updateShotsMethod.setAccessible(true);
        updateShotsMethod.invoke(board);
    }

    private void setSpriteVisible(Sprite sprite, boolean visible) throws Exception {
        Method setVisibleMethod = Sprite.class.getDeclaredMethod("setVisible", boolean.class);
        setVisibleMethod.setAccessible(true);
        setVisibleMethod.invoke(sprite, visible);
    }

    private Shot createShot(int x, int y, boolean visible) throws Exception {
        Shot shot = new Shot(0, 0);
        shot.setX(x);
        shot.setY(y);
        setSpriteVisible(shot, visible);
        return shot;
    }

    private Alien createAlien(int x, int y, boolean visible) throws Exception {
        Alien alien = new Alien(0, 0);
        alien.setX(x);
        alien.setY(y);
        setSpriteVisible(alien, visible);
        return alien;
    }

    private void invokeUpdateAliens(Board board) throws Exception {
        Method updateAliensMethod = Board.class.getDeclaredMethod("update_aliens");
        updateAliensMethod.setAccessible(true);
        updateAliensMethod.invoke(board);
    }

    private void invokeUpdateBomb(Board board) throws Exception {
        Method updateBombMethod = Board.class.getDeclaredMethod("update_bomb");
        updateBombMethod.setAccessible(true);
        updateBombMethod.invoke(board);
    }

    @Test
    void testGameInit_C3_GINIT3() throws Exception {
        Board board = crearBoard();
        List<Alien> aliens = board.getAliens();
        assertNotNull(aliens, "La lista de aliens no debería ser nula");
        assertEquals(24, aliens.size(), "Deben crearse 24 aliens");
        Alien alien0 = aliens.get(0);
        assertEquals(150, alien0.getX(), "Coordenada X del alien fila 0, columna 0");
        assertEquals(5, alien0.getY(), "Coordenada Y del alien fila 0, columna 0");
        Alien alien6 = aliens.get(6);
        assertEquals(150, alien6.getX(), "Coordenada X del alien fila 1, columna 0");
        assertEquals(23, alien6.getY(), "Coordenada Y del alien fila 1, columna 0");
    }


    @Test
    void testUpdate_C1_UPD1() throws Exception {
        Board board = crearBoard();
        board.setDeaths(25);
        invokeUpdate(board);
        assertFalse(board.isInGame(), "inGame debería ser false al alcanzar la condición de victoria");
        assertEquals("Game won!", board.getMessage(), "El mensaje debería ser \"Game won!\"");
    }

    @Test
    void testUpdate_C2_UPD2() throws Exception {
        Board board = crearBoard();
        board.setDeaths(0);
        invokeUpdate(board);
        assertTrue(board.isInGame(), "inGame debería mantenerse en true con deaths = 0");
        assertEquals("Game Over", board.getMessage(), "El mensaje debería ser \"Game Over\"");
    }


    @Test
    void testUpdateShots_C1_USHOT1() throws Exception {
        Board board = crearBoard();
        board.setDeaths(0);
        Shot shot = createShot(100, 150, false);
        board.setShot(shot);
        invokeUpdateShots(board);
        assertEquals(150, board.getShot().getY(), "Un disparo no visible no debe moverse");
        assertEquals(0, board.getDeaths(), "deaths debe mantenerse en 0");
    }

    @Test
    void testUpdateShots_C2_USHOT2() throws Exception {
        Board board = crearBoard();
        board.setDeaths(0);
        board.setAliens(new ArrayList<>());
        Shot shot = createShot(100, 150, true);
        board.setShot(shot);
        invokeUpdateShots(board);
        assertEquals(146, board.getShot().getY(), "El disparo debe subir SHOT_SPEED (4) píxeles hasta y = 146");
        assertTrue(board.getShot().isVisible(), "El disparo debe seguir visible");
        assertEquals(0, board.getDeaths(), "deaths debe mantenerse en 0");
    }

    @Test
    void testUpdateShots_C3_USHOT3() throws Exception {
        Board board = crearBoard();
        board.setDeaths(0);
        board.setAliens(new ArrayList<>());
        Shot shot = createShot(100, 2, true);
        board.setShot(shot);
        invokeUpdateShots(board);
        assertFalse(board.getShot().isVisible(), "El disparo debe dejar de ser visible al salir por arriba");
        assertEquals(0, board.getDeaths(), "deaths debe mantenerse en 0");
    }

    @Test
    void testUpdateShots_C4_USHOT4() throws Exception {
        Board board = crearBoard();
        board.setDeaths(0);
        Alien alien = createAlien(100, 100, false);
        board.setAliens(new ArrayList<>(List.of(alien)));
        Shot shot = createShot(100, 150, true);
        board.setShot(shot);
        invokeUpdateShots(board);
        assertEquals(146, board.getShot().getY(), "El disparo debe avanzar hasta y = 146");
        assertEquals(0, board.getDeaths(), "No debe aumentar deaths si el alien no es visible");
        assertFalse(alien.isDying(), "El alien no visible no debe marcarse como dying");
    }

    @Test
    void testUpdateShots_C5_USHOT5() throws Exception {
        Board board = crearBoard();
        board.setDeaths(0);
        Alien alien = createAlien(100, 100, true);
        board.setAliens(new ArrayList<>(List.of(alien)));
        Shot shot = createShot(200, 150, true);
        board.setShot(shot);
        invokeUpdateShots(board);
        assertEquals(146, board.getShot().getY(), "El disparo debe avanzar hasta y = 146");
        assertEquals(0, board.getDeaths(), "No debe haber colisión ni aumentar deaths");
        assertFalse(alien.isDying(), "El alien no alcanzado no debe marcarse como dying");
    }

    @Test
    void testUpdateShots_C6_USHOT6() throws Exception {
        Board board = crearBoard();
        board.setDeaths(0);
        Alien alien = createAlien(100, 100, true);
        board.setAliens(new ArrayList<>(List.of(alien)));
        Shot shot = createShot(105, 105, true);
        board.setShot(shot);
        invokeUpdateShots(board);
        assertTrue(alien.isDying(), "El alien alcanzado debe quedar en estado dying");
        assertEquals(1, board.getDeaths(), "deaths debe incrementarse a 1");
        assertFalse(board.getShot().isVisible(), "El disparo debe dejar de ser visible tras la colisión");
    }

    @Test
    void testUpdateAliens_C1_UALIEN1() throws Exception {
        Board board = crearBoard();
        board.setAliens(new ArrayList<>());
        board.setDirection(1);
        invokeUpdateAliens(board);
        assertEquals(1, board.getDirection(), "La dirección debe mantenerse en 1");
        assertTrue(board.isInGame(), "inGame debe mantenerse en true");
    }



    @Test
    void testUpdateBomb_C1_UBOMB1() throws Exception {
        Board board = crearBoard();
        board.setAliens(new ArrayList<>());
        invokeUpdateBomb(board);
        assertFalse(board.getPlayer().isDying(), "Sin aliens, el jugador no debe morir");
    }

    @Test
    void testUpdateBomb_C2_UBOMB2() throws Exception {
        Board board = crearBoard();
        Alien alien = createAlien(100, 100, false);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setX(100);
        bomb.setY(100);
        bomb.setDestroyed(true);
        board.setAliens(new ArrayList<>(List.of(alien)));
        invokeUpdateBomb(board);
        assertTrue(bomb.isDestroyed(), "Con el alien no visible, la bomba debe seguir destruida");
    }

    @Test
    void testUpdateBomb_C5_UBOMB5() throws Exception {
        Board board = crearBoard();
        Alien alien = createAlien(100, 100, true);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setX(182);
        bomb.setY(282);
        bomb.setDestroyed(false);
        board.setAliens(new ArrayList<>(List.of(alien)));
        invokeUpdateBomb(board);
        assertTrue(board.getPlayer().isDying(), "El jugador debe quedar en estado dying al ser alcanzado");
        assertTrue(bomb.isDestroyed(), "La bomba debe marcarse como destruida tras impactar al jugador");
    }

    @Test
    void testUpdateBomb_C6_UBOMB6() throws Exception {
        Board board = crearBoard();
        Alien alien = createAlien(100, 100, true);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setX(100);
        bomb.setY(100);
        bomb.setDestroyed(false);
        board.setAliens(new ArrayList<>(List.of(alien)));
        setSpriteVisible(board.getPlayer(), false);
        invokeUpdateBomb(board);
        assertEquals(101, bomb.getY(), "La bomba debe descender BOMB_SPEED (1) píxel hasta y = 101");
        assertFalse(bomb.isDestroyed(), "La bomba debe mantenerse activa al no impactar ni llegar al suelo");
    }

    @Test
    void testUpdateBomb_C7_UBOMB7() throws Exception {
        Board board = crearBoard();
        Alien alien = createAlien(100, 100, true);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setX(100);
        bomb.setY(290);
        bomb.setDestroyed(false);
        board.setAliens(new ArrayList<>(List.of(alien)));
        setSpriteVisible(board.getPlayer(), false);
        invokeUpdateBomb(board);
        assertTrue(bomb.isDestroyed(), "La bomba debe marcarse como destruida al superar el límite del suelo");
    }

}