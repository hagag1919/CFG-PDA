package PDA;

import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;

public class PDATest {
    public static void main(String[] args) {
        testProblemOne();
        testProblemTwo();
        testProblemThree();
        testProblemFour();
        testProblemFive();
    }

    private static void testProblemOne() {
        System.out.println("Testing Problem 1 (a^n b^m c^n):");
        PDAClass pda = createProblemOnePDA();
        
        // Test cases
        testString(pda, "", true);  // n=0 , m=0    
        testString(pda, "abc", true);  // n=1 , m=1
        testString(pda, "aabbcc", true);  // n=2 , m=2
        testString(pda, "aaabbbccc", true);  // n=3 , m=3
        testString(pda, "ab", false);  // missing c , n=1 , m=0
        testString(pda, "aabbc", false);  // not enough c , n=2 , m=2
        testString(pda, "aabbbccc", false);  // not enough a , n=3 , m=3
        testString(pda, "aabccc", false);  // not equal number , n=3 , m=1
        testString(pda, "aaabbbbccc", true);  // n=3 , m=4
        testString(pda, "abbc", true);  // not equal number , n=1 , m=2    
        System.out.println();
    }

    private static void testProblemTwo() {
        System.out.println("Testing Problem 2 (a^(3n) b^(2n)):");
        PDAClass pda = createProblemTwoPDA();
        
        // Test cases
        testString(pda, "aaabb", true);  // n=1
        testString(pda, "aaaaaabbbb", true);  // n=2
        testString(pda, "aaaaaaaaabbbbbb", true);  // n=3
        testString(pda, "aabb", false);  // incorrect ratio
        testString(pda, "aaabbb", false);  // incorrect ratio
        testString(pda, "aaaabb", false);  // incorrect ratio
        System.out.println();
    }

    private static void testProblemThree() {
        System.out.println("Testing Problem 3 (Balanced Parentheses):");
        PDAClass pda = createProblemThreePDA();
        
        // Test cases
        testString(pda, "", true);  // empty string is balanced
        testString(pda, "{}", true);  // simple balanced
        testString(pda, "{{}}", true);  // nested balanced
        testString(pda, "{ { } }", true);  // with spaces
        testString(pda, "{{}}{{}}}", false);  // unbalanced
        testString(pda, "{", false);  // unbalanced
        testString(pda, "}", false);  // unbalanced
        System.out.println();
    }

    private static void testProblemFour() {
        System.out.println("Testing Problem 4 (a^n b^(m+n) c^m):");
        PDAClass pda = createProblemFourPDA();
        
        // Test cases
        testString(pda, "abbbc", false);  // n=1, m=1
        testString(pda, "aabbbcc", false);  // n=2, m=2
        testString(pda, "aaabbbbccc", false);  // n=3, m=3
        testString(pda, "abbc", true);  // n=1, m=1
        testString(pda, "abc", false);  // not enough b
        testString(pda, "abbcc", false);  // incorrect ratio
        testString(pda, "aabbbbcc", true);
        testString(pda, "aaabbbbbcc", true);  // n=3, m=3
        testString(pda, "abbbcc", true);  // incorrect ratio
        System.out.println();
    }

    private static void testProblemFive() {
        System.out.println("Testing Problem 5 (W c^k where k=number of b in W):");
        PDAClass pda = createProblemFivePDA();
        
        // Test cases
        testString(pda, "c", false);  // W="", k=0
        testString(pda, "ac", false);  // W="a", k=0
        testString(pda, "bc", true);  // W="b", k=1
        testString(pda, "abcc", false);  // W="ab", k=1
        testString(pda, "abbccc", false);  // W="abb", k=2
        testString(pda, "abccc", false);  // incorrect count
        testString(pda, "aabbbccc", true);  // W="aabbb", k=3
        testString(pda, "aabbcc", true);  // W="aabb", k=2

        testString(pda, "abaabbaccc", true);  // W="aaabbb", k=3
        testString(pda, "aaabbbccc", true);  // W="aabb", k=2
        testString(pda, "aabaabbcc", false);  // W="aabb", k=2
        testString(pda, "bbaabbcccc", true);  // W="aabbb", k=3
        testString(pda, "aac", false);  // W="bb", k=2
        System.out.println();
    }

    private static void testString(PDAClass pda, String input, boolean expected) {
        boolean result = pda.isAccepted(input);
        System.out.println("Input: \"" + input + "\" - " + 
                          (result ? "Accepted" : "Rejected") + 
                          (result == expected ? " (Correct)" : " (Incorrect)"));
    }

    // PDA for language a^n b^m c^n (n | m ≥ 0)
    private static PDAClass createProblemOnePDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        ArrayList<Integer> finalStates = new ArrayList<>(Arrays.asList(4));
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
    private static PDAClass createProblemTwoPDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        ArrayList<Integer> finalStates = new ArrayList<>(Arrays.asList(7));
        ArrayList<Character> inputAlphabet = new ArrayList<>(Arrays.asList('a', 'b'));
        ArrayList<Character> stackAlphabet = new ArrayList<>(Arrays.asList('$', 'a'));
        int startState = 0;
        char stackInitial = '$';
        TransitionFunction transitionFunction = new TransitionFunction();

