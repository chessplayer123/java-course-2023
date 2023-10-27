package edu.hw3.Task6;

import java.util.Comparator;
import java.util.PriorityQueue;
import org.jetbrains.annotations.NotNull;

public class Market implements StockMarket {
    private final PriorityQueue<Stock> stocks;

    public Market() {
        stocks = new PriorityQueue<Stock>(Comparator.reverseOrder());
    }

    @Override
    public void add(@NotNull Stock stock) {
        stocks.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        stocks.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return stocks.peek();
    }
}
