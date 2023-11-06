package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import edu.hw4.Animal.Sex;
import edu.hw4.Utils.ValidationError;
import edu.hw4.Animal.Type;

public class AnimalsUtilsTest {
    static final Random random = new Random();

    static Sex randSex() {
        return (random.nextBoolean() ? Sex.M: Sex.F);
    }

    static int randInt() {
        return random.nextInt(1000);
    }

    static Type randType() {
        return Type.values()[random.nextInt(Type.values().length)];
    }

    @Test
    void testSortByHeight() {
        List<Animal> animals = Stream.of(10, 2, 1, 15, 8)
            .map(height -> new Animal("animal", randType(), randSex(), randInt(), height, randInt(), false))
            .toList();
        List<Animal> actualSort = Utils.sortByHeight(animals);

        assertThat(actualSort)
            .hasSize(animals.size())
            .isSortedAccordingTo(Comparator.comparing(Animal::height))
            .containsAll(animals);
    }

    @Test
    void testSortByWeightWithLimit() {
        int limit = 2;
        List<Animal> animals = List.of(
            new Animal("Jack", Type.DOG, Sex.M, randInt(), randInt(), 10, false),    // 0
            new Animal("Lora", Type.DOG, Sex.F, randInt(), randInt(), 2, false),     // 1
            new Animal("Sven", Type.DOG, Sex.M, randInt(), randInt(), 1, false),     // 2
            new Animal("Cara", Type.SPIDER, Sex.F, randInt(), randInt(), 15, false), // 3
            new Animal("Mark", Type.CAT, Sex.M, randInt(), randInt(), 8, false)      // 4
        );

        List<Animal> actualSort = Utils.sortByWeightWithLimit(animals, limit);
        List<Animal> expectedSort = List.of(animals.get(3), animals.get(0));

        assertThat(actualSort).isEqualTo(expectedSort);
    }

