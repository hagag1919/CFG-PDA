package PDA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;

public class PDAClass {
    ArrayList<Integer> states;
    ArrayList<Character> inputAlphabet;
    ArrayList<Character> stackAlphabet;
    TransitionFunction transitionFunction;
    int startState;
    ArrayList<Integer> finalStates;
    char stackInitial;


    PDAClass(ArrayList<Integer> states, ArrayList<Character> inputAlphabet,
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

    public boolean isAccepted(String s) {
        // return true if string valid
        // return false if string invalid
        return false;
    }

    public void solveProblem(BufferedReader br, BufferedWriter bw) {
        // read input from input file then call isAccept method
        // and write result in output file
    }
}

