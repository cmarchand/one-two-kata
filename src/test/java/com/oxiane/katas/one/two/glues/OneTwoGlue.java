package com.oxiane.katas.one.two.glues;

import com.oxiane.katas.one.two.OneTwo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

public class OneTwoGlue {
  private String actualWords;
  private String inputDigits;
  private String actualDigits;
  private String inputWords;

  @Given("digits are {string}")
  public void input_digits(String digits) {
    inputDigits = digits;
  }
  @When("converting to words")
  public void converting_to_words() {
    actualWords = new OneTwo().convertDigits(inputDigits);
  }
  @Then("words translation is {string}")
  public void words_translation_is_(String expected) {
    Assertions.assertThat(actualWords).isEqualTo(expected);
  }

  @Given("words are {string}")
  public void input_words(String words) {
    inputWords = words;
  }
  @When("converting to digits")
  public void converting_to_digits() {
    actualDigits = new OneTwo().convertWords(inputWords);
  }
  @Then("digits translation is {string}")
  public void digits_translation_is_(String expected) {
    Assertions.assertThat(actualDigits).isEqualTo(expected);
  }
}