package edu.hw8.QuoteService;

import org.jetbrains.annotations.NotNull;

public interface QuoteDatabase {
    @NotNull String getQuoteByTheme(@NotNull String theme);
}
