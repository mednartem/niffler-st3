package guru.qa.niffler.util;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {

    public static String generateName() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
