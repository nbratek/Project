package pl.nataliabratek.project.api.utils;

import org.springframework.lang.Nullable;

import java.util.Optional;

public class ParameterUtils {


    public static Optional<Integer> convertToInteger(@Nullable String input) {
        if (input == null) {
            return Optional.empty();
        }
        try {
            return Optional.of(Integer.parseInt(input));

        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }
}
