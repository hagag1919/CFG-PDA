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

    // PDA for language a^n b^m c^n (n | m ≥ 0)
    private static PDAClass problemOnePDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        ArrayList<Integer> finalStates = new ArrayList<>(Collections.singletonList(4));
        ArrayList<Character> inputAlphabet = new ArrayList<>(Arrays.asList('a', 'b', 'c'));
        ArrayList<Character> stackAlphabet = new ArrayList<>(Arrays.asList('$', 'A'));
        int startState = 0;
        char stackInitial = '$';
        TransitionFunction transitionFunction = new TransitionFunction();

        transitionFunction.addTransition(0, 'ε', '$', 4, "$");
        
        transitionFunction.addTransition(0, 'a', '$', 0, "A$");
        transitionFunction.addTransition(0, 'a', 'A', 0, "AA");
        
        transitionFunction.addTransition(0, 'ε', 'A', 1, "A");
        transitionFunction.addTransition(0, 'ε', '$', 1, "$");
    
        transitionFunction.addTransition(1, 'b', 'A', 1, "A");
        transitionFunction.addTransition(1, 'b', '$', 1, "$");
        
        transitionFunction.addTransition(1, 'ε', 'A', 2, "A");
        transitionFunction.addTransition(1, 'ε', '$', 2, "$");
    
        transitionFunction.addTransition(2, 'c', 'A', 2, "ε");
        
        transitionFunction.addTransition(2, 'ε', '$', 4, "$");

        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }

    // PDA for language { a^(3n) b^(2n) | n ≥ 1 }
    private static PDAClass problemTwoPDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
        ArrayList<Integer> finalStates = new ArrayList<>(Collections.singletonList(5));
        ArrayList<Character> inputAlphabet = new ArrayList<>(Arrays.asList('a', 'b'));
        ArrayList<Character> stackAlphabet = new ArrayList<>(Arrays.asList('$', 'X'));
        int startState = 0;
        char stackInitial = '$';
        TransitionFunction transitionFunction = new TransitionFunction();

        transitionFunction.addTransition(0, 'ε', '$', 1, "$");

        transitionFunction.addTransition(1, 'a', '$', 2, "$");
        transitionFunction.addTransition(1, 'a', 'X', 2, "X");

        transitionFunction.addTransition(2, 'a', '$', 3, "$");
        transitionFunction.addTransition(2, 'a', 'X', 3, "X");

        transitionFunction.addTransition(3, 'a', '$', 1, "XX$");
        transitionFunction.addTransition(3, 'a', 'X', 1, "XXX");

        transitionFunction.addTransition(1, 'ε', '$', 4, "$");
        transitionFunction.addTransition(1, 'ε', 'X', 4, "X");

        transitionFunction.addTransition(4, 'b', 'X', 4, "ε");

        transitionFunction.addTransition(4, 'ε', '$', 5, "$");
        
        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }

    // PDA for balanced parentheses language
    private static PDAClass problemThreePDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2));
        ArrayList<Integer> finalStates = new ArrayList<>(Collections.singletonList(2));
        ArrayList<Character> inputAlphabet = new ArrayList<>(Arrays.asList('{', '}', ' '));
        ArrayList<Character> stackAlphabet = new ArrayList<>(Arrays.asList('$', '{'));
        int startState = 0;
        char stackInitial = '$';
        TransitionFunction transitionFunction = new TransitionFunction();

        transitionFunction.addTransition(0, 'ε', '$', 1, "$");
        
        transitionFunction.addTransition(1, '{', '$', 1, "{$");
        transitionFunction.addTransition(1, '{', '{', 1, "{{");
        
        transitionFunction.addTransition(1, '}', '{', 1, "ε");
        
        transitionFunction.addTransition(1, ' ', '$', 1, "$");
        transitionFunction.addTransition(1, ' ', '{', 1, "{");
        
        transitionFunction.addTransition(1, 'ε', '$', 2, "$");

        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }

    // PDA for language { a^n b^m+n c^m | n,m>=1 }
    private static PDAClass problemFourPDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        ArrayList<Integer> finalStates = new ArrayList<>(Collections.singletonList(4));
        ArrayList<Character> inputAlphabet = new ArrayList<>(Arrays.asList('a', 'b', 'c'));
        ArrayList<Character> stackAlphabet = new ArrayList<>(Arrays.asList('$', 'A', 'B'));
        int startState = 0;
        char stackInitial = '$';
        TransitionFunction transitionFunction = new TransitionFunction();

        transitionFunction.addTransition(0, 'ε', '$', 1, "$");
        
        transitionFunction.addTransition(1, 'a', '$', 1, "A$");
        transitionFunction.addTransition(1, 'a', 'A', 1, "AA");
        
        transitionFunction.addTransition(1, 'b', 'A', 2, "ε");
        
        transitionFunction.addTransition(2, 'b', 'A', 2, "ε"); // Continue consuming the A's
        transitionFunction.addTransition(2, 'b', '$', 3, "B$"); // First additional b, push B
        transitionFunction.addTransition(3, 'b', 'B', 3, "BB"); // More additional b's
        transitionFunction.addTransition(3, 'b', '$', 3, "B$");
        
        transitionFunction.addTransition(3, 'c', 'B', 3, "ε");
        
        transitionFunction.addTransition(3, 'ε', '$', 4, "$");

        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }

    // PDA for L={W c^k ∣ W∈{a,b}*,k=number of b in W}
    private static PDAClass problemFivePDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        ArrayList<Integer> finalStates = new ArrayList<>(Collections.singletonList(4));
        ArrayList<Character> inputAlphabet = new ArrayList<>(Arrays.asList('a', 'b', 'c'));
        ArrayList<Character> stackAlphabet = new ArrayList<>(Arrays.asList('$', 'B', '#'));
        int startState = 0;
        char stackInitial = '$';
        TransitionFunction transitionFunction = new TransitionFunction();

        transitionFunction.addTransition(0, 'ε', '$', 1, "#$");
        
        transitionFunction.addTransition(1, 'a', '#', 1, "#");
        transitionFunction.addTransition(1, 'a', 'B', 1, "B");
        
        transitionFunction.addTransition(1, 'b', '#', 1, "B#");
        transitionFunction.addTransition(1, 'b', 'B', 1, "BB");

        transitionFunction.addTransition(1, 'c', 'B', 2, "ε");
        
        transitionFunction.addTransition(2, 'c', 'B', 2, "ε");
        
        transitionFunction.addTransition(2, 'ε', '#', 3, "ε");
        
        transitionFunction.addTransition(3, 'ε', '$', 4, "$");
        
        transitionFunction.addTransition(1, 'ε', '#', 3, "ε");
        
        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }
}