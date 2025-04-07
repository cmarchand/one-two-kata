package com.oxiane.katas.one.two;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;

public class OneTwo {

  public static String convertDigits(String inputDigits) {
    return DigitsToWordsConverter.convert(inputDigits);
  }

  public static String convertWords(String inputWords) {
    return WordsToDigitsConverter.convert(inputWords);
  }

  private enum NumberStrategy {
    ZERO("0", "zero"),
    ONE("1", "one"),
    TWO("2", "two"),
    THREE("3", "three"),
    FOR("4", "four"),
    FIVE("5", "five"),
    SIX("6", "six"),
    SEVEN("7", "seven"),
    EIGHT("8", "eight"),
    NINE("9", "nine"),
    ;

    public final String digit;
    public final String word;
    public final int intValue;

    NumberStrategy(String digit, String word) {
      this.digit = digit;
      this.word = word;
      this.intValue = Integer.parseInt(digit);
    }

    private static NumberStrategy ofDigit(String digit) {
      return Arrays.stream(values())
                   .filter(entry -> entry.digit.equals(digit))
                   .findFirst()
                   .orElseThrow(() -> new IllegalArgumentException("Invalid digit " + digit));
    }

    public static NumberStrategy of(int digit) {
      return values()[digit];
    }

    public static NumberStrategy ofWord(String word) {
      return Arrays.stream(values())
                   .filter(numberStrategy -> numberStrategy.word.equals(word))
                   .findFirst()
                   .orElseThrow(() -> new IllegalArgumentException("Invalid word " + word));
    }
  }

  private static class WordsToDigitsConverter {
    private static String convert(String inputWords) {
      return Pattern
          .compile(" ")
          .splitAsStream(inputWords)
          .gather(Gatherers.windowFixed(2))
          .map(WordsToDigitsConverter::convertPairToDigits)
          .collect(Collectors.joining(" "));
    }

    private static String convertPairToDigits(List<String> pair) {
      int quantity = NumberStrategy.ofWord(pair.getFirst()).intValue;
      String figure = NumberStrategy.ofWord(pair.getLast()).digit;
      return IntStream
          .range(0, quantity)
          .mapToObj(_ -> figure)
          .collect(Collectors.joining(" "));
    }
  }

  private static class DigitsToWordsConverter {
    private static String convert(String inputDigits) {
      return Pattern
          .compile(" ")
          .splitAsStream(inputDigits)
          .<GroupingAccumulator>gather(Gatherer.ofSequential(
              GroupingAccumulator::new,
              (state, digit, downStream) -> {
                if (state.isEmpty()) {
                  state.startGroupingWith(digit);
                } else if (state.matches(digit) && state.occurrences() < 9) {
                  state.inc();
                } else {
                  boolean ret = downStream.push(state);
                  state.startGroupingWith(digit);
                  return ret;
                }
                return true;
              },
              (state, downstream) -> downstream.push(state)
          ))
          .map(DigitsToWordsConverter::convertGroupToResult)
          .collect(Collectors.joining(" "));
    }

    private static String convertGroupToResult(GroupingAccumulator groupingAccumulator) {
      return
          NumberStrategy.of(groupingAccumulator.occurrences()).word +
              " " +
              NumberStrategy.ofDigit(groupingAccumulator.value()).word;
    }
  }
}

final class GroupingAccumulator {
  private String value;
  private int occurrences;

  GroupingAccumulator() {
    value = null;
    occurrences = 0;
  }

  boolean isEmpty() {
    return value == null;
  }

  void startGroupingWith(String value) {
    this.value = value;
    occurrences = 1;
  }

  boolean matches(String token) {
    return value.equals(token);
  }

  public int occurrences() {
    return occurrences;
  }

  public String value() {
    return value;
  }

  public void inc() {
    occurrences++;
  }
}