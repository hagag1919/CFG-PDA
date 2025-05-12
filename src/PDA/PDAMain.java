package PDA;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PDAMain {
    static String pdaInput = "input_pda.txt";
    static String pdaOutput = "output_pda.txt";

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(pdaInput));
             BufferedWriter bw = new BufferedWriter(new FileWriter(pdaOutput))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip empty lines

                int option;
                try {
                    option = Integer.parseInt(line.trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid problem number: " + line);
                    continue;
                }

                bw.write(option + "\n");
                bw.flush();

                PDAClass pda = createPDAForProblem(option);
                if (pda == null) {
                    System.err.println("No PDA defined for problem number: " + option);
                    continue;
                }

                pda.solveProblem(br, bw);
            }
        }
    }

    private static PDAClass createPDAForProblem(int problemNumber) {
        switch (problemNumber) {
            case 1:
                return problemOnePDA();
            case 2:
                return problemTwoPDA();
            case 3:
                return problemThreePDA();
            case 4:
                return problemFourPDA();
            case 5:
                return problemFivePDA();
            default:
                return null;
        }
    }

    // PDA for language a^n b^n c^n (n ≥ 0)
    private static PDAClass problemOnePDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        ArrayList<Integer> finalStates = new ArrayList<>(Collections.singletonList(4));
        ArrayList<Character> inputAlphabet = new ArrayList<>(Arrays.asList('a', 'b', 'c'));
        ArrayList<Character> stackAlphabet = new ArrayList<>(Arrays.asList('$', 'a'));
        int startState = 0;
        char stackInitial = '$';
        TransitionFunction transitionFunction = new TransitionFunction();

        transitionFunction.addTransition(0, 'ε', 'ε', 1, "$");
        transitionFunction.addTransition(1, 'a', 'ε', 1, "a");
        transitionFunction.addTransition(1, 'ε', 'ε', 2, "ε");
        transitionFunction.addTransition(2, 'b', 'ε', 2, "ε");
        transitionFunction.addTransition(2, 'ε', 'ε', 3, "ε");
        transitionFunction.addTransition(3, 'c', 'a', 3, "ε");
        transitionFunction.addTransition(3, 'ε', '$', 4, "ε");

        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }

    // PDA for language { a^(3n) b^(2n) | n ≥ 1 }
    private static PDAClass problemTwoPDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
        ArrayList<Integer> finalStates = new ArrayList<>(Collections.singletonList(6));
        ArrayList<Character> inputAlphabet = new ArrayList<>(Arrays.asList('a', 'b'));
        ArrayList<Character> stackAlphabet = new ArrayList<>(Arrays.asList('$', 'a'));
        int startState = 0;
        char stackInitial = '$';
        TransitionFunction transitionFunction = new TransitionFunction();

        transitionFunction.addTransition(0, 'ε', 'ε', 1, "$");
        transitionFunction.addTransition(1, 'a', 'ε', 2, "a");
        transitionFunction.addTransition(2, 'a', 'ε', 3, "a");
        transitionFunction.addTransition(3, 'a', 'ε', 4, "a");
        transitionFunction.addTransition(4, 'b', 'a', 5, "ε");
        transitionFunction.addTransition(5, 'b', 'a', 6, "ε");
        transitionFunction.addTransition(6, 'ε', '$', 6, "ε");

        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }

    // TODO : handel the spaces
    // PDA for balanced parentheses language
    private static PDAClass problemThreePDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        ArrayList<Integer> finalStates = new ArrayList<>(Collections.singletonList(3));
        ArrayList<Character> inputAlphabet = new ArrayList<>(Arrays.asList('{', '}',' '));
        ArrayList<Character> stackAlphabet = new ArrayList<>(Arrays.asList('$', '{', '}'));
        int startState = 0;
        char stackInitial = '$';
        TransitionFunction transitionFunction = new TransitionFunction();

        transitionFunction.addTransition(0, 'ε', 'ε', 1, "$");
        transitionFunction.addTransition(1, '{', 'ε', 1, "{");
        transitionFunction.addTransition(1,' ', 'ε', 1, "ε");
        transitionFunction.addTransition(1, '}', '{', 2, "ε");
        transitionFunction.addTransition(2, '{', 'ε', 1, "{");
        transitionFunction.addTransition(2, '}', '{', 2, "ε");
        transitionFunction.addTransition(2, ' ', 'ε', 2, "ε");
        transitionFunction.addTransition(2, 'ε', '$', 3, "ε");

        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }

    // PDA for language { a^n b^m+n c^m | n,m>=1 }
    private static PDAClass problemFourPDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
        ArrayList<Integer> finalStates = new ArrayList<>(Collections.singletonList(5));
        ArrayList<Character> inputAlphabet = new ArrayList<>(Arrays.asList('a', 'b', 'c'));
        ArrayList<Character> stackAlphabet = new ArrayList<>(Arrays.asList('$', 'a', 'b'));
        int startState = 0;
        char stackInitial = '$';
        TransitionFunction transitionFunction = new TransitionFunction();

        transitionFunction.addTransition(0, 'ε', 'ε', 1, "$");
        transitionFunction.addTransition(1, 'a', 'ε', 1, "a");
        transitionFunction.addTransition(1, 'b', 'a', 2, "ε");
        transitionFunction.addTransition(2, 'b', 'a', 2, "ε");
        transitionFunction.addTransition(2, 'b', 'ε', 3, "b");
        transitionFunction.addTransition(3, 'b', 'ε', 3, "b");
        transitionFunction.addTransition(3, 'c', 'b', 4, "ε");
        transitionFunction.addTransition(4, 'c', 'b', 4, "ε");
        transitionFunction.addTransition(4, 'ε', '$', 5, "ε");

        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }

    // PDA for L={W c^k ∣ W∈{a,b}*,k=number of b in W}
    private static PDAClass problemFivePDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        ArrayList<Integer> finalStates = new ArrayList<>(Collections.singletonList(4));
        ArrayList<Character> inputAlphabet = new ArrayList<>(Arrays.asList('a', 'b', 'c'));
        ArrayList<Character> stackAlphabet = new ArrayList<>(Arrays.asList('$', 'b'));
        int startState = 0;
        char stackInitial = '$';
        TransitionFunction transitionFunction = new TransitionFunction();

        transitionFunction.addTransition(0, 'ε', 'ε', 1, "$");
        transitionFunction.addTransition(1, 'a', 'ε', 1, "ε");
        transitionFunction.addTransition(1, 'b', 'ε', 1, "b");
        transitionFunction.addTransition(1, 'c', 'ε', 2, "ε");
        transitionFunction.addTransition(2, 'c', 'b', 3, "ε");
        transitionFunction.addTransition(3, 'c', 'b', 3, "ε");
        transitionFunction.addTransition(3, 'ε', '$', 4, "ε");

        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }
}