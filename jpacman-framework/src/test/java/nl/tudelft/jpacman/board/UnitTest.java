package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.npc.ghost.GhostMapParser;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static nl.tudelft.jpacman.board.Direction.NORTH;
import static nl.tudelft.jpacman.board.Direction.SOUTH;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {

    private PacManSprites pacManSprites =  new PacManSprites();

    private PlayerFactory playerFactory = new PlayerFactory(pacManSprites);
    private GhostFactory ghostFactory =  new GhostFactory(pacManSprites);
    private LevelFactory levelFactory = new LevelFactory(pacManSprites, ghostFactory);
    private BoardFactory boardFactory = new BoardFactory(pacManSprites);

    private MapParser ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);



    // 1/3
    @Test
    public void squareAheaudOfWithParamEqualsTo0Test() {
        List<String> map = Arrays.asList(
            "#########",
            "##C   # #",
            "#       #",// <=> "# B     #",
            "#       #",
            "#  #P   #", // P = (4,4)
            "#  ##   #",
            "#       #",// <=> "#      I#",
            "#       #"
        );

        Level level = ghostMapParser.parseMap(map);
        Player player = playerFactory.createPacMan();
        player.setDirection(NORTH);
        level.registerPlayer(player);

        //add Blinky tanks to code because I can't put is manually on the map
        Ghost ghostFactoryBlinky = ghostFactory.createBlinky();
        Square squareBlinky = level.getBoard().squareAt(2,2);
        ghostFactoryBlinky.occupy(squareBlinky);

        //add inky tanks to code because I can't put is manually on the map
        Ghost ghostFactoryInky = ghostFactory.createInky();
        Square squareInky = level.getBoard().squareAt(7,6);
        ghostFactoryInky.occupy(squareInky);

        Square playerDestination = player.squaresAheadOf(0);

        Board board = level.getBoard();
        int x = 0;
        int y = 0;
        for (int i = 0 ; i < board.getHeight() ; i++) {
            for (int j = 0 ; j < board.getWidth() ; j++) {
                if (board.squareAt(j, i) == playerDestination) {
                    x= j;
                    y= i;
                    break;
                }
            }
        }

        assertTrue(x==4 && y==4);
    }

    // 2/3
    @Test
    public void squareAheaudOfWithParamEqualsToANegativeNumberTest() {
        List<String> map = Arrays.asList(
            "#########",
            "##C   # #",
            "#       #",// <=> "# B     #",
            "#       #",
            "#  #P   #", // P = (4,4)
            "#  ##   #",
            "#       #",// <=> "#      I#",
            "#       #"
        );

        Level level = ghostMapParser.parseMap(map);
        Player player = playerFactory.createPacMan();
        player.setDirection(SOUTH);
        level.registerPlayer(player);

        //add Blinky tanks to code because I can't put is manually on the map
        Ghost ghostFactoryBlinky = ghostFactory.createBlinky();
        Square squareBlinky = level.getBoard().squareAt(2,2);
        ghostFactoryBlinky.occupy(squareBlinky);

        //add inky tanks to code because I can't put is manually on the map
        Ghost ghostFactoryInky = ghostFactory.createInky();
        Square squareInky = level.getBoard().squareAt(7,6);
        ghostFactoryInky.occupy(squareInky);

        Square playerDestination = player.squaresAheadOf(-2);

        Board board = level.getBoard();
        int x = 0;
        int y = 0;
        for (int i = 0 ; i < board.getHeight() ; i++) {
            for (int j = 0 ; j < board.getWidth() ; j++) {
                if (board.squareAt(j, i) == playerDestination) {
                    x= j;
                    y= i;
                    break;
                }
            }
        }
        assertTrue(x==4 && y==4);
    }

    // 3/3

    /*@Test
    public void squareAheaudOfWithParamEqualsToAOutOfRangeOfTheMapTest() {
        List<String> map = Arrays.asList(
            "#########",
            "##C   # #",
            "#       #",// <=> "# B     #",
            "#       #",
            "#  #P   #", // P = (4,4)
            "#  ##   #",
            "#       #",// <=> "#      I#",
            "#       #"
        );

        Level level = ghostMapParser.parseMap(map);
        Player player = playerFactory.createPacMan();
        player.setDirection(NORTH);
        level.registerPlayer(player);

        //add Blinky tanks to code because I can't put is manually on the map
        Ghost ghostFactoryBlinky = ghostFactory.createBlinky();
        Square squareBlinky = level.getBoard().squareAt(2,2);
        ghostFactoryBlinky.occupy(squareBlinky);

        //add inky tanks to code because I can't put is manually on the map
        Ghost ghostFactoryInky = ghostFactory.createInky();
        Square squareInky = level.getBoard().squareAt(7,6);
        ghostFactoryInky.occupy(squareInky);

        Square playerDestination = player.squaresAheadOf(20);

        Board board = level.getBoard();
        int x = 0;
        int y = 0;
        for (int i = 0 ; i < board.getHeight() ; i++) {
            for (int j = 0 ; j < board.getWidth() ; j++) {
                if (board.squareAt(j, i) == playerDestination) {
                    x= j;
                    y= i;
                    break;
                }
            }
        }

        System.out.println(x + " - " + y);
        assertTrue(x==4 && y==4);
    }*/
}
