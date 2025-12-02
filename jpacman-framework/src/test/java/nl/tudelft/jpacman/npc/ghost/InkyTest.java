package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.*;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static nl.tudelft.jpacman.board.Direction.*;
import static org.junit.jupiter.api.Assertions.*;

public class InkyTest {

    private PacManSprites pacManSprites =  new PacManSprites();

    private PlayerFactory playerFactory = new PlayerFactory(pacManSprites);
    private GhostFactory ghostFactory =  new GhostFactory(pacManSprites);
    private LevelFactory levelFactory = new LevelFactory(pacManSprites, ghostFactory);
    private BoardFactory boardFactory = new BoardFactory(pacManSprites);

    private MapParser ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);



    // 1/3 BAD WEATHER
    @Test
    public void noBlinkyOnMapOKTest() {
        // build a level
        List<String> map = Arrays.asList(
            "#########",
            "#C     P#",
            "#########"
        );

        Level level = ghostMapParser.parseMap(map);
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        //add inky tanks to code because i can't put is manually on the map
        Ghost ghostFactoryInky = ghostFactory.createInky();

        Square square = level.getBoard().squareAt(4,1);
        ghostFactoryInky.occupy(square);

        Optional<Direction> direction = ghostFactoryInky.nextAiMove();

        assertEquals(Optional.empty(), direction);
    }

    // 2/3 BAD WEATHER
    /*
     * We test on this map if Inky correctly move through the map in (6,2) for this initial position
     * Blinky : (2,2)
     * PacMan : (4,4) who looks southward. So the two cases in direction of PacMan is (4,2)
     * Inky : (7,6)
     * BUT the result is (4,2) and not (6,2) for Inky
     * */
    @Test
    public void InkyDoesntTargetTheRightPlaceKOTest() {
        List<String> map = Arrays.asList(
            "#########",
            "##C   # #",
            "#       #",// <=> "# B     #",
            "#       #",
            "#  #P   #",
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
        Inky inky = (Inky) ghostFactoryInky;

        Square playerDestination = player.squaresAheadOf(2);
        List<Direction> firstHalf = Navigation.shortestPath(squareBlinky, playerDestination, null);
        Square finalDestination = inky.followPath(firstHalf, playerDestination);

        //Optional<Direction> direction = ghostFactoryInky.nextAiMove();
        //assertEquals(Optional.of(WEST), direction);
        //assertEquals(level.getBoard().squareAt(4,2), finalDestination);
        Board board = level.getBoard();
        int x = 0;
        int y = 0;
        for (int i = 0 ; i < board.getHeight() ; i++) {
            for (int j = 0 ; j < board.getWidth() ; j++) {
                if (board.squareAt(j, i) == finalDestination) {
                    x= j;
                    y= i;
                    break;
                }
            }
        }

        assertFalse(x==4 && y==2);
    }

    // 3/3 BAD WEATHER
    /*
     * We test on this map if Inky sends a correct answer when he doesn't have any path until the 2 cases in front of Pacman
     * Blinky : (2,2)
     * PacMan : (4,4) who looks southward. So the two cases in direction of PacMan is (4,2)
     * Inky : (7,6)
     * Resuslt : Inky is supposed to send a path equals to "empty"
     * */
    @Test
    public void InkyDoenstFoundAnyPathUntilPacManOKTest() {
        List<String> map = Arrays.asList(
            "#########",
            "##C  ## #",
            "#    #  #",// <=> "# B     #",
            "#  # ####",
            "#  #P####",
            "#  ######",
            "####### #",// <=> "#      I#",
            "#########"
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

        Optional<Direction> direction = ghostFactoryInky.nextAiMove();

        assertEquals(Optional.empty(), direction);
    }

    // 1/2 GOOD WEATHER
    /*
    * We test on this map if Inky correctly move through the map in (6,10) for this initial position
    * Blinky : (2,2)
    * PacMan : (4,4) who looks northward. So the two cases in direction of PacMan is (4,6)
    * Inky : (7,6)
    * */
    @Test
    public void goodMoveOfInkyOKTest() {
        List<String> map = Arrays.asList(
            "#########",
            "##C     #",
            "#       #",// <=> "# B      ",
            "#   #   #",
            "#   P#   ",
            "#       #",
            "#    #  #",// <=> "#        I",
            "#       #",
            "#       #",
            "#       #",
            "#       #",
            "#       #",
            "#       #",
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

        Optional<Direction> direction = ghostFactoryInky.nextAiMove();
        assertEquals(Optional.of(NORTH), direction);
    }

    // 2/2 GOOD WEATHER
    /*
     * We test on this map if Inky correctly move through the map in (6,2) for this initial position
     * Blinky : (2,2)
     * PacMan : (4,4) who looks southward. So the two cases in direction of PacMan is (4,2)
     * Inky : (7,6)
     * */
    @Test
    public void InkyDoesntTargetTheRightPlaceButMoveOKTest() {
        List<String> map = Arrays.asList(
            "#########",
            "##C     #",
            "#       #",// <=> "##B  ####",
            "#       #",
            "#  #P#   ",
            "#  ###  #",
            "#    #  #",// <=> "###      I",
            "#       #",
            "#       #",
            "#       #",
            "#       #",
            "#       #",
            "#       #",
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

        Optional<Direction> direction = ghostFactoryInky.nextAiMove();
        assertEquals(Optional.of(SOUTH), direction);
    }



}
