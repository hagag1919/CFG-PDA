
import java.io.*;
import java.util.*;

public class Main {

    static String cfgInput = "input_cfg.txt";
    static String cfgOutput = "output_cfg.txt";

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(cfgInput));
             BufferedWriter bw = new BufferedWriter(new FileWriter(cfgOutput))) {

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

    private static CFGClass problemOneCFG() {
        // Equal number of a's and b's
        ArrayList<Character> terminals = new ArrayList<>(Arrays.asList('a', 'b'));
        ArrayList<Character> nonTerminals = new ArrayList<>(Arrays.asList('S'));
        Character startSymbol = 'S';

        Map<Character, ArrayList<String>> productionRules = new HashMap<>();
        productionRules.put('S', new ArrayList<>(Arrays.asList("aSb", "bSa", "ab", "ba", "ε")));

        CFGModel cfgModel = new CFGModel(terminals, nonTerminals, startSymbol, productionRules);
        return new CFGClass(cfgModel);
    }

    private static CFGClass problemTwoCFG() {
        ArrayList<Character> terminals = new ArrayList<>(Arrays.asList('a', 'b'));
        ArrayList<Character> nonTerminals = new ArrayList<>(Arrays.asList('S', 'T'));
        Character startSymbol = 'S';
    
        Map<Character, ArrayList<String>> productionRules = new HashMap<>();
        productionRules.put('S', new ArrayList<>(Arrays.asList("aSTb", "T")));
        productionRules.put('T', new ArrayList<>(Arrays.asList("bT", "ε")));
    
        CFGModel cfgModel = new CFGModel(terminals, nonTerminals, startSymbol, productionRules);
        return new CFGClass(cfgModel);
    }

    private static CFGClass problemThreeCFG() {
        ArrayList<Character> terminals = new ArrayList<>(Arrays.asList('a', 'b'));
        ArrayList<Character> nonTerminals = new ArrayList<>(Arrays.asList('S', 'A', 'B', 'E'));
        Character startSymbol = 'S';
    
        Map<Character, ArrayList<String>> productionRules = new HashMap<>();
        productionRules.put('S', new ArrayList<>(Arrays.asList("aSa", "bSb", "aA", "bB", "a", "b")));
        productionRules.put('A', new ArrayList<>(Arrays.asList("bE")));
        productionRules.put('B', new ArrayList<>(Arrays.asList("aE")));
        productionRules.put('E', new ArrayList<>(Arrays.asList("aE", "bE", "ε")));
    
        CFGModel cfgModel = new CFGModel(terminals, nonTerminals, startSymbol, productionRules);
        return new CFGClass(cfgModel);
    }

    private static CFGClass problemFourCFG() {
        ArrayList<Character> terminals = new ArrayList<>(Arrays.asList('a', 'b'));
        ArrayList<Character> nonTerminals = new ArrayList<>(Arrays.asList('S', 'T'));
        Character startSymbol = 'S';

        Map<Character, ArrayList<String>> productionRules = new HashMap<>();
        productionRules.put('S', new ArrayList<>(Arrays.asList("aaaT", "aaa")));
        productionRules.put('T', new ArrayList<>(Arrays.asList("aaTb", "aab")));

        CFGModel cfgModel = new CFGModel(terminals, nonTerminals, startSymbol, productionRules);
        return new CFGClass(cfgModel);
    }

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
