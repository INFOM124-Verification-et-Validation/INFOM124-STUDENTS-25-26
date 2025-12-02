package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.npc.ghost.GhostMapParser;
import nl.tudelft.jpacman.npc.ghost.Inky;
import nl.tudelft.jpacman.npc.ghost.Navigation;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static nl.tudelft.jpacman.board.Direction.NORTH;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerCollisionTest {

    private PacManSprites pacManSprites =  new PacManSprites();

    private PlayerFactory playerFactory = new PlayerFactory(pacManSprites);
    private GhostFactory ghostFactory =  new GhostFactory(pacManSprites);
    
    @Test
    public void PacManVSGhostTest() {

        Ghost inky = ghostFactory.createInky();
        Player pacman = playerFactory.createPacMan();

        PlayerCollisions collision = new PlayerCollisions();
        collision.collide(pacman, inky);

        assertFalse(pacman.isAlive());
    }

    @Test
    public void PacManVSPelletTest() {

        Player pacman = playerFactory.createPacMan();
        Pellet pellet = new Pellet(10, pacManSprites.getPelletSprite());
        PlayerCollisions collision = new PlayerCollisions();

        int previousScore = pacman.getScore();

        collision.collide(pacman, pellet);
        assertEquals(pacman.getScore(), 10);
        assertEquals(previousScore+10, pacman.getScore());
    }

    @Test
    public void GhostVSPelletTest() {
        Ghost inky = ghostFactory.createInky();
        Pellet pellet = new Pellet(10, inky.getSprite());
        PlayerCollisions collision = new PlayerCollisions();
        collision.collide(inky, pellet);
        assertNotNull(pellet);
    }

    @Test
    public void GhostVSGhostTest() {
        Ghost inky = ghostFactory.createInky();
        Ghost clyde = ghostFactory.createClyde();
        PlayerCollisions collision = new PlayerCollisions();
        collision.collide(inky, clyde);
        assertNotNull(inky);
        assertNotNull(clyde);

    }


}
