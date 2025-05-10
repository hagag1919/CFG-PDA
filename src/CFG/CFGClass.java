package CFG;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CFGClass {

    CFGModel cfgModel;

    CFGClass(CFGModel cfgModel) {
        this.cfgModel = cfgModel;
    }

   public boolean derive(String currentDerivation, String text) {
    return deriveHelper(currentDerivation, text, 0, 0);
}

private boolean deriveHelper(String derivation, String text, int derPtr, int textPtr) {
    while (derPtr < derivation.length()) {
        char derChar = derivation.charAt(derPtr);

        if (cfgModel.terminals.contains(derChar)) {
            if (textPtr >= text.length() || text.charAt(textPtr) != derChar) {
                return false;
            }
            derPtr++;
            textPtr++;
        } else if (cfgModel.nonTerminals.contains(derChar)) {
            for (String production : cfgModel.productionRules.getOrDefault(derChar, new ArrayList<>())) {
                String newDerivation = (production.equals("ε") ? "" : production)
                        + derivation.substring(derPtr + 1);

                if (deriveHelper(newDerivation, text, 0, textPtr)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    return textPtr == text.length();
}


    public void solveProblem(BufferedReader br, BufferedWriter bw) throws IOException {
        String line;

        while ((line = br.readLine()) != null) {
            if (line.equals("end")) {
                bw.write("x");
                bw.newLine();
                break;
            } else {
                boolean derived = false;

                for (String production : cfgModel.productionRules.get(cfgModel.startSymbol)) {
                    if (derive(production, line)) {
                        derived = true;
                        break;
                    }
                }

                bw.write(derived ? "accepted" : "not accepted");
                bw.newLine();
            }
        }
    }
}
