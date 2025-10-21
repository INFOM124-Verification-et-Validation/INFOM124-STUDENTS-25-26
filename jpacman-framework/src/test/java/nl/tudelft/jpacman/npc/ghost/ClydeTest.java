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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ClydeTest {

    /*private PacManSprites pacManSprites = new PacManSprites();
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
    }*/

    private GhostMapParser parser;
    private PlayerFactory playerFactory;

    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        BoardFactory boardFactory = new BoardFactory(sprites);
        GhostFactory ghostFactory = new GhostFactory(sprites);
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory);

        parser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        playerFactory = new PlayerFactory(sprites);
    }

    @Test
    void smallDistancePathFree() {
        String[] map = {
            "############",
            "#P    C    #",
            "############"
        };

        Level level = parser.parseMap(List.of(map));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> nextMove = clyde.nextAiMove();

        assertThat(nextMove).isPresent();
        assertThat(nextMove.get()).isEqualTo(Direction.EAST);
    }


    @Test
    void smallDistancePathKO() {
        String[] map = {
            "############",
            "#P    #C#  #",
            "############"
        };

        Level level = parser.parseMap(List.of(map));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> nextMove = clyde.nextAiMove();

        assertThat(nextMove).isEmpty();
    }


    @Test
    void bigDistancePathFree() {
        String[] map = {
            "############",
            "#P         #",
            "            ",
            "            ",
            "            ",
            "            ",
            "            ",
            "            ",
            "           C",
            "############"
        };

        Level level = parser.parseMap(List.of(map));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> nextMove = clyde.nextAiMove();

        assertThat(nextMove).isPresent();
        assertThat(nextMove.get()).isEqualTo(Direction.NORTH);
    }


    @Test
    void bigDistancePathKO() {
        String[] map = {
            "############",
            "#P         #",
            "            ",
            "            ",
            "            ",
            "            ",
            "            ",
            "         ###",
            "         #C#",
            "############"
        };

        Level level = parser.parseMap(List.of(map));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> nextMove = clyde.nextAiMove();

        assertThat(nextMove).isEmpty();
    }

    @Test
    void multiplePacman() {
        String[] map = {
            "############",
            "#P         #",
            "            ",
            "            ",
            "    P       ",
            "            ",
            "            ",
            "            ",
            "     P     C",
            "############"
        };

        Level level = parser.parseMap(List.of(map));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> nextMove = clyde.nextAiMove();

        assertThat(nextMove).isPresent();
        assertThat(nextMove.get()).isEqualTo(Direction.NORTH);
    }

    @Test
    void multipleClyde() {
        String[] map = {
            "############",
            "#P         #",
            "            ",
            " C          ",
            "        C   ",
            "            ",
            "            ",
            "    C       ",
            "           C",
            "############"
        };

        Level level = parser.parseMap(List.of(map));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> nextMove = clyde.nextAiMove();

        assertThat(nextMove).isPresent();
        assertThat(nextMove.get()).isEqualTo(Direction.SOUTH);
    }

    @Test
    void smallDistanceMultiplePathFree() {
        String[] map = {
                        "############",
                        "            ",
                        "# P  # C   #",
                        "            ",
                        "############"
                       };

        Level level = parser.parseMap(List.of(map));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();

        Optional<Direction> nextMove = clyde.nextAiMove();

        assertThat(nextMove).isPresent();
        assertThat(nextMove.get()).isEqualTo(Direction.SOUTH);
            }


    @Test
    void BigDistanceMultiplePathFree() {
        String[] map = {
                        "############",
                        "     P      ",
                        "            ",
                        "            ",
                        "            ",
                        "            ",
                        "            ",
                        "            ",
                        "     #      ",
                        "     C     #",
                        "            ",
                        "############"
                        };

        Level level = parser.parseMap(List.of(map));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde).isNotNull();
        Optional<Direction> nextMove = clyde.nextAiMove();
        assertThat(nextMove).isPresent();
        assertThat(nextMove.get()).isEqualTo(Direction.WEST);
    }
}
