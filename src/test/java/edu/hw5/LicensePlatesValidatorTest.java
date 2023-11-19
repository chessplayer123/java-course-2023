package edu.hw5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

public class LicensePlatesValidatorTest {
    @ParameterizedTest
    @ValueSource(strings = {
        "А123ВЕ777",
        "А123ВЕ77",
        "О777ОО177",
        "У468КМ177",
        "Т900РС01",
        "Х930НТ03",
        "О000ОО123",
    })
    void correctLicensePlates(String plate) {
        LicensePlatesValidator validator = new LicensePlatesValidator();

        Boolean actualResult = validator.validatePlate(plate);

        assertThat(actualResult).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "random text",
        "У333АЕ7",
        "АА1234А11",
        "123АВЕ777",
        "А123ВГ77",
        "123ВВ777",
        "А123ВЕ7777",
        "Б001ВГ123",
        "А001ЪД123",
        "В000ФО123",
        "И000МЯ123",
        "E001OB77",  // latin latin latin
        "B000CE123", // latin latin latin
        "Х000АC123", // cyr   cyr   latin
    })
    void incorrectLicensePlates(String plate) {
        LicensePlatesValidator validator = new LicensePlatesValidator();

        Boolean actualResult = validator.validatePlate(plate);

        assertThat(actualResult).isFalse();
    }
}
