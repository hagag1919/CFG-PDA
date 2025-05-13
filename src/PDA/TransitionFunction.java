package PDA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TransitionFunction {
    private Map<TransitionKey, List<TransitionValue>> transitions = new HashMap<>();

    public void addTransition(int currentState, char input, char stackTop, int nextState, String stackPush) {
        TransitionKey key = new TransitionKey(currentState, input, stackTop);
        TransitionValue value = new TransitionValue(nextState, stackTop, stackPush);

        if (!transitions.containsKey(key)) {
            transitions.put(key, new ArrayList<>());
        }
        transitions.get(key).add(value);
    }

    public List<TransitionValue> getTransitions(int currentState, char input, char stackTop) {
        TransitionKey key = new TransitionKey(currentState, input, stackTop);
        return transitions.getOrDefault(key, new ArrayList<>());
    }

    public TransitionValue getTransition(int currentState, char input, char stackTop) {
        List<TransitionValue> values = getTransitions(currentState, input, stackTop);
        return values.isEmpty() ? null : values.get(0);
    }
}