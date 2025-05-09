package CFG;

import java.util.ArrayList;
import java.util.Map;

public class CFGModel {
    ArrayList<Character> terminals; 
    ArrayList<Character> nonTerminals; 
    Character startSymbol; 
    Map<Character, ArrayList<String>> productionRules;
     
    public CFGModel(ArrayList<Character> terminals, ArrayList<Character> nonTerminals, Character startSymbol, Map<Character, ArrayList<String>> productionRules) {
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.startSymbol = startSymbol;
        this.productionRules = productionRules;
    }
}