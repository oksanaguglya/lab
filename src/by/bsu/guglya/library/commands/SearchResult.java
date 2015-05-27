package by.bsu.guglya.library.commands;

import by.bsu.guglya.library.beans.CatalogItem;

import java.util.List;

public class SearchResult {

    private List<CatalogItem> items;
    private int count;

    public SearchResult(List<CatalogItem> items, int count) {
        this.items = items;
        this.count = count;
    }

    public List<CatalogItem> getItems() {
        return items;
    }

    public int getCount() {
        return count;
    }
}
