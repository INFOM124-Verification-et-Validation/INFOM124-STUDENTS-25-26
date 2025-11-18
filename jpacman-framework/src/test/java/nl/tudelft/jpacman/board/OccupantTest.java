package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen 
 *
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    /**
     * Asserts that a unit has no square to start with.
     */
    @Test
    void noStartSquare() {
        assertThat(unit.getSquare()).isEqualTo(null);
    }

    /**
     * Tests that the unit indeed has the target square as its base after
     * occupation.
     */
    @Test
    void testOccupy() {
        Square square = new BasicSquare();
        unit.occupy(square);
        assertThat(unit.getSquare()).isEqualTo(square);
        assertThat(square.getOccupants()).containsExactly(unit);
    }

    /**
     * Test that the unit indeed has the target square as its base after
     * double occupation.
     */
    @Test
    void testReoccupy() {
        Square squareA = new BasicSquare();
        Square squareB = new BasicSquare();
        unit.occupy(squareA);
        unit.occupy(squareB);
        assertThat(unit.getSquare()).isEqualTo(squareB);
        assertThat(squareB.getOccupants()).containsExactly(unit);
        assertThat(squareA.getOccupants()).isEmpty();
    }
}
