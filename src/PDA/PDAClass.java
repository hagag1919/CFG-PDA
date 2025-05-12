package PDA;

import java.io.*;
import java.util.*;

public class PDAClass {
    private ArrayList<Integer> states;
    private ArrayList<Character> inputAlphabet;
    private ArrayList<Character> stackAlphabet;
    private TransitionFunction transitionFunction;
    private int startState;
    private ArrayList<Integer> finalStates;
    private char stackInitial;

    public PDAClass(ArrayList<Integer> states, ArrayList<Character> inputAlphabet,
                    ArrayList<Character> stackAlphabet, TransitionFunction transitionFunction,
                    int startState, ArrayList<Integer> finalStates, char stackInitial) {
        this.states = states;
        this.inputAlphabet = inputAlphabet;
        this.stackAlphabet = stackAlphabet;
        this.transitionFunction = transitionFunction;
        this.startState = startState;
        this.finalStates = finalStates;
        this.stackInitial = stackInitial;
    }

    public boolean isAccepted(String input) {
        Stack<Character> stack = new Stack<>();
        stack.push(stackInitial);
        return simulate(startState, input, 0, stack);
    }

    private boolean simulate(int currentState, String input, int index, Stack<Character> stack) {
        char inputChar = (index < input.length()) ? input.charAt(index) : 'ε';
        char stackTop = stack.isEmpty() ? 'ε' : stack.peek();

        // Try input transition
        TransitionValue transition = transitionFunction.getTransition(currentState, inputChar, stackTop);
        if (transition != null) {
            Stack<Character> newStack = new Stack<>();
            newStack.addAll(stack);
            if (!newStack.isEmpty()) newStack.pop();
            for (int i = transition.stackPush.length() - 1; i >= 0; i--) {
                char c = transition.stackPush.charAt(i);
                if (c != 'ε') newStack.push(c);
            }
            int nextIndex = inputChar == 'ε' ? index : index + 1;
            if (simulate(transition.nextState, input, nextIndex, newStack)) return true;
        }

        // Try ε transition
        TransitionValue epsilonTransition = transitionFunction.getTransition(currentState, 'ε', stackTop);
        if (epsilonTransition != null) {
            Stack<Character> newStack = new Stack<>();
            newStack.addAll(stack);
            if (!newStack.isEmpty()) newStack.pop();
            for (int i = epsilonTransition.stackPush.length() - 1; i >= 0; i--) {
                char c = epsilonTransition.stackPush.charAt(i);
                if (c != 'ε') newStack.push(c);
            }
            if (simulate(epsilonTransition.nextState, input, index, newStack)) return true;
        }

        return index == input.length() && finalStates.contains(currentState);
    }

    public void solveProblem(BufferedReader br, BufferedWriter bw) throws IOException {
        String line;
        int problemNumber = 0;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            problemNumber = Integer.parseInt(line.trim());
            bw.write(problemNumber + "\n");

            while (!(line = br.readLine()).equals("end")) {
                boolean accepted = isAccepted(line.trim());
                bw.write(accepted ? "Accepted\n" : "Rejected\n");
            }

            bw.write("x\n");
        }
        bw.flush();
    }
}