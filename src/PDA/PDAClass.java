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
        int currentState = startState;

    }

    public void solveProblem(BufferedReader br, BufferedWriter bw) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue; // Skip empty lines
            if (line.equals("end")) break; // Stop processing when "end" is encountered

            boolean accepted = isAccepted(line.trim());
            bw.write(accepted ? "Accepted\n" : "Rejected\n");
        }
        bw.write("x\n"); // Write "x" after processing all inputs for a problem
        bw.flush();
    }
}