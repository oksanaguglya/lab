package by.bsu.guglya.library.logic;

import java.util.List;

public class PageItems {

    private List<TableItem> items;
    private int count;

    public PageItems(List<TableItem> items, int count) {
        this.items = items;
        this.count = count;
    }

    public List<TableItem> getItems() {
        return items;
    }

    public int getCount() {
        return count;
    }
}
