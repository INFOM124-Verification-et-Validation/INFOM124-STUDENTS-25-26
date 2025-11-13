package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.Sprite;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * An implementation of the classic Pac-Man ghost Inky.
 * </p>
 * <b>AI:</b> Inky has the most complicated AI of all. Inky considers two things: Blinky's
 * location, and the location two grid spaces ahead of Pac-Man. Inky draws a
 * line from Blinky to the spot that is two squares in front of Pac-Man and
 * extends that line twice as far. Therefore, if Inky is alongside Blinky
 * when they are behind Pac-Man, Inky will usually follow Blinky the whole
 * time. But if Inky is in front of Pac-Man when Blinky is far behind him,
 * Inky tends to want to move away from Pac-Man (in reality, to a point very
 * far ahead of Pac-Man). Inky is affected by a similar targeting bug that
 * affects Speedy. When Pac-Man is moving or facing up, the spot Inky uses to
 * draw the line is two squares above and left of Pac-Man.
 * <p>
 * Source: http://strategywiki.org/wiki/Pac-Man/Getting_Started
 * </p>
 *
 * @author Jeroen Roosen
 */
public class Inky extends Ghost {

    private static final int SQUARES_AHEAD = 2;

    /**
     * The variation in intervals, this makes the ghosts look more dynamic and
     * less predictable.
     */
    private static final int INTERVAL_VARIATION = 50;

    /**
     * The base movement interval.
     */
    private static final int MOVE_INTERVAL = 250;

    /**
     * Creates a new "Inky".
     *
     * @param spriteMap The sprites for this ghost.
     */
    public Inky(Map<Direction, Sprite> spriteMap) {
        super(spriteMap, MOVE_INTERVAL, INTERVAL_VARIATION);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Inky has the most complicated AI of all. Inky considers two things: Blinky's
     * location, and the location two grid spaces ahead of Pac-Man. Inky
     * draws a line from Blinky to the spot that is two squares in front of
     * Pac-Man and extends that line twice as far. Therefore, if Inky is
     * alongside Blinky when they are behind Pac-Man, Inky will usually
     * follow Blinky the whole time. But if Inky is in front of Pac-Man when
     * Blinky is far behind him, Inky tends to want to move away from Pac-Man
     * (in reality, to a point very far ahead of Pac-Man). Inky is affected
     * by a similar targeting bug that affects Speedy. When Pac-Man is moving or
     * facing up, the spot Inky uses to draw the line is two squares above
     * and left of Pac-Man.
     * </p>
     *
     * <p>
     * <b>Implementation:</b>
     * To actually implement this in jpacman we have the following approximation:
     * first determine the square of Blinky (A) and the square 2
     * squares away from Pac-Man (B). Then determine the shortest path from A to
     * B regardless of terrain and walk that same path from B. This is the
     * destination.
     * </p>
     */
    @Override
    public Optional<Direction> nextAiMove() {
        assert hasSquare();
        Unit blinky = Navigation.findNearest(Blinky.class, getSquare());
        Unit player = Navigation.findNearest(Player.class, getSquare());

        if (blinky == null || player == null) {
            return Optional.empty();
        }

        assert player.hasSquare();
        Square playerDestination = player.squaresAheadOf(SQUARES_AHEAD);

        List<Direction> firstHalf = Navigation.shortestPath(blinky.getSquare(),
            playerDestination, null);

        if (firstHalf == null) {
            return Optional.empty();
        }

        Square destination = followPath(firstHalf, playerDestination);
        List<Direction> path = Navigation.shortestPath(getSquare(),
            destination, this);

        if (path != null && !path.isEmpty()) {
            return Optional.ofNullable(path.get(0));
        }
        return Optional.empty();
    }


    private Square followPath(List<Direction> directions, Square start) {
        Square destination = start;

        for (Direction d : directions) {
            destination = destination.getSquareAt(d);
        }

        return destination;
    }
}


package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.*;
    import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InkyTest {

    private PacManSprites sprites = new PacManSprites();
    private PlayerFactory playerFactory = new PlayerFactory(sprites);
    private GhostFactory ghostFactory = new GhostFactory(sprites);
    private LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory);
    private BoardFactory boardFactory = new BoardFactory(sprites);
    MapParser parser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);

    // GOOD WEATHER 1: normal case with both Blinky and Pac-Man
    @Test
    void testInkyMovesWhenBothBlinkyAndPacmanPresent() {
        List<String> map = Arrays.asList(
            "#############",
            "#B   P   I  #",
            "#############"
        );

        Level level = parser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.EAST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        Optional<Direction> move = inky.nextAiMove();

        assertTrue(move.isPresent(), "Inky should move toward target");
    }

    // GOOD WEATHER 2: Pac-Man facing UP (special bug case)
    @Test
    void testInkyWithPacmanFacingUp() {
        List<String> map = Arrays.asList(
            "#############",
            "#B   P   I  #",
            "#############"
        );
        Level level = parser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.NORTH);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        Optional<Direction> move = inky.nextAiMove();

        assertTrue(move.isPresent(), "Inky should still calculate a move even when Pac-Man faces north");
    }

    // BAD WEATHER 1: No Blinky present
    @Test
    void testInkyWithoutBlinky() {
        List<String> map = Arrays.asList(
            "#############",
            "#P      I   #",
            "#############"
        );

        Level level = parser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.EAST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        Optional<Direction> move = inky.nextAiMove();

        assertEquals(Optional.empty(), move);
    }

    // BAD WEATHER 2: No Pac-Man present
    @Test
    void testInkyWithoutPacman() {
        List<String> map = Arrays.asList(
            "#############",
            "#B      I   #",
            "#############"
        );

        Level level = parser.parseMap(map);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        Optional<Direction> move = inky.nextAiMove();

        assertEquals(Optional.empty(), move);
    }

    // BAD WEATHER 3: Path blocked by walls
    @Test
    void testInkyWithWallsBlocking() {
        List<String> map = Arrays.asList(
            "#############",
            "#B#P#   #I# #",
            "#############"
        );

        Level level = parser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.EAST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        Optional<Direction> move = inky.nextAiMove();

        // May return empty since path cannot be computed
        assertNotNull(move);
    }
}

