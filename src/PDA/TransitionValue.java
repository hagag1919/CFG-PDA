package PDA;

import java.util.HashMap;
import java.util.Map;

public class TransitionValue {
    int nextState;
    String stackPush;

    TransitionValue(int nextState, String stackPush) {
        this.nextState = nextState;
        this.stackPush = stackPush;
    }
}

class TransitionFunction {
    private Map<TransitionKey, TransitionValue> transitions = new HashMap<>();

    public void addTransition(int currentState, char input, char stackTop, int nextState, String stackPush) {
        TransitionKey key = new TransitionKey(currentState, input, stackTop);
        TransitionValue value = new TransitionValue(nextState, stackPush);
        transitions.put(key, value);
    }

    public TransitionValue getTransition(int currentState, char input, char stackTop) {
        return transitions.get(new TransitionKey(currentState, input, stackTop));
    }
}
