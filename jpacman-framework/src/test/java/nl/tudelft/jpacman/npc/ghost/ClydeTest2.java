package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static nl.tudelft.jpacman.board.Direction.*;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClydeTest2 {

    private PacManSprites pacManSprites =  new PacManSprites();

    private PlayerFactory playerFactory = new PlayerFactory(pacManSprites);
    private GhostFactory ghostFactory =  new GhostFactory(pacManSprites);
    private LevelFactory levelFactory = new LevelFactory(pacManSprites, ghostFactory);
    private BoardFactory boardFactory = new BoardFactory(pacManSprites);

    MapParser ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);

    @Test
    public void distanceGreaterThan8AndPathIsBlockedTest() {
        // build a level
        List<String> map = Arrays.asList(
            "##############",
            "#C#         P#",
            "##############"
        );

        Level level = ghostMapParser.parseMap(map);
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Optional<Direction> direction = clyde.nextAiMove();
        //assert direction.equals(Optional.of(Direction.EAST));
        assertEquals(Optional.empty(), direction);
    }

    @Test
    public void distanceLesserThan8AndPathIsBlockedTest() {
        List<String> map = Arrays.asList(
            "##########",
            "#C#     P#",
            "##########"
        );

        Level level = ghostMapParser.parseMap(map);
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Optional<Direction> direction = clyde.nextAiMove();

        assertTrue(
            direction.equals(Optional.empty())
        );
    }

    /*@Test
    public void distanceLesserThan8AndPathIsMultipleTest() {
        List<String> map = Arrays.asList(
            "# ########",
            "#C      P#",
            "# ########"
        );

        Level level = ghostMapParser.parseMap(map);
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Optional<Direction> direction = clyde.nextAiMove();
        System.out.println(direction);
        assertTrue(
                direction.equals(Optional.of(NORTH))||
                direction.equals(Optional.of(SOUTH))
        );
    }*/

    @Test
    public void distanceLesserThan8AndPathIsFreeTest() {
        List<String> map = Arrays.asList(
            "# ########",
            " C      P#",
            "# ########"
        );

        Level level = ghostMapParser.parseMap(map);
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Optional<Direction> direction = clyde.nextAiMove();

        assertTrue(
            direction.equals(Optional.of(WEST)) ||
            direction.equals(Optional.of(NORTH))||
            direction.equals(Optional.of(SOUTH))
        );
    }

    @Test
    public void distanceIsEqualTo8AndPathIsFreeTest() {
        List<String> map = Arrays.asList(
            "# ##########",
            " C        P#",
            "# ##########"
        );

        Level level = ghostMapParser.parseMap(map);
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Optional<Direction> direction = clyde.nextAiMove();
        System.out.println(direction);
        assertTrue(
            direction.equals(Optional.of(WEST)) ||
                direction.equals(Optional.of(NORTH))||
                direction.equals(Optional.of(SOUTH))||
                direction.equals(Optional.of(EAST))
        );
    }


}
