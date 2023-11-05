package edu.hw3;

import java.util.Arrays;
import java.util.Comparator;
import org.jetbrains.annotations.NotNull;

public class ContactListSorter {
    public enum SortOrder {
        DESC,
        ASC,
    }

    public Contact[] parseContacts(String[] names, SortOrder order) throws IllegalArgumentException {
        int len = names.length;
        Contact[] contacts = new Contact[len];

        for (int i = 0; i < len; ++i) {
            String[] initials = names[i].split(" ");

            if (initials.length == 1) { // only first name
                contacts[i] = new Contact(initials[0], null);
            } else if (initials.length == 2) { // first name and second name
                contacts[i] = new Contact(initials[0], initials[1]);
            } else {
                throw new IllegalArgumentException("invalid name format");
            }
        }

        Comparator<Contact> comparator = Comparator.comparing(contact -> {
            return (contact.lastName() == null ?  contact.firstName() : contact.lastName());
        });

        if (order == SortOrder.ASC) {
            Arrays.sort(contacts, comparator.thenComparing(Contact::firstName));
        } else {
            Arrays.sort(contacts, comparator.thenComparing(Contact::firstName).reversed());
        }

        return contacts;
    }

    public record Contact(@NotNull String firstName, String lastName) {}
}
