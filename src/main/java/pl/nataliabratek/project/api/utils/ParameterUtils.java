package pl.nataliabratek.project.api.utils;

import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static Set<Integer> convertToSetInteger(@Nullable Set<String> input) {
        if (input == null) {
            return Collections.emptySet();
        }
        Set<Integer> resultSet = input.stream()
                .map(ParameterUtils::convertToInteger)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        return resultSet;

        }
    }

