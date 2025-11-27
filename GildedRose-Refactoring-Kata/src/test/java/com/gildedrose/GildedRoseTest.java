package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[] {
            new Sulfuras(10),
            new BackstagePasses(12, 40),
            new BackstagePasses(8, 30),
            new BackstagePasses(3, 40),
            new BackstagePasses(3, 49),
            new BackstagePasses(-1, 49),
            new AgedBrie(3, 25),
            new AgedBrie(3, 50),
            new Conjured(3, 10),
            new Conjured(3, 1),

        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();


        assertEquals(80, app.items[0].quality);

        assertEquals(39, app.items[1].quality);
        assertEquals(32, app.items[2].quality);
        assertEquals(43, app.items[3].quality);
        assertEquals(50, app.items[4].quality);
        assertEquals(0, app.items[5].quality);

        assertEquals(26, app.items[6].quality);
        assertEquals(50, app.items[7].quality);

        assertEquals(8, app.items[8].quality);
        assertEquals(0, app.items[9].quality);
    }

}
