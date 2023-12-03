package edu.project3.Formatters;

import edu.project3.Table;

public class MarkdownFormatter implements ReportFormatter {
    @SuppressWarnings("MultipleStringLiterals")
    @Override
    public String formatTable(Table table) {
        StringBuilder builder = new StringBuilder();

        builder.append("### ").append(table.getTitle()).append("\n");
        builder.append("|").append(String.join("|", table.get(0))).append("|\n");
        builder.append("|").append(":-:|".repeat(table.getCols())).append("\n");

        for (int i = 1, rows = table.getRows(); i < rows; ++i) {
            builder.append("|").append(String.join("|", table.get(i))).append("|\n");
        }

        return builder.toString();
    }
}
