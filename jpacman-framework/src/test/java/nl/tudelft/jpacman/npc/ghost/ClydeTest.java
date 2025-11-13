package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.Navigation;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.level.Level;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;

// TEST CASE
// ---------

// **Distance < 8**
// 1. Path free $\Rightarrow$ Move away
// 2. Path blocked $\Rightarrow$ Empty direction
// 3. Multiple moves $\Rightarrow$ Move away

// **Distance > 8**
// 4. Path free $\Rightarrow$ Move towards
// 5. Path blocked $\Rightarrow$ Empty direction
// 6. Multiple moves $\Rightarrow$ Move towards

// **Distance = 8**
// 7. Path free $\Rightarrow$ Move away
// 8. Path blocked $\Rightarrow$ Empty direction
// 9. Multiple moves $\Rightarrow$ Move away

// **Boundary cases**

// 1.  Multiple Clyde instances on the map
// 2.  Multiple Pac-Man instances on the map
// 3.  Clyde is on Pac
// 4.  Pac-Man not on the board
// 5.  Pac-Man does not have a square
// 6.  Pac-Man at the edge of the board
// 7.  Pac-Man goes from one edge of the board to another

public class ClydeTest {
    private PacManSprites sprites = new PacManSprites();

    private PlayerFactory playerFactory = new PlayerFactory(sprites);
    private GhostFactory ghostFactory = new GhostFactory(sprites);
    private LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory);
    private BoardFactory boardFactory = new BoardFactory(sprites);


    // The Clyde ghost under test.
    private Clyde clyde;

    // The player (Pac-Man).
    private Player player;

    // Map parser used to construct boards.
    private MapParser parser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);

    // Sets up the test environment with Clyde and a player on a simple board.

    public void setUp(String[] map) {

        Level level = parser.parseMap(Lists.newArrayList(map));

        player = playerFactory.createPacMan();
        level.registerPlayer(player);

        clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

    }

    // Simple test to check that setup works
    @Test
    void testSetup() {
        String[] map = {"######",
            "#C   #",
            "#    #",
            "#    #",
            "#   P#",
            "######"};
        setUp(map);
        assertThat(clyde).isNotNull();
        assertThat(player).isNotNull();
        assertThat(clyde.getSquare()).isNotNull();
        assertThat(player.getSquare()).isNotNull();
    }

    // 1. Distance < 8, Path free => Move away
    @Test
    void testClydeMovesAwayWhenCloseAndPathFree() {
        String[] map = {"######",
            "#    #",
            "#C#  #",
            "#P   #",
            "#    #",
            "######"};
        setUp(map);
        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).isPresent();
        assertThat(move.get()).isEqualTo(Direction.NORTH);
    }

    // 2. Distance < 8, Path blocked => Empty direction
    @Test
    void testClydeMovesAwayWhenCloseAndNoPath(){
        String[] map = {"######",
            "#    #",
            "#    #",
            "#   ##",
            "#  PC#",
            "######"};
        setUp(map);
        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move.isEmpty());
    }

    // 3. Distance < 8, Multiple moves => Move away
    @Test
    void testClydeMovesAwayWhenCloseAndMultiplePaths(){
        String[] map = {"########",
            "#      #",
            "#  C   #",
            "#      #",
            "#   P  #",
            "########"};
        setUp(map);
        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).isPresent();
        assertThat(move.get()).isIn(Direction.NORTH, Direction.EAST, Direction.WEST);
    }

    // 4. Distance > 8, Path free => Move towards
    @Test
    void testClydeMovesTowardsWhenFarAndPathFree() {
        String[] map = {"##########",
            "#C#      #",
            "#        #",
            "#        #",
            "#        #",
            "#        #",
            "#       P#",
            "##########"};

        setUp(map);
        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).isPresent();
        assertThat(move.get()).isEqualTo(Direction.SOUTH);
    }

    // 5. Distance > 8, Path blocked => Empty direction
    @Test
    void testClydeMovesTowardsWhenFarAndNoPath(){
        String[] map = {"##########",
            "#C#      #",
            "##       #",
            "#        #",
            "#        #",
            "#        #",
            "#       P#",
            "##########"};
        setUp(map);
        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move.isEmpty());
    }

    // 6. Distance > 8, Multiple moves => Move towards
    @Test
    void testClydeMovesTowardsWhenFarAndMultiplePaths(){
        String[] map = {"##########",
            "#        #",
            "#C       #",
            "#        #",
            "#        #",
            "#        #",
            "#        #",
            "#        #",
            "#        #",
            "#        #",
            "#        #",
            "#    P   #",
            "##########"};
        setUp(map);
        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).isPresent();
        assertThat(move.get()).isIn(Direction.SOUTH, Direction.EAST);
        assertThat(move.get()).isNotEqualTo(Direction.NORTH);
    }

    // 7. Distance = 8, Path free => Move away
    @Test
    void testClydeMovesAwayWhen8AndPathFree() {
        String[] map = {"########",
            "#      #",
            "#  C   #",
            "#      #",
            "#      #",
            "#      #",
            "#      #",
            "#      #",
            "#      #",
            "#      #",
            "#  P   #",
            "########"};
        setUp(map);
        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).isPresent();
        assertThat(move.get()).isIn(Direction.NORTH, Direction.EAST, Direction.WEST);
        assertThat(move.get()).isNotEqualTo(Direction.SOUTH);
    }

    // 8. Distance = 8, Path blocked => Empty direction
    @Test
    void testClydeMovesAwayWhen8AndNoPath(){
        String[] map = {"#########",
            "###C#####",
            "#########",
            "#########",
            "#########",
            "#########",
            "#########",
            "#########",
            "#########",
            "#P      #",
            "#########"};
        setUp(map);
        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).isEmpty();
        assertThat(move).isNotPresent();
    }

    // 9. Distance = 8, Multiple moves => Move away
    @Test
    void testClydeMovesAwayWhen8AndMultiplePaths(){
        String[] map = {"#############",
            "# C        P#",
            "#############"};

        setUp(map);
        Optional<Direction> move = clyde.nextAiMove();
        assertThat(move).isPresent();
        assertThat(move.get()).isIn(Direction.NORTH, Direction.EAST, Direction.WEST);
        assertThat(move.get()).isNotEqualTo(Direction.SOUTH);
    }


}
