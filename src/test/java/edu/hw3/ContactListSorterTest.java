package edu.hw3;

import edu.hw3.ContactListSorter.Contact;
import edu.hw3.ContactListSorter.SortOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ContactListSorterTest {
    static Arguments[] contacts() {
        return new Arguments[] {
            Arguments.of(
                new String[] { "John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes" },
                new Contact[] {
                    new Contact("Thomas", "Aquinas"),
                    new Contact("Rene", "Descartes"),
                    new Contact("David", "Hume"),
                    new Contact("John", "Locke")
                },
                SortOrder.ASC
            ),
            Arguments.of(
                new String[] { "Paul Erdos", "Leonhard Euler", "Carl Gauss" },
                new Contact[] {
                    new Contact("Carl", "Gauss"),
                    new Contact("Leonhard", "Euler"),
                    new Contact("Paul", "Erdos"),
                },
                SortOrder.DESC
            ),
            Arguments.of(
                new String[] { "Paul Erdos", "Leonhard", "Carl Gauss" },
                new Contact[] {
                    new Contact("Paul", "Erdos"),
                    new Contact("Carl", "Gauss"),
                    new Contact("Leonhard", null),
                },
                SortOrder.ASC
            ),
            Arguments.of(
                new String[] { "A A", "B", "C", "B A", "C A" },
                new Contact[] {
                    new Contact("C", null),
                    new Contact("B", null),
                    new Contact("C", "A"),
                    new Contact("B", "A"),
                    new Contact("A", "A"),
                },
                SortOrder.DESC
            ),
            Arguments.of(
                new String[] {},
                new Contact[] {},
                SortOrder.ASC
            ),
        };
    }

    @ParameterizedTest
    @MethodSource("contacts")
    void returnCorrectContactsForNamesWithValidFormat(String[] names, Contact[] expectedContacts, SortOrder order) {
        ContactListSorter sorter = new ContactListSorter();

        Contact[] actualContacts = sorter.parseContacts(names, order);

        assertThat(actualContacts).isEqualTo(expectedContacts);
    }

    @Test
    void invalidNameFormatThrowsException() {
        ContactListSorter sorter = new ContactListSorter();

        String[] names = new String[] { "First Second", "   " };

        assertThatThrownBy(
            () -> sorter.parseContacts(names, SortOrder.ASC)
        ).isInstanceOf(IllegalArgumentException.class)
         .hasMessage("invalid name format");
    }
}
