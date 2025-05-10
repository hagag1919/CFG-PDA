// package PDA;

// import java.io.*;

// public class PDAMain {
//     static String pdaInput = "input_pda.txt";
//     static String pdaOutput = "output_pda.txt";

//     public static void main(String[] args) throws IOException {
//         try (BufferedReader br = new BufferedReader(new FileReader(pdaInput));
//              BufferedWriter bw = new BufferedWriter(new FileWriter(pdaOutput))) {

//             String line;
//             while ((line = br.readLine()) != null) {
//                 int problemNumber = Integer.parseInt(line.trim());
//                 bw.write(String.valueOf(problemNumber));
//                 bw.newLine();

//                 PDAClass pda = createPDAForProblem(problemNumber);
//                 if (pda == null) {
//                     System.out.println("Invalid problem number: " + problemNumber);
//                     continue;
//                 }

//                 pda.solveProblem(br, bw);
//             }
//         }catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     private static PDAClass createPDAForProblem(int problemNumber) {
//         switch (problemNumber) {
//             case 1:
//                 return problemOnePDA();
//             case 2:
//                 return problemTwoPDA();
//             case 3:
//                 return problemThreePDA();
//             case 4:
//                 return problemFourPDA();
//             case 5:
//                 return problemFivePDA();
//             default:
//                 return null;
//         }
//     }

//     private static PDAClass problemOnePDA() {

//     }

//     private static PDAClass problemTwoPDA() {

//     }

//     private static PDAClass problemThreePDA() {

//     }

//     private static PDAClass problemFourPDA() {

//     }

//     private static PDAClass problemFivePDA() {

//     }
// }
