package com.oxiane.katas.one.two;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OneTwoIllegalSyntaxTests {
  @Test
  @DisplayName("ç in convert to digits should throw exception")
  public void test_1() {
    // Given
    String input = "ç";
    Assertions
        .assertThatThrownBy(() -> OneTwo.convertWords(input))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid word ç");
  }
  @Test
  @DisplayName("ç in convert to words should throw exception")
  public void test_2() {
    // Given
    String input = "ç";
    Assertions
        .assertThatThrownBy(() -> OneTwo.convertDigits(input))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid digit ç");
  }

}
