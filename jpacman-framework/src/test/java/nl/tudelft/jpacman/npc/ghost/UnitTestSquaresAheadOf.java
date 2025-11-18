package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.npc.ghost.Clyde;
import nl.tudelft.jpacman.parser.GhostMapParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UnitTestSquaresAheadOf {

    @Test
    void squaresAheadUp() {
        String[] map = {
            "#####",
            "#C__#",
            "#####"
        };
        GhostMapParser parser = new GhostMapParser();
        Board board = parser.parseMap(map).getBoard();

        Clyde clyde = parser.parseMap(map).getBoard().getSquare(1,1).getOccupants().get(0);
        List<Square> squares = clyde.squaresAheadOf(Direction.UP, 1);

        assertThat(squares).hasSize(1);
    }

    @Test
    void squaresAheadRightMultiple() {
        String[] map = {
            "#####",
            "#C__#",
            "#####"
        };
        GhostMapParser parser = new GhostMapParser();
        Board board = parser.parseMap(map).getBoard();

        Clyde clyde = parser.parseMap(map).getBoard().getSquare(1,1).getOccupants().get(0);
        List<Square> squares = clyde.squaresAheadOf(Direction.RIGHT, 2);

        assertThat(squares).hasSize(2);
    }

    @Test
    void squaresAheadBlockedByWall() {
        String[] map = {
            "#####",
            "#C#_#",
            "#####"
        };
        GhostMapParser parser = new GhostMapParser();
        Board board = parser.parseMap(map).getBoard();

        Clyde clyde = parser.parseMap(map).getBoard().getSquare(1,1).getOccupants().get(0);
        List<Square> squares = clyde.squaresAheadOf(Direction.RIGHT, 3);

        assertThat(squares).hasSize(1);
    }
}
