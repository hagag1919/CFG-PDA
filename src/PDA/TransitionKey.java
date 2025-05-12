package PDA;

import java.util.Objects;

public class TransitionKey {
    int currentState;
    char input;
    char stackTop;

    TransitionKey(int currentState, char input, char stackTop) {
        this.currentState = currentState;
        this.input = input;
        this.stackTop = stackTop;
    }
}