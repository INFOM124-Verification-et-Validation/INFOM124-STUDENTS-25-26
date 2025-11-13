package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.sprite.Sprite;

/**
 * Basic implementation of unit.
 *
 * @author Jeroen Roosen 
 */
class BasicUnit extends Unit {

    /**
     * Creates a new basic unit.
     */
    BasicUnit() {
        super();
    }

    @Override
    @SuppressWarnings("return.type.incompatible")
    public Sprite getSprite() {
        return null;
    }
}

/**
 * Tests for Unit.squaresAheadOf() using specification-based testing.
 */
public class UnitTest {

    @Test
    void testSquaresAheadOfEastDirection() {
        // Plateau 5x5
        Board board = new Board(5, 5);
        Square start = board.squareAt(2, 2);
        Unit unit = new BasicUnit();
        unit.occupy(start);
        unit.setDirection(Direction.EAST);

        // Déplace de 2 cases vers l'Est
        Square result = unit.squaresAheadOf(2);

        // La case attendue est (4,2)
        assertThat(result).isEqualTo(board.squareAt(4, 2));
    }

    @Test
    void testSquaresAheadOfNorthDirection() {
        Board board = new Board(5, 5);
        Square start = board.squareAt(2, 3);
        Unit unit = new BasicUnit();
        unit.occupy(start);
        unit.setDirection(Direction.NORTH);

        // 1 case vers le Nord → (2,2)
        Square result = unit.squaresAheadOf(1);

        assertThat(result).isEqualTo(board.squareAt(2, 2));
    }

    @Test
    void testSquaresAheadOfAtEdgeStopsCorrectly() {
        Board board = new Board(3, 3);
        Square start = board.squareAt(0, 1); // tout à gauche
        Unit unit = new BasicUnit();
        unit.occupy(start);
        unit.setDirection(Direction.WEST);

        // Essaye d'aller 2 cases à gauche — bord du plateau
        Square result = unit.squaresAheadOf(2);

        // Doit rester à la case du bord (0,1)
        assertThat(result).isEqualTo(board.squareAt(0, 1));
    }

    @Test
    void testSquaresAheadOfZeroDistanceReturnsSameSquare() {
        Board board = new Board(3, 3);
        Square start = board.squareAt(1, 1);
        Unit unit = new BasicUnit();
        unit.occupy(start);
        unit.setDirection(Direction.EAST);

        // amountToLookAhead = 0 → case actuelle
        Square result = unit.squaresAheadOf(0);

        assertThat(result).isEqualTo(start);
    }
}