    @Test
    void testCountByType() {
        List<Animal> animals = Stream.of(Type.DOG, Type.DOG, Type.FISH, Type.CAT, Type.SPIDER)
            .map(type -> new Animal("animal", type, randSex(), randInt(), randInt(), randInt(), random.nextBoolean()))
            .toList();

        Map<Type, Integer> expectedMap = Map.of(
            Type.DOG,    2,
            Type.FISH,   1,
            Type.CAT,    1,
            Type.SPIDER, 1
        );
        Map<Type, Integer> actualMap = Utils.getGroupsCountByType(animals);

        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @Test
    void testLongestName() {
        List<Animal> animals = Stream.of("Jack", "Ken", "Connor", "Bob")
            .map(name -> new Animal(name, randType(), randSex(), randInt(), randInt(), randInt(), false))
            .toList();

        Animal expectedAnimalWithLongestName = animals.get(2); // Connor
        Animal actualAnimalWithLongestName = Utils.getAnimalWithLongestName(animals);

        assertThat(actualAnimalWithLongestName).isEqualTo(expectedAnimalWithLongestName);
    }

    @Test
    void testMostFrequentSex() {
        List<Animal> animals = Stream.of(Sex.F, Sex.F, Sex.M, Sex.M, Sex.M, Sex.M)
            .map(sex -> new Animal("animal", randType(), sex, randInt(), randInt(), randInt(), false))
            .toList();

        Sex mostFreqSex = Sex.M;
        Sex actualMostFreqSex = Utils.getMostFrequentSex(animals);

        assertThat(actualMostFreqSex).isEqualTo(mostFreqSex);
    }

    @Test
    void testMostHeavyAnimalInEachGroup() {
        List<Animal> animals = List.of(
            new Animal("Jack", Type.DOG, Sex.M, randInt(), randInt(), 7, false),
            new Animal("Lora", Type.DOG, Sex.F, randInt(), randInt(), 12, false),    // 1, dog
            new Animal("Sven", Type.DOG, Sex.M, randInt(), randInt(), 8, false),
            new Animal("Cara", Type.SPIDER, Sex.F, randInt(), randInt(), 15, false), // 3, spider
            new Animal("Mark", Type.CAT, Sex.M, randInt(), randInt(), 21, false),    // 4, cat
            new Animal("Olivia", Type.CAT, Sex.F, randInt(), randInt(), 19, false)
        );

        Map<Type, Animal> expectedGroups = Map.of(
            Type.DOG, animals.get(1),
            Type.SPIDER, animals.get(3),
            Type.CAT, animals.get(4)
        );
        Map<Type, Animal> actualGroups = Utils.getHeaviestAnimalInEachGroup(animals);

        assertThat(actualGroups).isEqualTo(expectedGroups);
    }

    @Test
    void testKthOldest() {
        int k = 2;
        List<Animal> animals = Stream.of(10, 11, 15, 17, 34)
            .map(age -> new Animal("animal", randType(), randSex(), age, randInt(), randInt(), false))
            .toList();

        Animal expectedKthOldest = animals.get(3);
        Animal actualKthOldest = Utils.getKthOldestAnimal(animals, k);

        assertThat(actualKthOldest).isEqualTo(expectedKthOldest);
    }

    @Test
    void testMostHeavyAnimalWithHeightLessThanK() {
        int k = 10;
        List<Animal> animals = List.of(
            new Animal("animal1", randType(), randSex(), randInt(), 10, 7, false),
            new Animal("animal2", randType(), randSex(), randInt(), 12, 12, false),
            new Animal("animal3", randType(), randSex(), randInt(), 8, 8, false),
            new Animal("animal4", randType(), randSex(), randInt(), 7, 15, false),
            new Animal("animal5", randType(), randSex(), randInt(), 17, 21, false),
            new Animal("animal6", randType(), randSex(), randInt(), 6, 19, false) // 5
        );

        Animal actualAnimal = Utils.getHeaviestAnimalWithHeightLessThanK(animals, k).orElse(null);
        Animal expectedAnimal = animals.get(5);

        assertThat(actualAnimal).isEqualTo(expectedAnimal);
    }

    @Test
    void testSumOfLegs() {
        List<Animal> animals = List.of(
            new Animal("animal1", Type.DOG, randSex(), randInt(), randInt(), randInt(), false),    // 4
            new Animal("animal2", Type.DOG, randSex(), randInt(), randInt(), randInt(), false),    // 4
            new Animal("animal3", Type.FISH, randSex(), randInt(), randInt(), randInt(), false),   // 0
            new Animal("animal4", Type.BIRD, randSex(), randInt(), randInt(), randInt(), false),   // 2
            new Animal("animal5", Type.SPIDER, randSex(), randInt(), randInt(), randInt(), false), // 8
            new Animal("animal6", Type.CAT, randSex(), randInt(), randInt(), randInt(), false)     // 4
        );

        int expectedPaws = 22;
        int actualPaws = Utils.getSumOfLegs(animals);

        assertThat(actualPaws).isEqualTo(expectedPaws);
    }

    @Test
    void testCountOfAnimalsWithAgeNotEqualsLegs() {
        List<Animal> animals = List.of(
            new Animal("animal1", Type.DOG, randSex(), 4, randInt(), randInt(), false), // 4 == 4
            new Animal("animal2", Type.DOG, randSex(), 2, randInt(), randInt(), false), // 4 != 2    1
            new Animal("animal3", Type.FISH, randSex(), 10, randInt(), randInt(), false), // 0 != 10 2
            new Animal("animal4", Type.BIRD, randSex(), 2, randInt(), randInt(), false),  // 2 == 2
            new Animal("animal5", Type.SPIDER, randSex(), 3, randInt(), randInt(), false) // 8 != 3  4
        );

        List<Animal> expectedList = List.of(
            animals.get(1),
            animals.get(2),
            animals.get(4)
        );

        List<Animal> actualList = Utils.getAnimalsWithAgeNotEqualsLegs(animals);

        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    void testAnimalsBiteAndHeightMoreThan100() {
        List<Animal> animals = List.of(
            new Animal("animal1", randType(), randSex(), randInt(), 201, randInt(), true), // 0
            new Animal("animal2", randType(), randSex(), randInt(), 10, randInt(), true),
            new Animal("animal3", randType(), randSex(), randInt(), 105, randInt(), false),
            new Animal("animal4", randType(), randSex(), randInt(), 100, randInt(), true),
            new Animal("animal5", randType(), randSex(), randInt(), 152, randInt(), true) // 4
        );

        List<Animal> expectedList = List.of(
            animals.get(0),
            animals.get(4)
        );
        List<Animal> actualList = Utils.getAnimalsWhoCanBiteAndHeightMoreThan100(animals);

        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    void testWeightMoreThanHeight() {
        List<Animal> animals = List.of(
            new Animal("animal1", randType(), randSex(), randInt(), 201, 201, true),
            new Animal("animal2", randType(), randSex(), randInt(), 10, 5, true),
            new Animal("animal3", randType(), randSex(), randInt(), 37, 91, false), // 2
            new Animal("animal4", randType(), randSex(), randInt(), 10, 15, true),  // 3
            new Animal("animal5", randType(), randSex(), randInt(), 152, 19, true)
        );

        List<Animal> expectedList = List.of(
            animals.get(2),
            animals.get(3)
        );
        List<Animal> actualList = Utils.getAnimalsWeightMoreThanHeight(animals);

        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    void testMoreThan2WordsInName() {
        List<Animal> animals = List.of(
            new Animal("", randType(), randSex(), randInt(), randInt(), randInt(), true),
            new Animal("One Two", randType(), randSex(), randInt(), randInt(), randInt(), true),
            new Animal("One", randType(), randSex(), randInt(), randInt(), randInt(), false),
            new Animal("One Two Three", randType(), randSex(), randInt(), randInt(), randInt(), true), // 3
            new Animal("More than two words", randType(), randSex(), randInt(), randInt(), randInt(), true) // 4
        );

        List<Animal> expectedList = List.of(
            animals.get(3),
            animals.get(4)
        );
        List<Animal> actualList = Utils.getAnimalsWithMoreThan2WordsInName(animals);

        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    void testDogWithHeight() {
        int height = 10;
        List<Animal> animals = List.of(
            new Animal("name1", Type.DOG, randSex(), randInt(), 100, randInt(), true),
            new Animal("name2", Type.DOG, randSex(), randInt(), 7, randInt(), true),
            new Animal("name3", Type.DOG, randSex(), randInt(), 10, randInt(), true),
            new Animal("name4", Type.FISH, randSex(), randInt(), 10, randInt(), true),
            new Animal("name5", Type.CAT, randSex(), randInt(), 11, randInt(), true),
            new Animal("name6", Type.SPIDER, randSex(), randInt(), 8, randInt(), true)
        );

        Boolean expectedAnswer = true;
        Boolean actualAnswer = Utils.hasDogWithHeight(animals, height);

        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @Test
    void testSumOfWeightWithAgeInRange() {
        int start = 12;
        int end = 25;

        List<Animal> animals = List.of(
            new Animal("name1", Type.DOG, randSex(), 12, randInt(), 15, true), // +
            new Animal("name2", Type.DOG, randSex(), 7, randInt(), 18, true),
            new Animal("name3", Type.DOG, randSex(), 25, randInt(), 7, true),  // +
            new Animal("name4", Type.FISH, randSex(), 15, randInt(), 8, true), // +
            new Animal("name5", Type.CAT, randSex(), 37, randInt(), 6, true),
            new Animal("name6", Type.SPIDER, randSex(), 85, randInt(), 5, true)
        );

        Integer expectedAnswer = 15 + 7 + 8;
        Integer actualAnswer = Utils.getSumOfWeightForAnimalsWithAgeInRange(animals, start, end);

        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @Test
    void testSortByTypeThenSexThenName() {
        List<Animal> animals = List.of(
            new Animal("A", Type.DOG,    Sex.F, randInt(), randInt(), randInt(), true), // 0
            new Animal("B", Type.DOG,    Sex.M, randInt(), randInt(), randInt(), true), // 1
            new Animal("C", Type.DOG,    Sex.M, randInt(), randInt(), randInt(), true), // 2
            new Animal("D", Type.FISH,   Sex.F, randInt(), randInt(), randInt(), true), // 3
            new Animal("E", Type.CAT,    Sex.F, randInt(), randInt(), randInt(), true), // 4
            new Animal("F", Type.SPIDER, Sex.M, randInt(), randInt(), randInt(), true)  // 5
        );

        List<Animal> actualSort = Utils.sortByTypeThenSexThenName(animals);
        List<Animal> expectedSort = List.of(
            animals.get(4), // CAT,    F, "E"
            animals.get(1), // DOG,    M, "B"
            animals.get(2), // DOG,    M, "C"
            animals.get(0), // FISH,   F, "A"
            animals.get(3), // FISH,   F, "D"
            animals.get(5)  // SPIDER, M, "F"
        );

        assertThat(actualSort).isEqualTo(expectedSort);
    }

    @Test
    void testSpiderBiteMoreThanDogs() {
        List<Animal> animals = List.of(
            new Animal("name", Type.DOG,    randSex(), randInt(), randInt(), randInt(), true),  // DOG +
            new Animal("name", Type.SPIDER, randSex(), randInt(), randInt(), randInt(), true),  // SPIDER +
            new Animal("name", Type.DOG,    randSex(), randInt(), randInt(), randInt(), false), // DOG -
            new Animal("name", Type.FISH,   randSex(), randInt(), randInt(), randInt(), true),
            new Animal("name", Type.CAT,    randSex(), randInt(), randInt(), randInt(), false),
            new Animal("name", Type.SPIDER, randSex(), randInt(), randInt(), randInt(), true)   // SPIDER +
        );

        Boolean actualResult = Utils.doesSpidersBiteMoreThanDogs(animals);
        Boolean expectedResult = true;

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void testHeaviestFish() {
        List<Animal> animals1 = List.of(
            new Animal("Nemo", Type.FISH, Sex.M, randInt(), randInt(), 20, false) // heaviest
        );
        List<Animal> animals2 = List.of(
            new Animal("Richard", Type.FISH, Sex.M, randInt(), randInt(), 15, false),
            new Animal("Jack", Type.CAT, Sex.M, randInt(), randInt(), 16, true),
            new Animal("Bob", Type.SPIDER, Sex.M, randInt(), randInt(), 7, true)
        );
        List<Animal> animals3 = List.of(
            new Animal("Jimmy", Type.DOG, Sex.M, randInt(), randInt(), 93, true),
            new Animal("Mathew", Type.FISH, Sex.M, randInt(), randInt(), 19, true)
        );
        List<Animal> animals4 = List.of();

        Animal actualMostHeavyFish = Utils.getHeaviestFish(animals1, animals2, animals3, animals4);
        Animal expectedMostHeavyFish = animals1.get(0);

        assertThat(actualMostHeavyFish).isEqualTo(expectedMostHeavyFish);
    }

    @Test
    void testAnimalsValidation() {
        List<Animal> animals = List.of(
            new Animal("", Type.DOG, Sex.M, 15, 7, 18, true),
            new Animal("Jim", Type.SPIDER, Sex.M, -1, 15, 20, true),
            new Animal("Paul", Type.SPIDER, Sex.M, 5, -21, 21, true),
            new Animal("Anne", Type.CAT, Sex.F, 6, -15, -8, true)
        );

        Map<String, Set<ValidationError>> actualErrors = Utils.getAnimalsWithErrors(animals);

        Map<String, Set<Utils.ValidationError>> expectedErrors = Map.of(
            "", Set.of(new ValidationError("Name", "Name must contains at least one char")),
            "Jim", Set.of(new ValidationError("Age", "Age can't be below 0")),
            "Paul", Set.of(new ValidationError("Height", "Height can't be below 0")),
            "Anne", Set.of(
                new ValidationError("Height", "Height can't be below 0"),
                new ValidationError("Weight", "Weight can't be below 0")
            )
        );

        assertThat(actualErrors).isEqualTo(expectedErrors);
    }

    @Test
    void testAnimalsValidationMoreReadable() {
        List<Animal> animals = List.of(
            new Animal("", Type.DOG, Sex.M, 15, 7, 18, true),
            new Animal("Jim", Type.SPIDER, Sex.M, -1, 15, 20, true),
            new Animal("Paul", Type.SPIDER, Sex.M, 5, -21, 21, true),
            new Animal("Anne", Type.CAT, Sex.F, 6, -15, -8, true)
        );

        Map<String, String> actualErrors = Utils.getAnimalsWithErrorsMoreReadable(animals);

        Map<String, String> expectedErrors = Map.of(
            "", "Name",
            "Jim", "Age",
            "Paul", "Height",
            "Anne", "Height, Weight"
        );

        assertThat(actualErrors).isEqualTo(expectedErrors);
    }
}
