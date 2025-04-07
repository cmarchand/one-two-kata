Feature: One Two Kata

  Part 1
  We need a method to convert strings to other strings in the following way.
  To understand the conversion, just read the input string. The first example
  string shows one two. There is indeed one number, and this number is two.

  - 2 -> one two
  - 1 2 -> one one one two
  Easy.

  Now, we can also count numbers, as in the following examples.

  - 2 2 -> two two
  - 3 9 9 9 8 8 -> one three three nine two eight
  - 1 1 1 1 1 1 1 -> seven one (Brasilians will love this example)
  - 2 4 4 4 6 6 6 6 6 -> one two three four five six
  Got it?

  Now, to keep things simple, we do not count past 9. Thus the conversion of
  the following string.

  - 5 5 5 5 5 5 5 5 5 5 5 5 -> nine five three five


  Part 2
  And of course we would like a second method to do the opposite conversion.

  Scenario Outline: Convert from digits to words
    Given digits are <digits>
    When converting to words
    Then words translation is <words>
    Examples:
      | digits              | words                            |
      | "1"                 | "one one"                        |
      | "2"                 | "one two"                        |
      | "3"                 | "one three"                      |
      | "4"                 | "one four"                       |
      | "5"                 | "one five"                       |
      | "6"                 | "one six"                        |
      | "7"                 | "one seven"                      |
      | "8"                 | "one eight"                      |
      | "9"                 | "one nine"                       |
      | "0"                 | "one zero"                       |
      | "2 2"               | "two two"                        |
      | "2 2 2"             | "three two"                      |
      | "3 9 9 9 8 8"       | "one three three nine two eight" |
      | "1 1 1 1 1 1 1"     | "seven one"                      |
      | "2 4 4 4 6 6 6 6 6" | "one two three four five six"    |
      | "5 5 5 5 5 5 5 5 5 5 5 5" | "nine five three five"    |


  Scenario Outline: Convert from words to digits
    Given words are <words>
    When converting to digits
    Then digits translation is <digits>
    Examples:
      | digits              | words                            |
      | "1"                 | "one one"                        |
      | "2"                 | "one two"                        |
      | "3"                 | "one three"                      |
      | "4"                 | "one four"                       |
      | "5"                 | "one five"                       |
      | "6"                 | "one six"                        |
      | "7"                 | "one seven"                      |
      | "8"                 | "one eight"                      |
      | "9"                 | "one nine"                       |
      | "0"                 | "one zero"                       |
      | "2 2"               | "two two"                        |
      | "2 2 2"             | "three two"                      |
      | "3 9 9 9 8 8"       | "one three three nine two eight" |
      | "1 1 1 1 1 1 1"     | "seven one"                      |
      | "2 4 4 4 6 6 6 6 6" | "one two three four five six"    |
      | "5 5 5 5 5 5 5 5 5 5 5 5" | "nine five three five"    |