        // Following the exact state diagram provided
        // State 0: initial state
        transitionFunction.addTransition(0, 'ε', 'ε', 1, "$"); // Push $ and go to state 1
        
        // State 1: Read first a
        transitionFunction.addTransition(1, 'a', 'ε', 2, "ε"); // Read a, epsilon -> epsilon
        
        // State 2: Can read a's
        transitionFunction.addTransition(2, 'a', 'ε', 2, "ε"); // Read a (loop), epsilon -> epsilon
        transitionFunction.addTransition(2, 'a', 'ε', 3, "a"); // Read a, epsilon -> a (push a)
        
        // State 3: Continue reading a's or start reading b's
        transitionFunction.addTransition(3, 'a', 'ε', 4, "a"); // Read a, epsilon -> a (push a)
        
        // State a: Can read more a's or b's
        transitionFunction.addTransition(4, 'a', 'ε', 2, "ε"); // Read a, epsilon -> epsilon (go to state 2)
        transitionFunction.addTransition(4, 'b', 'a', 5, "ε"); // Read b, pop a, go to state 5
        
        // State 5: Read b's
        transitionFunction.addTransition(5, 'b', 'a', 5, "ε"); // Read b, pop a, stay in state 5
        transitionFunction.addTransition(5, 'b', 'a', 6, "ε"); // Read b, pop a, go to state 6
        
        // State 6: Final transitions
        transitionFunction.addTransition(6, 'b', 'a', 5, "ε"); // Read b, pop a, go to state 5
        transitionFunction.addTransition(6, 'ε', '$', 7, "ε"); // If $ is left, go to accepting state
        
        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }

    // PDA for balanced parentheses language
    private static PDAClass createProblemThreePDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2));
        ArrayList<Integer> finalStates = new ArrayList<>(Arrays.asList(2));
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

    // PDA for language { a^n b^(m+n) c^m | n,m>=1 }
    private static PDAClass createProblemFourPDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        ArrayList<Integer> finalStates = new ArrayList<>(Arrays.asList(4));
        ArrayList<Character> inputAlphabet = new ArrayList<>(Arrays.asList('a', 'b', 'c'));
        ArrayList<Character> stackAlphabet = new ArrayList<>(Arrays.asList('$', 'A', 'B'));
        int startState = 0;
        char stackInitial = '$';
        TransitionFunction transitionFunction = new TransitionFunction();

        // Read a's and push A's onto stack
        transitionFunction.addTransition(0, 'a', '$', 1, "A$");
        transitionFunction.addTransition(1, 'a', 'A', 1, "AA");
        transitionFunction.addTransition(1, 'a', '$', 1, "A$");
        
        // Read b's, first matching the number of a's
        transitionFunction.addTransition(1, 'b', 'A', 1, "ε");
        
        // Then read additional b's and push B's for each
        transitionFunction.addTransition(1, 'b', '$', 2, "B$");
        transitionFunction.addTransition(2, 'b', 'B', 2, "BB");
        transitionFunction.addTransition(2, 'b', '$', 2, "B$");
        
        // Read c's and pop B's
        transitionFunction.addTransition(2, 'c', 'B', 3, "ε");
        transitionFunction.addTransition(3, 'c', 'B', 3, "ε");
        
        // Accept when all B's are consumed
        transitionFunction.addTransition(3, 'ε', '$', 4, "$");

        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }

    // PDA for L={W c^k ∣ W∈{a,b}*,k=number of b in W}
    private static PDAClass createProblemFivePDA() {
        ArrayList<Integer> states = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        ArrayList<Integer> finalStates = new ArrayList<>(Arrays.asList(4));
        ArrayList<Character> inputAlphabet = new ArrayList<>(Arrays.asList('a', 'b', 'c'));
        ArrayList<Character> stackAlphabet = new ArrayList<>(Arrays.asList('$', 'B', '#'));
        int startState = 0;
        char stackInitial = '$';
        TransitionFunction transitionFunction = new TransitionFunction();

        // Initial transition - push a marker # onto stack
        transitionFunction.addTransition(0, 'ε', '$', 1, "#$");
        
        // Process a's without changing stack
        transitionFunction.addTransition(1, 'a', '#', 1, "#");
        transitionFunction.addTransition(1, 'a', 'B', 1, "B");
        
        // Push B for each b encountered
        transitionFunction.addTransition(1, 'b', '#', 1, "B#");
        transitionFunction.addTransition(1, 'b', 'B', 1, "BB");
        
        // Transition to state 2 when we see first c, but only if there's at least one B
        transitionFunction.addTransition(1, 'c', 'B', 2, "ε");
        
        // If there's no B on stack (no b in W), don't allow transition to c-processing state
        // This prevents accepting strings with c's when there are no b's in W
        
        // Continue consuming c's, pop B's
        transitionFunction.addTransition(2, 'c', 'B', 2, "ε");
        
        // When no more B's, check if we're at marker #
        transitionFunction.addTransition(2, 'ε', '#', 3, "ε");
        
        // Accept when we've consumed the marker and are at $
        transitionFunction.addTransition(3, 'ε', '$', 4, "$");

        return new PDAClass(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial);
    }
} 