package com.gildedrose;

public class Item {

    public String name;

    public int sellIn;

    public int quality;

    public String category;

    public Item(String name, int sellIn, int quality, String category) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.category = category
    }

   @Override
   public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality + "," + this.category;
    }
}
