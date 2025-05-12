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


