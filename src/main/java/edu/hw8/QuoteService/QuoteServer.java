package edu.hw8.QuoteService;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class QuoteServer implements QuoteDatabase {
    private final Map<String, List<String>> quotes;

    private QuoteServer(@NotNull Map<String, List<String>> quotes) {
        this.quotes = quotes;
    }

    public static QuoteServer fromQuotes(Quote... quotes) {
        Map<String, List<String>> map = new HashMap<>();

        for (Quote quote : quotes) {
            for (String theme : quote.themes) {
                map.putIfAbsent(theme, new ArrayList<>());
                map.get(theme).add(quote.quote);
            }
        }

        return new QuoteServer(map);
    }

    public static QuoteDatabase getDefault() {
        return fromQuotes(
            new Quote(
                "Не переходи на личности там, где их нет",
                List.of("личности")
            ),
            new Quote(
                "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",
                List.of("оскорбления")
            ),
            new Quote(
                "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.",
                List.of("глупый")
            ),
            new Quote(
                "Чем ниже интеллект, тем громче оскорбления",
                List.of("интеллект")
            )
        );
    }

    @Override
    @NotNull
    public String getQuoteByTheme(@NotNull String theme) {
        if (!quotes.containsKey(theme)) {
            return "";
        }

        List<String> phrases = quotes.get(theme);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return phrases.get(random.nextInt(phrases.size()));
    }

    public record Quote(String quote, List<String> themes) {}
}
