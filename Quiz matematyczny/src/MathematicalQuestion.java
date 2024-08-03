import java.util.Random;

public class MathematicalQuestion {
  private int operand1, operand2, result;
  private char operator;
  MathematicalQuestion (Random random) {
    operator = ("+-×÷").charAt (random.nextInt (4));
    if (operator == '+') {
      operand1 = random.nextInt (100);
      operand2 = random.nextInt (100 - operand1);
      result   = operand1 + operand2;
    } else if (operator == '-') {
      operand1 = random.nextInt (100);
      operand2 = random.nextInt (operand1);
      result   = operand1 - operand2;
    } else if (operator == '×') {
      operand1 = 2 + random.nextInt (8);
      operand2 = 2 + random.nextInt (8);
      result   = operand1 * operand2;
    } else if (operator == '÷') {
      operand2 = 2 + random.nextInt (8);
      operand1 = (1 + random.nextInt (9)) * operand2;
      result   = operand1 / operand2;
    }
  }
  public String toString () {
    return "" + operand1 + operator + operand2;
  }
  public boolean correct (int attempt) {
    return attempt == result;
  }
}