import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //BASIC SUDOKU
        String[] inputs = new String[10];
        try {
            FileWriter writer = new FileWriter("report_basic.txt");
            read_config_basic(inputs);
            
            for(int i = 0; i < inputs.length; i++) {

                writer.write("\nPUZZLE " + (i + 1) + ": \n" + puzzle_to_array(inputs[i]) + "\n");

                SudokuSolver ss = new BacktrackSolver(inputs[i]);
                ss.solve();
                writer.write(ss.output);
    
                SudokuSolver ss_ac3 = new BacktrackSolverAC3(inputs[i]);
                ss_ac3.solve();
                writer.write(ss_ac3.output);
    
                SudokuSolver mrv = new MRVandLCVsolver(inputs[i]);
                mrv.solve();
                writer.write(mrv.output);
                
                SudokuSolver fc = new ForwardCheckingSolver(inputs[i]);
                fc.solve();
                writer.write(fc.output);
            }

            writer.close();
        } catch (IOException i) {
            System.out.println("File not found! \n");
        }


        //OVERLAPPING SUDOKU
        String[][] puzzle = new String[10][3];
        read_overlapping_puzzle(puzzle);
        
        try {
            FileWriter writer_overlap = new FileWriter("report_overlapping.txt");
            
            for(int i = 0; i < puzzle.length; i++) {
                writer_overlap.write("\nPUZZLE " + (i+1) + " solution:  \n");

                SudokuSolver ss1 = new BacktrackSolver(puzzle[i][0]);
                SudokuSolver ss2 = new BacktrackSolver(puzzle[i][1]);
                SudokuSolver ss3 = new BacktrackSolver(puzzle[i][2]);
                ss1.solve();
                ss2.solve();
                ss3.solve();
                writer_overlap.write("\n" + combine_overlap(ss1, ss2, ss3) + "\n");
                writer_overlap.write("Solved by BT in " + (ss1.cntr+ss2.cntr + ss3.cntr) + "\n");

                SudokuSolver ss1_ac3 = new BacktrackSolverAC3(puzzle[i][0]);
                SudokuSolver ss2_ac3 = new BacktrackSolverAC3(puzzle[i][1]);
                SudokuSolver ss3_ac3 = new BacktrackSolverAC3(puzzle[i][2]);
                ss1_ac3.solve();
                ss2_ac3.solve();
                ss3_ac3.solve();
                writer_overlap.write("Solved by BT with AC3 in " + (ss1_ac3.cntr+ss2_ac3.cntr + ss3_ac3.cntr) + "\n");

                SudokuSolver ss1_mrv = new MRVandLCVsolver(puzzle[i][0]);
                SudokuSolver ss2_mrv = new MRVandLCVsolver(puzzle[i][1]);
                SudokuSolver ss3_mrv = new MRVandLCVsolver(puzzle[i][2]);
                ss1_mrv.solve();
                ss2_mrv.solve();
                ss3_mrv.solve();
                writer_overlap.write("Solved by MRV and LCV in " + (ss1_mrv.cntr+ss2_mrv.cntr + ss3_mrv.cntr) + "\n");

                SudokuSolver ss1_fc = new ForwardCheckingSolver(puzzle[i][0]);
                SudokuSolver ss2_fc = new ForwardCheckingSolver(puzzle[i][1]);
                SudokuSolver ss3_fc = new ForwardCheckingSolver(puzzle[i][2]);
                ss1_fc.solve();
                ss2_fc.solve();
                ss3_fc.solve();
                writer_overlap.write("Solved by FC in " + (ss1_fc.cntr+ss2_fc.cntr + ss3_fc.cntr) + "\n");   
            }

            writer_overlap.close();

        } catch (IOException i) {
            System.out.println("File not found!\n");
        }


        //KILLER SUDOKU
        String[] killer_puzzle = new String[10];
        read_killer_config(killer_puzzle);

        try {
            FileWriter killer_writer = new FileWriter("report_killer.txt");

            for(int i = 0; i < killer_puzzle.length; i++) {
                killer_writer.write("\nPUZZLE " + (i + 1) + "\n" + puzzle_to_array(killer_puzzle[i].substring(0, 81)) + "\n");

                SudokuSolver killer_bt = new BacktrackSolver(killer_puzzle[i]);
                killer_bt.solve();
                killer_writer.write(killer_bt.output);

                SudokuSolver killer_ac3 = new BacktrackSolverAC3(killer_puzzle[i]);
                killer_ac3.solve();
                killer_writer.write(killer_ac3.output);

                SudokuSolver killer_mrv = new MRVandLCVsolver(killer_puzzle[i]);
                killer_mrv.solve();
                killer_writer.write(killer_mrv.output);

                SudokuSolver killer_fc = new ForwardCheckingSolver(killer_puzzle[i]);
                killer_fc.solve();
                killer_writer.write(killer_fc.output);
            }

            killer_writer.close();

        } catch (IOException i) {
            System.out.println("File not found!");
        }
    }

    public static void read_config_basic(String[] str) {
        String puzzle = "";

        try {
            File file = new File("config_basic.txt");
            Scanner scnr = new Scanner(file);

            for(int i = 0; i < str.length; i ++) {
                puzzle = "";

                for(int j = 0; j < 9; j++) {
                    puzzle += scnr.nextLine();
                }
                scnr.nextLine();
                str[i] = puzzle;
            }

            scnr.close();

        } catch(IOException i) {
            System.out.println("File not found!\n");
        }
    }

    public static String puzzle_to_array (String str) {
        String ret = "";

        for(int i = 0; i < str.length(); i++) {
            ret += str.charAt(i);
            ret += " ";
            if(i % 9 == 8) ret += "\n";
        }

        return ret;
    }

    public static void read_overlapping_puzzle(String[][] puzzle) {

        try {
            File file = new File("config_overlapping.txt");
            Scanner scnr = new Scanner(file);

            for(int num = 0; num < puzzle.length; num++) {
                String one = "";
                String two = "";
                String three = "";

                for(int i = 0; i < 15; i++) {
                    String temp = scnr.nextLine();
    
                    for(int j = 0; j < 15; j++) {
                        if( i < 9 && j < 9) {
                            one += temp.charAt(j);
                        }
                        if (i > 2 && i < 12 && j > 2 && j < 12) {
                            two += temp.charAt(j);
                        }
                        if (i > 5 && j > 5) {
                            three += temp.charAt(j);
                        
                        }
                    }
                }

                puzzle[num][0] = one;
                puzzle[num][1] = two;
                puzzle[num][2] = three;
                scnr.nextLine();
            }
            scnr.close();

        } catch (IOException i) {
            System.out.println("File not found! \n");
        }   
    }

    public static String combine_overlap(SudokuSolver s1, SudokuSolver s2, SudokuSolver s3) {
        String ret = "";

        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {                
                if(i < 9 ) {
                    
                    if(j < 9) ret += s1.board[i][j].val;
                    if(i < 3 && j > 8) ret += '_'; 
                    if(i >= 3 && j > 8 && j < 12) ret += s2.board[i-3][j-3].val;
                    if (j > 11) {
                        if(i > 5) ret += s3.board[i-6][j-6].val;
                        else if(i > 2) ret += "_";
                    } 
                
                } else {
                    
                    if(i < 12){
                        if(j < 3) ret += "_";
                        else if(j < 6) ret += s2.board[i-3][j-3].val;
                        else ret += s3.board[i-6][j-6].val;
                    } else {
                        if(j < 6) ret += "_";
                        else ret += s3.board[i-6][j-6].val;
                    }
                }
            }
            ret += "\n";
        }
        return ret;
    }

    public static void read_killer_config(String[] puzzle) {

        try {
            File file = new File("config_killer.txt");
            Scanner scnr = new Scanner(file);

            for(int j = 0; j < puzzle.length; j++) {
                puzzle[j] = "";

                for(int i = 0; i < 9; i++) {
                    puzzle[j] += scnr.nextLine();
                }
    
                scnr.nextLine();
                String temp = scnr.nextLine();

                while(!temp.contains("_____")) {
                    puzzle[j] += temp + " & ";
                    temp = scnr.nextLine();
                }
            }

            scnr.close();
        
        } catch(IOException i) {
            System.out.println("File not found!\n");
        }
    }
}
