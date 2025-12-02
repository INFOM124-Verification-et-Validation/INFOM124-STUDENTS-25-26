package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.npc.ghost.GhostMapParser;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerCollisionTest {

    private PacManSprites pacManSprites = new PacManSprites();
    private GhostFactory ghostfactory = new GhostFactory(pacManSprites);
    private PlayerFactory playerfactory = new PlayerFactory(pacManSprites);
    private LevelFactory levelfactory = new LevelFactory(pacManSprites, ghostfactory);
    private BoardFactory boardfactory = new BoardFactory(pacManSprites);
    MapParser ghostMapParser = new GhostMapParser(levelfactory, boardfactory, ghostfactory);
    private PlayerCollisions collisions = new PlayerCollisions();
    private Pellet pellet = new Pellet(10, pacManSprites.getPelletSprite());

    List<String> map = Arrays.asList(
        "#############",
        "#C#        P#",
        "#############"
    );

    Level level = ghostMapParser.parseMap(map);

    @Test
    public void testPlayerGhostCollision() {
        Player player = playerfactory.createPacMan();
        Ghost ghost = ghostfactory.createClyde();
        assertEquals(player.isAlive(), true);
        assertEquals(level.isInProgress(), false);
        collisions.collide(player, ghost);
        assertEquals(player.isAlive(), false);
    }

    @Test
    public void testPlayerPelletCollision() {
        Player player = playerfactory.createPacMan();
        int score = player.getScore();
        collisions.collide(player, pellet);;
        assertEquals(player.getScore(), score + 10);
    }
}
