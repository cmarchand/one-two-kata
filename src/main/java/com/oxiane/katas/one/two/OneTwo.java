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

  public String convertDigits(String inputDigits) {
    return DigitsToWordsConverter.convert(inputDigits);
  }

  public String convertWords(String inputWords) {
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
      return Pattern.compile(" ")
          .splitAsStream(inputWords)
          .gather(Gatherers.windowFixed(2))
          .map(WordsToDigitsConverter::convertPairToDigits)
      .collect(Collectors.joining(" "));
    }

    private static String convertPairToDigits(List<String> pair) {
      int quantity = NumberStrategy.ofWord(pair.getFirst()).intValue;
      String figure = NumberStrategy.ofWord(pair.getLast()).digit;
      StringJoiner stringJoiner = new StringJoiner(" ");
      IntStream.range(0, quantity)
          .forEach(_ -> stringJoiner.add(figure));
      return stringJoiner.toString();
    }
  }
  private static class DigitsToWordsConverter {
    private static String convert(String inputDigits) {
      return Pattern.compile(" ")
          .splitAsStream(inputDigits)
          .<GroupingAccumulator>gather(Gatherer.ofSequential(
              GroupingAccumulator::new,
              (state, digit, downStream) -> {
                if(state.isEmpty())
                  state.startGroupingWith(digit);
                else if (state.matches(digit))
                  state.occurrences++;
                else {
                  downStream.push(state);
                  state.startGroupingWith(digit);
                }
                return true;
              },
            (groupingAccumulator, downstream) ->
                downstream.push(groupingAccumulator)
          ))
          .map(groupingAccumulator -> convertGroupToResult(groupingAccumulator))
      .collect(Collectors.joining(" "));
    }
    private static String convertGroupToResult(GroupingAccumulator groupingAccumulator) {
      NumberStrategy currentNumber = NumberStrategy.ofDigit(groupingAccumulator.value);
      StringJoiner result = new StringJoiner(" ");
      while(groupingAccumulator.occurrences >= 9) {
        result.add(NumberStrategy.NINE.word);
        result.add(currentNumber.word);
        groupingAccumulator.occurrences -=9;
      }
      result.add(NumberStrategy.of(groupingAccumulator.occurrences).word);
      result.add(currentNumber.word);
      return result.toString();
    }
  }
  private static class GroupingAccumulator {
    String value;
    int occurrences;

    private GroupingAccumulator() {
      value = null;
      occurrences = 0;
    }
    private GroupingAccumulator(String value) {
      this.value = value;
      occurrences = 0;
    }
    private boolean isEmpty() {
      return value==null;
    }
    private void startGroupingWith(String value) {
      this.value = value;
      occurrences = 1;
    }
    private boolean matches(String token) {
      return value.equals(token);
    }
  }
}