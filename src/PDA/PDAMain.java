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

        // Handle n=0 (empty string)
        transitionFunction.addTransition(0, 'ε', '$', 4, "$");
        
        // Process 'a's and push 'A's onto the stack
        transitionFunction.addTransition(0, 'a', '$', 0, "A$");
        transitionFunction.addTransition(0, 'a', 'A', 0, "AA");
        
        // Transition to state 1 to process b's
        transitionFunction.addTransition(0, 'ε', 'A', 1, "A");
        transitionFunction.addTransition(0, 'ε', '$', 1, "$");
    
        // Process any number of 'b's without changing the stack
        transitionFunction.addTransition(1, 'b', 'A', 1, "A");
        transitionFunction.addTransition(1, 'b', '$', 1, "$");
        
        // Transition to state 2 to process c's
        transitionFunction.addTransition(1, 'ε', 'A', 2, "A");
        transitionFunction.addTransition(1, 'ε', '$', 2, "$");
    
        // Process 'c's and pop 'A's
        transitionFunction.addTransition(2, 'c', 'A', 2, "ε");
        
        // Accept when all A's are consumed and only $ remains
        transitionFunction.addTransition(2, 'ε', '$', 4, "$");

        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }

    // PDA for language { a^(3n) b^(2n) | n ≥ 1 }
    private static PDAClass problemTwoPDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        ArrayList<Integer> finalStates = new ArrayList<>(Collections.singletonList(7));
        ArrayList<Character> inputAlphabet = new ArrayList<>(Arrays.asList('a', 'b'));
        ArrayList<Character> stackAlphabet = new ArrayList<>(Arrays.asList('$', 'X', 'Y'));
        int startState = 0;
        char stackInitial = '$';
        TransitionFunction transitionFunction = new TransitionFunction();

        // Start by reading a's in groups of 3, pushing one X for each group
        transitionFunction.addTransition(0, 'a', '$', 1, "$"); // Read first a
        transitionFunction.addTransition(1, 'a', '$', 2, "$"); // Read second a
        transitionFunction.addTransition(2, 'a', '$', 3, "X$"); // Read third a, push X marker
        
        // Continue reading a's in groups of 3, pushing X each time
        transitionFunction.addTransition(3, 'a', 'X', 4, "X"); // Read first a of next group
        transitionFunction.addTransition(4, 'a', 'X', 5, "X"); // Read second a of next group
        transitionFunction.addTransition(5, 'a', 'X', 3, "XX"); // Read third a, push another X marker
        
        // Transition to processing b's
        transitionFunction.addTransition(3, 'b', 'X', 6, "Y"); // Read first b, change X to Y
        
        // Read pairs of b's, popping one Y for each pair
        transitionFunction.addTransition(6, 'b', 'Y', 6, ""); // Read second b, pop Y
        
        // Accept when stack has only $ and all input is consumed
        transitionFunction.addTransition(6, 'ε', '$', 7, "$");

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

        // Initial transition
        transitionFunction.addTransition(0, 'ε', '$', 1, "$");
        
        // Push { onto stack when encountered
        transitionFunction.addTransition(1, '{', '$', 1, "{$");
        transitionFunction.addTransition(1, '{', '{', 1, "{{");
        
        // Pop { from stack when } is encountered
        transitionFunction.addTransition(1, '}', '{', 1, "ε");
        
        // Ignore spaces
        transitionFunction.addTransition(1, ' ', '$', 1, "$");
        transitionFunction.addTransition(1, ' ', '{', 1, "{");
        
        // Accept if stack has only $ left
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

        // Initial transition
        transitionFunction.addTransition(0, 'ε', '$', 1, "$");
        
        // Read a's (n >= 1) and push A's onto stack
        transitionFunction.addTransition(1, 'a', '$', 1, "A$");
        transitionFunction.addTransition(1, 'a', 'A', 1, "AA");
        
        // Read first set of b's (matching the number of a's)
        transitionFunction.addTransition(1, 'b', 'A', 2, "ε");
        
        // Read remaining b's (m >= 1) and push B's
        transitionFunction.addTransition(2, 'b', 'A', 2, "ε"); // Continue consuming the A's
        transitionFunction.addTransition(2, 'b', '$', 3, "B$"); // First additional b, push B
        transitionFunction.addTransition(3, 'b', 'B', 3, "BB"); // More additional b's
        transitionFunction.addTransition(3, 'b', '$', 3, "B$");
        
        // Read c's and pop B's
        transitionFunction.addTransition(3, 'c', 'B', 3, "ε");
        
        // Accept when all B's consumed and we're at $
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

        // Initial transition - push a marker # onto stack
        transitionFunction.addTransition(0, 'ε', '$', 1, "#$");
        
        // Process a's without changing stack (part of W)
        transitionFunction.addTransition(1, 'a', '#', 1, "#");
        transitionFunction.addTransition(1, 'a', 'B', 1, "B");
        
        // For each b in W, push a B onto stack
        transitionFunction.addTransition(1, 'b', '#', 1, "B#");
        transitionFunction.addTransition(1, 'b', 'B', 1, "BB");
        
        // Transition to state 2 when seeing first c
        // Only allow this if there's at least one B on the stack (at least one b in W)
        transitionFunction.addTransition(1, 'c', 'B', 2, "ε");
        
        // Process c's, each c pops one B
        transitionFunction.addTransition(2, 'c', 'B', 2, "ε");
        
        // When all c's processed (and if number matches Bs), we should be at # marker
        transitionFunction.addTransition(2, 'ε', '#', 3, "ε");
        
        // Accept if we've correctly popped all Bs and reached the marker
        transitionFunction.addTransition(3, 'ε', '$', 4, "$");
        
        // Handle special case: no b's in W and no c's in input (W contains only a's or is empty)
        transitionFunction.addTransition(1, 'ε', '#', 3, "ε"); // Skip from W to final check
        
        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }
}