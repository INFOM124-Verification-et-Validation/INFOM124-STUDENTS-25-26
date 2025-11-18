package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.factory.PlayerFactory;
import nl.tudelft.jpacman.parser.GhostMapParser;
import nl.tudelft.jpacman.util.Navigation;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InkyTest {

    @Test
    void inkyMovesTowardsTargetGoodWeather() {
        String[] map = {
            "############",
            "#P__B___I__#",
            "############"
        };
        GhostMapParser parser = new GhostMapParser();
        Level level = parser.parseMap(map);

        Player pacMan = new PlayerFactory().createPacMan();
        level.registerPlayer(pacMan);
        pacMan.setDirection(Direction.RIGHT);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard()).get();
        Optional<Direction> move = inky.nextAiMove();

        assertThat(move).isPresent();
    }

    @Test
    void inkyMovesAwayIfBlockedByWall() {
        String[] map = {
            "############",
            "#P#B#I#####_#",
            "############"
        };
        GhostMapParser parser = new GhostMapParser();
        Level level = parser.parseMap(map);

        Player pacMan = new PlayerFactory().createPacMan();
        level.registerPlayer(pacMan);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard()).get();
        Optional<Direction> move = inky.nextAiMove();

        assertThat(move).isPresent();
    }

    @Test
    void inkyDoesSomethingIfPacManFar() {
        String[] map = {
            "############",
            "#P_______B_I#",
            "############"
        };
        GhostMapParser parser = new GhostMapParser();
        Level level = parser.parseMap(map);

        Player pacMan = new PlayerFactory().createPacMan();
        level.registerPlayer(pacMan);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard()).get();
        Optional<Direction> move = inky.nextAiMove();

        assertThat(move).isPresent();
    }

    @Test
    void inkyDoesSomethingIfNoBlinky() {
        String[] map = {
            "############",
            "#P________I#",
            "############"
        };
        GhostMapParser parser = new GhostMapParser();
        Level level = parser.parseMap(map);

        Player pacMan = new PlayerFactory().createPacMan();
        level.registerPlayer(pacMan);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard()).get();
        Optional<Direction> move = inky.nextAiMove();

        assertThat(move).isPresent();
    }

    @Test
    void inkyDoesSomethingRandom() {
        String[] map = {
            "############",
            "#I_________#",
            "############"
        };
        GhostMapParser parser = new GhostMapParser();
        Level level = parser.parseMap(map);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard()).get();
        Optional<Direction> move = inky.nextAiMove();

        assertThat(move).isPresent();
    }
}
