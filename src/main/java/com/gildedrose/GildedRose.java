package com.gildedrose;

import java.util.Set;

class GildedRose {
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE = "Backstage";
    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
    public static final String BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT1 = "Backstage passes to a TAFKAL80ETC concert";

    public static final Set<String> BACKSTAGE_PASSES = Set.of("Backstage passes to a TAFKAL80ETC concert");
    public static final int MAX_QUALITY = 50;
    public static final String CONJURED_MANA_CAKE = "Conjured Mana Cake";
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (isLegendaryItem(item)) {
                continue;
            }

            item.sellIn = item.sellIn - 1;
            String itemName = item.name;
            switch (itemName) {
                case BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT1, BACKSTAGE -> updatePasses(item);
                case AGED_BRIE -> updateCheese(item);
                case CONJURED_MANA_CAKE -> updateConjured(item);
                default -> updateCommonItems(item);
            }
        }
    }

    private void updateConjured(Item item) {
        decreaseConjuredQuality(item);

        if (item.sellIn < 0) {
            decreaseConjuredQuality(item);
        }
    }

    private void decreaseConjuredQuality(Item item) {
        decreaseQuality(item);
        decreaseQuality(item);
    }

    private void updateCommonItems(Item item) {
        decreaseQuality(item);

        if (item.sellIn < 0) {
            decreaseQuality(item);
        }
    }

    private void updateCheese(Item item) {
        increaseQuality(item);

        if (item.sellIn < 0) {
            increaseQuality(item);
        }
    }

    private void updatePasses(Item item) {
        if (item.sellIn < 0) {
            item.quality = 0;
        } else {
            increaseQuality(item);

            if (item.sellIn < 10) {
                increaseQuality(item);
            }

            if (item.sellIn < 5) {
                increaseQuality(item);
            }
        }
    }

    private void decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality = item.quality - 1;
        }
    }

    private void increaseQuality(Item item) {
        if (item.quality < MAX_QUALITY) {
            item.quality = item.quality + 1;
        }
    }

    private boolean isLegendaryItem(Item item) {
        return item.name.equals(SULFURAS_HAND_OF_RAGNAROS);
    }
}
