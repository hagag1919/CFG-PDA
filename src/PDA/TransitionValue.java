package PDA;

class TransitionValue {
    int nextState;
    char stackPop;
    String stackPush;

    TransitionValue(int nextState, char stackPop, String stackPush) {
        this.nextState = nextState;
        this.stackPop = stackPop;
        this.stackPush = stackPush;
    }
}


