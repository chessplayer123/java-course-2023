package edu.hw4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("MagicNumber")
public final class Utils {
    private Utils() {
    }

    public static List<Animal> sortByHeight(List<Animal> animals) {
        return animals
            .stream()
            .sorted(Comparator.comparing(Animal::height))
            .toList();
    }

    public static List<Animal> sortByWeightWithLimit(List<Animal> animals, int k) {
        return animals
            .stream()
            .sorted(Comparator.comparing(Animal::weight).reversed())
            .limit(k)
            .toList();
    }

    public static Map<Animal.Type, Integer> getGroupsCountByType(List<Animal> animals) {
        return animals
            .stream()
            .collect(
                Collectors.groupingBy(
                    Animal::type,
                    Collectors.collectingAndThen(
                        Collectors.counting(), Long::intValue
                    )
                )
            );
    }

    public static Animal getAnimalWithLongestName(List<Animal> animals) {
        return animals
            .stream()
            .max(Comparator.comparingInt(animal -> animal.name().length()))
            .orElse(null);
    }

    public static Animal.Sex getMostFrequentSex(List<Animal> animals) {
        return animals
            .stream()
            .collect(Collectors.groupingBy(
                Animal::sex,
                Collectors.counting()
            ))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .orElseThrow()
            .getKey();
    }

    public static Map<Animal.Type, Animal> getHeaviestAnimalInEachGroup(List<Animal> animals) {
        return animals
            .stream()
            .collect(
            Collectors.toMap(
                Animal::type,
                Function.identity(),
                BinaryOperator.maxBy(Comparator.comparing(Animal::weight))
            )
        );
    }

    public static Animal getKthOldestAnimal(List<Animal> animals, int k) throws IllegalArgumentException {
        return animals
            .stream()
            .sorted(Comparator.comparing(Animal::age).reversed())
            .toList()
            .get(k - 1);
    }

    public static Optional<Animal> getHeaviestAnimalWithHeightLessThanK(List<Animal> animals, int k) {
        return animals
            .stream()
            .filter(animal -> animal.height() < k)
            .max(Comparator.comparing(Animal::weight));
    }

    public static Integer getSumOfLegs(List<Animal> animals) {
        return animals
            .stream()
            .mapToInt(Animal::paws)
            .sum();
    }

    public static List<Animal> getAnimalsWithAgeNotEqualsLegs(List<Animal> animals) {
        return animals
            .stream()
            .filter(animal -> animal.paws() != animal.age())
            .toList();
    }

    public static List<Animal> getAnimalsWhoCanBiteAndHeightMoreThan100(List<Animal> animals) {
        return animals
            .stream()
            .filter(animal -> animal.bites() && animal.height() > 100)
            .toList();
    }

    public static List<Animal> getAnimalsWeightMoreThanHeight(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.weight() > animal.height())
            .toList();
    }

    public static List<Animal> getAnimalsWithMoreThan2WordsInName(List<Animal> animals) {
        return animals
            .stream()
            .filter(animal -> animal.name().split(" ").length > 2)
            .toList();
    }

    public static Boolean hasDogWithHeight(List<Animal> animals, int height) {
        return animals
            .stream()
            .anyMatch(animal -> animal.type() == Animal.Type.DOG && animal.height() == height);

    }

    public static Integer getSumOfWeightForAnimalsWithAgeInRange(List<Animal> animals, int begin, int end) {
        return animals
            .stream()
            .filter(animal -> begin <= animal.age() && animal.age() <= end)
            .mapToInt(Animal::weight)
            .sum();
    }

    public static List<Animal> sortByTypeThenSexThenName(List<Animal> animals) {
        return animals
            .stream()
            .sorted(Comparator
                .comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name)
            ).toList();
    }

    public static Boolean doesSpidersBiteMoreThanDogs(List<Animal> animals) {
        record Ratio(int bitesCount, int count) {}

        Ratio spiders = animals
            .stream()
            .filter(animal -> animal.type() == Animal.Type.SPIDER)
            .collect(
                Collectors.teeing(
                    Collectors.summingInt(animal -> (animal.bites() ? 1 : 0)),
                    Collectors.counting(),
                    (sum, num) -> new Ratio(sum, num.intValue())
                )
            );

        Ratio dogs = animals
            .stream()
            .filter(animal -> animal.type() == Animal.Type.DOG)
            .collect(
                Collectors.teeing(
                    Collectors.summingInt(animal -> (animal.bites() ? 1 : 0)),
                    Collectors.counting(),
                    (sum, num) -> new Ratio(sum, num.intValue())
                )
            );
        if (spiders.count == 0 || dogs.count == 0) {
            return false;
        }
        // spiders.bitesCount / spiders.count > dogs.bitesCount / dogs.count | * (dogs.count * spiders.count)
        return spiders.bitesCount * dogs.count > dogs.bitesCount * spiders.count;
    }

    @SafeVarargs
    public static Animal getHeaviestFish(List<Animal>... animals) {
        return Arrays
            .stream(animals)
            .flatMap(List::stream)
            .filter(animal -> animal.type() == Animal.Type.FISH)
            .max(Comparator.comparing(Animal::weight))
            .orElse(null);
    }

    public static Map<String, Set<ValidationError>> getAnimalsWithErrors(List<Animal> animals) {
        return animals
            .stream()
            .filter(animal -> !validateAnimalInfo(animal).isEmpty())
            .collect(
                Collectors.toMap(
                    Animal::name,
                    Utils::validateAnimalInfo
                )
            );
    }

    public static Map<String, String> getAnimalsWithErrorsMoreReadable(List<Animal> animals) {
        return animals
            .stream()
            .filter(animal -> !validateAnimalInfo(animal).isEmpty())
            .collect(
                Collectors.toMap(
                    Animal::name,
                    animal -> validateAnimalInfo(animal)
                        .stream()
                        .map(ValidationError::field)
                        .collect(Collectors.joining(", "))
                )
            );
    }

    private static Set<ValidationError> validateAnimalInfo(Animal animal) {
        HashSet<ValidationError> errorsSet = new HashSet<ValidationError>();
        if (animal.name().isEmpty()) {
            errorsSet.add(new ValidationError("Name", "Name must contains at least one char"));
        }
        if (animal.age() < 0) {
            errorsSet.add(new ValidationError("Age", "Age can't be below 0"));
        }
        if (animal.weight() < 0) {
            errorsSet.add(new ValidationError("Weight", "Weight can't be below 0"));
        }
        if (animal.height() < 0) {
            errorsSet.add(new ValidationError("Height", "Height can't be below 0"));
        }
        return errorsSet;
    }

    public record ValidationError(String field, String message) {}
}
