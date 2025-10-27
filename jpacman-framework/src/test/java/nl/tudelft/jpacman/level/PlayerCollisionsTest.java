package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class PlayerCollisionsTest {


    private PlayerCollisions playerCollisions;
    private Player pacman;
    private Ghost ghost;
    private Pellet pellet;


    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        PlayerFactory playerFactory = new PlayerFactory(sprites);
        GhostFactory ghostFactory = new GhostFactory(sprites);

        playerCollisions = new PlayerCollisions();
        pacman = playerFactory.createPacMan();
        ghost = ghostFactory.createClyde();

        pellet = new Pellet(10, new PacManSprites().getPelletSprite());

    }


    @Test
    void playerVSGhost() {

        assertThat(pacman.isAlive()).isTrue();
        playerCollisions.collide(pacman, ghost);
        assertThat(pacman.isAlive()).isFalse();
    }


    @Test
    void playerVSPlayer() {

        playerCollisions.collide(pacman, pacman);
        assertThat(pacman.isAlive()).isTrue();
    }


    @Test
    void playerVSPellet() {

        playerCollisions.collide(pacman, pellet);
        assertThat(pacman.isAlive()).isTrue();
        assertThat(pacman.getScore()).isEqualTo(10);
    }
}
