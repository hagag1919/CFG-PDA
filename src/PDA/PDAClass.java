package PDA;

import java.io.*;
import java.util.*;

public class PDAClass {
    private Set<Integer> states;
    private Set<Character> inputAlphabet;
    private Set<Character> stackAlphabet;
    private TransitionFunction transitionFunction;
    private int startState;
    private Set<Integer> finalStates;
    private char stackInitial;
    private boolean acceptByEmptyStack;

    public PDAClass(List<Integer> states, List<Character> inputAlphabet,
                    List<Character> stackAlphabet, TransitionFunction transitionFunction,
                    int startState, List<Integer> finalStates, char stackInitial) {
        this(states, inputAlphabet, stackAlphabet, transitionFunction, startState, finalStates, stackInitial, false);
    }
    
    public PDAClass(List<Integer> states, List<Character> inputAlphabet,
                    List<Character> stackAlphabet, TransitionFunction transitionFunction,
                    int startState, List<Integer> finalStates, char stackInitial, boolean acceptByEmptyStack) {
        this.states = new HashSet<>(states);
        this.inputAlphabet = new HashSet<>(inputAlphabet);
        this.stackAlphabet = new HashSet<>(stackAlphabet);
        this.transitionFunction = transitionFunction;
        this.startState = startState;
        this.finalStates = new HashSet<>(finalStates);
        this.stackInitial = stackInitial;
        this.acceptByEmptyStack = acceptByEmptyStack;
    }

    public boolean isAccepted(String input) {
        Stack<Character> stack = new Stack<>();
        stack.push(stackInitial);
        return simulate(startState, input, 0, stack, new HashSet<>());
    }

    private boolean simulate(int currentState, String input, int index, Stack<Character> stack, Set<String> visited) {
        // Create a unique key for the current state to avoid infinite recursion
        String key = currentState + ":" + index + ":" + stack.toString();
        if (visited.contains(key)) {
            return false;
        }
        visited.add(key);
        
        // Accept if we've consumed all input and are in a final state or have empty stack (based on acceptance mode)
        if (index == input.length()) {
            if ((finalStates.contains(currentState) && !acceptByEmptyStack) || 
                (stack.isEmpty() && acceptByEmptyStack) ||
                (finalStates.contains(currentState) && stack.isEmpty())) {
                return true;
            }
            
            // Try epsilon transitions
            char stackTop = stack.isEmpty() ? 'ε' : stack.peek();
            List<TransitionValue> epsilonTransitions = transitionFunction.getTransitions(currentState, 'ε', stackTop);
            
            for (TransitionValue transition : epsilonTransitions) {
                Stack<Character> stackCopy = new Stack<>();
                stackCopy.addAll(stack);
                
                // Handle stack operation for epsilon transition
                if (processStackOperation(stackCopy, transition)) {
                    if (simulate(transition.nextState, input, index, stackCopy, new HashSet<>(visited))) {
                        return true;
                    }
                }
            }
            
            return false;
        }

        char currentInput = input.charAt(index);
        char stackTop = stack.isEmpty() ? 'ε' : stack.peek();

        // Try regular transitions with current input
        List<TransitionValue> transitions = transitionFunction.getTransitions(currentState, currentInput, stackTop);
        for (TransitionValue transition : transitions) {
            Stack<Character> stackCopy = new Stack<>();
            stackCopy.addAll(stack);
            
            // Handle stack operation for input transition
            if (processStackOperation(stackCopy, transition)) {
                if (simulate(transition.nextState, input, index + 1, stackCopy, new HashSet<>(visited))) {
                    return true;
                }
            }
        }
        
        // Try epsilon transitions
        List<TransitionValue> epsilonTransitions = transitionFunction.getTransitions(currentState, 'ε', stackTop);
        for (TransitionValue transition : epsilonTransitions) {
            Stack<Character> stackCopy = new Stack<>();
            stackCopy.addAll(stack);
            
            // Handle stack operation for epsilon transition
            if (processStackOperation(stackCopy, transition)) {
                if (simulate(transition.nextState, input, index, stackCopy, new HashSet<>(visited))) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private boolean processStackOperation(Stack<Character> stack, TransitionValue transition) {
        // Pop from stack if required and if stack has the correct top symbol
        if (transition.stackPop != 'ε') {
            if (stack.isEmpty() || stack.peek() != transition.stackPop) {
                return false;
            }
            stack.pop();
        }
        
        // Push new symbols onto stack (in reverse order)
        for (int i = transition.stackPush.length() - 1; i >= 0; i--) {
            if (transition.stackPush.charAt(i) != 'ε') {
                stack.push(transition.stackPush.charAt(i));
            }
        }
        
        return true;
    }

    public void solveProblem(BufferedReader br, BufferedWriter bw) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            if (line.equals("end")) break;
            boolean accepted = isAccepted(line.trim());
            bw.write(accepted ? "accepted\n" : "not accepted\n");
        }
        bw.write("x\n");
        bw.flush();
    }
}
