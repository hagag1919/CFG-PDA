import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CFGClass {

    CFGModel cfgModel;

    CFGClass(CFGModel cfgModel) {
        this.cfgModel = cfgModel;
    }

    public boolean derive(String currentDerivation, String text) {
        if (currentDerivation.equals(text)) {
            return true;
        }

        if (currentDerivation.length() > text.length()) {
            return false;
        }

        List<Integer> nonTerminalIndices = new ArrayList<>();
        for (int i = 0; i < currentDerivation.length(); i++) {
            char symbol = currentDerivation.charAt(i);
            if (cfgModel.nonTerminals.contains(symbol)) {
                nonTerminalIndices.add(i);
            }
        }

        if (nonTerminalIndices.isEmpty()) {
            return false;
        }

        for (int i : nonTerminalIndices) {
            char symbol = currentDerivation.charAt(i);
            for (String production : cfgModel.productionRules.getOrDefault(symbol, new ArrayList<>())) {
                String newDerivation = currentDerivation.substring(0, i)
                        + (production.equals("ε") ? "" : production)
                        + currentDerivation.substring(i + 1);
                if (derive(newDerivation, text)) {
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
