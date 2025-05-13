package CFG;

import java.io.*;
import java.util.*;

public class CFGMain {

    static String cfgInput = "input_cfg.txt";
    static String cfgOutput = "output_cfg.txt";

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(cfgInput)); BufferedWriter bw = new BufferedWriter(new FileWriter(cfgOutput))) {

            String line;
            while ((line = br.readLine()) != null) {
                int problemNumber = Integer.parseInt(line.trim());
                bw.write(String.valueOf(problemNumber));
                bw.newLine();

                CFGClass cfg = createCFGForProblem(problemNumber);
                if (cfg == null) {
                    System.out.println("Invalid problem number: " + problemNumber);
                    continue;
                }

                cfg.solveProblem(br, bw);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static CFGClass createCFGForProblem(int problemNumber) {
        switch (problemNumber) {
            case 1:
                return problemOneCFG();
            case 2:
                return problemTwoCFG();
            case 3:
                return problemThreeCFG();
            case 4:
                return problemFourCFG();
            case 5:
                return problemFiveCFG();
            default:
                return null;
        }
    }

    // 1.	S -> aSb | bSa | ba | ab | ε
    private static CFGClass problemOneCFG() {
        ArrayList<Character> terminals = new ArrayList<>(Arrays.asList('a', 'b'));
        ArrayList<Character> nonTerminals = new ArrayList<>(Arrays.asList('S'));
        Character startSymbol = 'S';

        Map<Character, ArrayList<String>> productionRules = new HashMap<>();
        productionRules.put('S', new ArrayList<>(Arrays.asList("aSb", "bSa", "ab", "ba", "ε")));

        CFGModel cfgModel = new CFGModel(terminals, nonTerminals, startSymbol, productionRules);
        return new CFGClass(cfgModel);
    }

    // 2.	S -> aSbSbS | bSbSaS | bSaSbS | ε
    private static CFGClass problemTwoCFG() {
        ArrayList<Character> terminals = new ArrayList<>(Arrays.asList('a', 'b'));
        ArrayList<Character> nonTerminals = new ArrayList<>(Arrays.asList('S'));
        Character startSymbol = 'S';

        Map<Character, ArrayList<String>> productionRules = new HashMap<>();
        productionRules.put('S', new ArrayList<>(Arrays.asList("aSbSbS", "bSbSaS", "bSaSbS", "ε")));

        CFGModel cfgModel = new CFGModel(terminals, nonTerminals, startSymbol, productionRules);
        return new CFGClass(cfgModel);
    }

    // 3.    S -> aSa | bSb | aTb | bTa, T -> aT | bT | ε
    private static CFGClass problemThreeCFG() {
        ArrayList<Character> terminals = new ArrayList<>(Arrays.asList('a', 'b'));
        ArrayList<Character> nonTerminals = new ArrayList<>(Arrays.asList('S','T'));
        Character startSymbol = 'S';

        Map<Character, ArrayList<String>> productionRules = new HashMap<>();
        productionRules.put('S', new ArrayList<>(Arrays.asList("aSa","bSb","aTb", "bTa")));
        productionRules.put('T', new ArrayList<>(Arrays.asList("aT", "bT", "ε")));

        CFGModel cfgModel = new CFGModel(terminals, nonTerminals, startSymbol, productionRules);
        return new CFGClass(cfgModel);
    }

    //4. S -> aaaT | ε, T-> aaTb | ε
    private static CFGClass problemFourCFG() {
        ArrayList<Character> terminals = new ArrayList<>(Arrays.asList('a', 'b'));
        ArrayList<Character> nonTerminals = new ArrayList<>(Arrays.asList('S', 'T'));
        Character startSymbol = 'S';

        Map<Character, ArrayList<String>> productionRules = new HashMap<>();
        productionRules.put('S', new ArrayList<>(Arrays.asList("aaaT", "ε")));
        productionRules.put('T', new ArrayList<>(Arrays.asList("aaTb", "ε")));

        CFGModel cfgModel = new CFGModel(terminals, nonTerminals, startSymbol, productionRules);
        return new CFGClass(cfgModel);
    }

    // 5.	S -> aS | aB, B -> aBb | ε 
    private static CFGClass problemFiveCFG() {
        ArrayList<Character> terminals = new ArrayList<>(Arrays.asList('a', 'b'));
        ArrayList<Character> nonTerminals = new ArrayList<>(Arrays.asList('S', 'B'));
        Character startSymbol = 'S';

        Map<Character, ArrayList<String>> productionRules = new HashMap<>();
        productionRules.put('S', new ArrayList<>(Arrays.asList("aS", "aB")));
        productionRules.put('B', new ArrayList<>(Arrays.asList("aBb", "ab", "ε")));

        CFGModel cfgModel = new CFGModel(terminals, nonTerminals, startSymbol, productionRules);
        return new CFGClass(cfgModel);
    }
}
