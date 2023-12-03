package edu.project3.Formatters;

public enum FormatterType {
    AsciiDoc,
    Markdown;

    public static ReportFormatter fromString(String name) throws IllegalArgumentException {
        return switch (name) {
            case "adoc" -> new AsciiDocFormatter();
            case "markdown" -> new MarkdownFormatter();
            default -> throw new IllegalArgumentException("Unknown formatter type");
        };
    }
}
