package edu.project3.Formatters;

import edu.project3.Table;

public class AsciiDocFormatter implements ReportFormatter {
    @SuppressWarnings("MultipleStringLiterals")
    @Override
    public String formatTable(Table table) {
        StringBuilder builder = new StringBuilder();

        builder.append(".").append(table.getTitle()).append("\n");
        builder.append("[options=\"header\"]\n");
        builder.append("|===\n");

        for (int i = 0, rows = table.getRows(); i < rows; ++i) {
            builder.append("|").append(String.join("|", table.get(i))).append("\n");
        }

        builder.append("|===\n");

        return builder.toString();
    }
}
