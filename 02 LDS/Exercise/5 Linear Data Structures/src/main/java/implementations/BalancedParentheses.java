package implementations;

import interfaces.Solvable;

import java.util.ArrayDeque;

public class BalancedParentheses implements Solvable {
    private String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        String input = this.parentheses;
        java.util.ArrayDeque<Character> stack = new ArrayDeque<>();
        boolean isBalanced = false;

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (currentChar == '(' || currentChar == '[' || currentChar == '{') {
                stack.push(currentChar);
            } else if (currentChar == ')' || currentChar == ']' || currentChar == '}') {

                if (stack.isEmpty()) {
                    isBalanced = false;
                    break;
                }

                char currentOpenBracket = stack.pop();

                if (currentOpenBracket == '(' && currentChar == ')') {
                    isBalanced = true;
                } else if (currentOpenBracket == '[' && currentChar == ']') {
                    isBalanced = true;
                } else if (currentOpenBracket == '{' && currentChar == '}') {
                    isBalanced = true;
                } else {
                    isBalanced = false;
                    break;
                }
            }
        }

        return isBalanced;
    }
}
