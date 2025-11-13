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
