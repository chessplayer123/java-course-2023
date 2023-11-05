package edu.hw3;

import edu.hw3.Task6.Market;
import edu.hw3.Task6.Stock;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StockMarketTest {
    @Test
    void maxStockAtEnd() {
        Market market = new Market();

        Stock stock1 = new Stock(20.0);
        Stock stock2 = new Stock(30.0);
        Stock maxStock = new Stock(50.0);

        market.add(stock1);
        market.add(stock2);
        market.add(maxStock);

        assertThat(market.mostValuableStock()).isEqualTo(maxStock);

    }

    @Test
    void maxStockAtBegin() {
        Market market = new Market();

        Stock maxStock = new Stock(91.5);
        Stock stock1 = new Stock(50.0);
        Stock stock2 = new Stock(17.0);

        market.add(maxStock);
        market.add(stock1);
        market.add(stock2);

        assertThat(market.mostValuableStock()).isEqualTo(maxStock);

    }

    @Test
    void maxStockAfterRemove() {
        Market market = new Market();

        Stock maxStock = new Stock(91.5);
        Stock prevMaxStock = new Stock(50.0);

        market.add(prevMaxStock);
        market.add(maxStock);
        market.remove(maxStock);

        assertThat(market.mostValuableStock()).isEqualTo(prevMaxStock);
    }

    @Test
    void addSameStockAgain() {
        Market market = new Market();

        Stock maxStock = new Stock(91.5);
        Stock prevMaxStock = new Stock(50.0);

        market.add(prevMaxStock);
        market.add(maxStock);
        market.add(maxStock);

        market.remove(maxStock);

        assertThat(market.mostValuableStock()).isEqualTo(maxStock);
    }

    @Test
    void removeNotExistentStock() {
        Market market = new Market();

        Stock maxStock = new Stock(91.5);
        Stock stock = new Stock(50.0);

        market.add(maxStock);
        market.remove(stock);

        assertThat(market.mostValuableStock()).isEqualTo(maxStock);
    }

    @Test
    void mostValuableStockWithoutAdding() {
        Market market = new Market();

        assertThat(market.mostValuableStock()).isEqualTo(null);
    }
}
