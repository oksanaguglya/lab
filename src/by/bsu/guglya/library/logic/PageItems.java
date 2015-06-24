package by.bsu.guglya.library.logic;

import java.util.List;

public class PageItems {

    private List<?> items;
    private int count;

    public PageItems(List<?> items, int count) {
        this.items = items;
        this.count = count;
    }

    public List<?> getItems() {
        return items;
    }

    public int getCount() {
        return count;
    }
}
