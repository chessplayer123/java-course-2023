package edu.hw1;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class Task3Test {
    @Test
    void testNestable() {
        Task3 task3 = new Task3();

        int[] arr1 = new int[] {1, 2, 3, 4};
        int[] arr2 = new int[] {0, 6};

        boolean isNestable = task3.isNestable(arr1, arr2);

        assertTrue(isNestable);
    }

    @Test
    void testNotNestableLowerBound() {
        Task3 task3 = new Task3();

        int[] arr1 = new int[] {1, 2,};
        int[] arr2 = new int[] {2, 3};

        boolean isNestable = task3.isNestable(arr1, arr2);

        assertFalse(isNestable);
    }

    @Test
    void testNotNestableUpperBound() {
        Task3 task3 = new Task3();

        int[] arr1 = new int[] {3, 4};
        int[] arr2 = new int[] {2, 3};

        boolean isNestable = task3.isNestable(arr1, arr2);

        assertFalse(isNestable);
    }

    @Test
    void testNotNestableBothBounds() {
        Task3 task3 = new Task3();

        int[] arr1 = new int[] {1, 2, 3, 4};
        int[] arr2 = new int[] {2, 3};

        boolean isNestable = task3.isNestable(arr1, arr2);

        assertFalse(isNestable);
    }

    @Test
    void testEmptyInnerArray() {
        Task3 task3 = new Task3();

        int[] arr1 = new int[] {};
        int[] arr2 = new int[] {0, 6};

        boolean isNestable = task3.isNestable(arr1, arr2);

        assertFalse(isNestable);
    }

    @Test
    void testEmptyOuterArray() {
        Task3 task3 = new Task3();

        int[] arr1 = new int[] {1, 2, 3, 4};
        int[] arr2 = new int[] {};

        boolean isNestable = task3.isNestable(arr1, arr2);

        assertFalse(isNestable);
    }

    @Test
    void testEmptyArrays() {
        Task3 task3 = new Task3();

        int[] arr1 = new int[] {};
        int[] arr2 = new int[] {};

        boolean isNestable = task3.isNestable(arr1, arr2);

        assertFalse(isNestable);
    }
}
