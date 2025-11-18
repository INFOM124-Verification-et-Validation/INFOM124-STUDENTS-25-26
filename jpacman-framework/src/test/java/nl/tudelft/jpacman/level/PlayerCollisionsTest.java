package nl.tudelft.jpacman.level;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class PlayerCollisionsTest {

    private PlayerCollisions collisions;
    private Player player;
    private Ghost ghost;
    private Pellet pellet;
    private Fruit fruit;

    @BeforeEach
    void setUp() {
        collisions = new DefaultPlayerInteractionMap();
        player = mock(Player.class);
        ghost = mock(Ghost.class);
        pellet = mock(Pellet.class);
        fruit = mock(Fruit.class);
    }

    @Test
    void playerEatsPellet() {
        collisions.collide(player, pellet);
        verify(pellet).leaveSquare();
    }

    @Test
    void playerHitsGhost_normal() {
        when(player.isSuper()).thenReturn(false);
        collisions.collide(player, ghost);
        verify(player).setAlive(false);
    }

    @Test
    void playerHitsGhost_super() {
        when(player.isSuper()).thenReturn(true);
        collisions.collide(player, ghost);
        verify(ghost).setAlive(false);
    }

    @Test
    void playerEatsFruit() {
        collisions.collide(player, fruit);
        verify(fruit).leaveSquare();
    }

    @Test
    void pelletHitsPellet_nothingHappens() {
        collisions.collide(pellet, pellet);
    }

    @Test
    void ghostHitsGhost_nothingHappens() {
        collisions.collide(ghost, ghost);
    }

}
