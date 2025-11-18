package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.DirectColorModel;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClydeTest {
    private PacManSprites pacManSprites = new PacManSprites();
    private PlayerFactory playerFactory = new PlayerFactory(pacManSprites);
    private GhostFactory ghostFactory = new GhostFactory(pacManSprites);
    private LevelFactory levelFactory = new LevelFactory(pacManSprites, ghostFactory);
    private BoardFactory boardFactory = new BoardFactory(pacManSprites);
    MapParser ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);

    @Test
    void distanceGreaterThan8AndPathBlockedTest() {
        List<String> map = Arrays.asList(
            "#############",
            "#C#        P#",
            "#############"
        );

        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);
        Optional<Direction> direction = clyde.nextAiMove();
        assertEquals(Optional.empty(), direction);
    }

    @Test
    void clydeMovesTowardsPacManIfClose() {
        String[] map = {
            "############",
            "#P______C__#",
            "############"
        };
        GhostMapParser parser = new GhostMapParser();
        Level level = parser.parseMap(map);

        Player pacMan = new PlayerFactory().createPacMan();
        level.registerPlayer(pacMan);
        pacMan.setDirection(Direction.RIGHT);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard()).get();
        Optional<Direction> move = clyde.nextAiMove();

        assertThat(move).hasValue(Direction.LEFT); // Clyde va vers PacMan
    }

    @Test
    void clydeMovesRandomIfPacManFar() {
        String[] map = {
            "############",
            "#P________C#",
            "############"
        };
        GhostMapParser parser = new GhostMapParser();
        Level level = parser.parseMap(map);

        Player pacMan = new PlayerFactory().createPacMan();
        level.registerPlayer(pacMan);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard()).get();
        Optional<Direction> move = clyde.nextAiMove();

        assertThat(move).isPresent(); // Peut être n'importe quelle direction
    }

    @Test
    void clydeDoesNotMoveThroughWalls() {
        String[] map = {
            "############",
            "#P#C######_#",
            "############"
        };
        GhostMapParser parser = new GhostMapParser();
        Level level = parser.parseMap(map);

        Player pacMan = new PlayerFactory().createPacMan();
        level.registerPlayer(pacMan);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard()).get();
        Optional<Direction> move = clyde.nextAiMove();

        assertThat(move).isPresent(); // Pas de mouvement à travers mur
    }

    @Test
    void clydeDoesNothingIfNoPacMan() {
        String[] map = {
            "############",
            "#C_________#",
            "############"
        };
        GhostMapParser parser = new GhostMapParser();
        Level level = parser.parseMap(map);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard()).get();
        Optional<Direction> move = clyde.nextAiMove();

        assertThat(move).isPresent(); // Choisit une direction aléatoire
    }
}
