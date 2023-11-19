package edu.project3;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ArgParserTest {
    private static final ArgParser PARSER = new ArgParser()
        .requiredArgument("path")
        .optionalArgument("from",   "0000-01-01")
        .optionalArgument("to",     "3000-01-01")
        .optionalArgument("format", "markdown");

    @Test
    void test() {
        //Main.main(new String[] {"--path", "src/**/2015*", "--format", "markdown"});
        /*
        Main.main(new String[] {
            "--path", "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs",
            "--format", "adoc"
        });
         */
    }

    @Test
    void allArgumentsParsedCorrectly() {
        String[] args = new String[] {
            "--path",   "glob-or-url",
            "--format", "adoc-or-markdown",
            "--from",   "date-in-iso-format1",
            "--to",     "date-in-iso-format2",
        };

        Map<String, String> expectedMap = Map.of(
            "path",   "glob-or-url",
            "format", "adoc-or-markdown",
            "from",   "date-in-iso-format1",
            "to",     "date-in-iso-format2"
        );
        Map<String, String> actualMap = PARSER.parse(args);

        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @Test
    void defaultValuesParsedCorrectly() {
        String[] args = new String[] {"--path", "glob-or-url"};

        Map<String, String> expectedMap = Map.of(
            "path",   "glob-or-url",
            "from",   "0000-01-01",
            "to",     "3000-01-01",
            "format", "markdown"
        );
        Map<String, String> actualMap = PARSER.parse(args);

        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @Test
    void parserThrowsExceptionWhenValueForArgumentWasNotProvided() {
        String[] args = new String[] {"--path", "path", "--from"};

        assertThatThrownBy(() -> PARSER.parse(args))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("No value was provided for argument '--from'");
    }

    @Test
    void parserThrowsExceptionWhenAllRequiredArgumentsWasNotProvided() {
        String[] args = new String[] {"--format", "markdown"};

        assertThatThrownBy(() -> PARSER.parse(args))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("No value was provided for required argument 'path'");
    }

    @Test
    void parserThrowsExceptionWhenUnexpectedArgumentWasProvided() {
        String[] args = new String[] {"--path", "path", "--unexpected", "value"};

        assertThatThrownBy(() -> PARSER.parse(args))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Unexpected argument '--unexpected' was provided");
    }
}
