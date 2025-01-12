package pl.tim3erland.interview;

import java.util.Stack;

public class Main {
    public static int evaluateExpression(String expression) {
        String[] tokens = expression.split("\s");
        Stack<Integer> numbers = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                numbers.push(Integer.parseInt(token));
            }
            if (isOperator(token)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)) {
                    String operator = operators.pop();
                    processTopOperation(numbers, operator);
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            String operator = operators.pop();
            processTopOperation(numbers, operator);
        }
        return numbers.pop();
    }

    private static boolean isNumber(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(String token) {
        return "+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token);
    }

    private static int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return -1;
        }
    }

    private static void processTopOperation(Stack<Integer> numbers, String operator) {
        int b = numbers.pop();
        int a = numbers.pop();
        numbers.push(applyOperation(a, b, operator));
    }

    private static int applyOperation(int a, int b, String operator) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }


    public static void main(String[] args) {
        System.out.println(evaluateExpression("2 + 3"));         // Output: 5
        System.out.println(evaluateExpression("3 * 2 + 1"));     // Output: 7
        System.out.println(evaluateExpression("3 * -2 + 6"));    // Output: 0
        System.out.println(evaluateExpression("3 + -2 * 6"));    // Output: -9
        System.out.println(evaluateExpression("3 + -2 * 6 / 0"));    // Output: exception
    }
}