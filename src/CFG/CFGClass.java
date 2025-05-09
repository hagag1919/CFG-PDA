package CFG;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFGClass {

    CFGModel cfgModel;

    CFGClass(CFGModel cfgModel) {
        this.cfgModel = cfgModel;
    }

    public boolean derive(String currentDerivation, String text) {
        return deriveHelper(currentDerivation, text, new HashSet<>());
    }

    private boolean deriveHelper(String currentDerivation, String text, Set<String> visited) {
        // Base case: If the current derivation matches the text
        if (currentDerivation.equals(text)) {
            return true;
        }

        // Base case: If the current derivation is longer than the text, stop
        if (currentDerivation.length() > text.length()) {
            return false;
        }

        // Avoid revisiting the same derivation
        if (visited.contains(currentDerivation)) {
            return false;
        }
        visited.add(currentDerivation);

        // Find indices of non-terminals in the current derivation
        List<Integer> nonTerminalIndices = new ArrayList<>();
        for (int i = 0; i < currentDerivation.length(); i++) {
            char symbol = currentDerivation.charAt(i);
            if (cfgModel.nonTerminals.contains(symbol)) {
                nonTerminalIndices.add(i);
            }
        }

        // If no non-terminals are left, stop
        if (nonTerminalIndices.isEmpty()) {
            return false;
        }

        // Try all possible productions for each non-terminal
        for (int i : nonTerminalIndices) {
            char symbol = currentDerivation.charAt(i);
            for (String production : cfgModel.productionRules.getOrDefault(symbol, new ArrayList<>())) {
                String newDerivation = currentDerivation.substring(0, i)
                        + (production.equals("ε") ? "" : production)
                        + currentDerivation.substring(i + 1);
                if (deriveHelper(newDerivation, text, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void solveProblem(BufferedReader br, BufferedWriter bw) {
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals("end")) {
                    break;
                }
                String result = derive(String.valueOf(cfgModel.startSymbol), line) ? "accepted" : "not accepted";
                bw.write(result);
                bw.newLine();
            }
            bw.write("x");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
