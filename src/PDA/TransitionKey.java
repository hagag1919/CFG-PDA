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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransitionKey)) return false;
        TransitionKey that = (TransitionKey) o;
        return currentState == that.currentState &&
                input == that.input &&
                stackTop == that.stackTop;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentState, input, stackTop);
    }
}