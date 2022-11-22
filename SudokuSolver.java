import java.util.*;

public abstract class SudokuSolver {
    Variable[][] board;
    Collection<Variable> unassignedVars;
    String output = "";
    int cntr = 0;
    ArrayList<Cage> cages;
    final static int SIZE = 9;

    public SudokuSolver(String str) {
        board = new Variable[SIZE][SIZE];        
        unassignedVars = new LinkedList<>();
        createBoard(str);;
    }

    //Given a string of values, creates a 2-dimensional array
    //representing the grid of sudoku
    public void createBoard(String str) {
        int cnt = 0;

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                char c = str.charAt(cnt);

                if(c == '0') {
                    Variable var = new Variable(i, j);
                    board[i][j] = var;
                    unassignedVars.add(var);
                } else {
                    Variable var = new Variable(i, j, c - '0');
                    board[i][j] = var;
                }

                cnt++;
            }
        }

        if(str.length() > 81) {
            createCages(str.substring(cnt));
        }
    }

    //If killer sudoku, create the cages and puts variables in it
    public void createCages(String str) {
        cages = new ArrayList<>();
        String[] cage_input = str.split(" & ");

        for(int i = 0; i < cage_input.length; i++) {
            Cage cage = new Cage();
            String[] nums = cage_input[i].split(", ");

            for(int j = 0; j < nums.length-1; j++) {
                int num = Integer.valueOf(nums[j]);
                int row = num / 9;
                int col = num % 9;
                Variable var = board[row][col];
                cage.add_cage_member(var);
            }

            cage.cage_sum = Integer.valueOf(nums[nums.length - 1]);
            cages.add(cage); 
        }
    }

    public abstract boolean solve();

    public abstract boolean isSolved();

    //Checks if the value is valid in the row
    public boolean isRow(Variable var, int val) {
        for(int i = 0; i < SIZE; i++) {
            if(i != var.row && board[i][var.col].val == val) return false;
        }

        return true;
    }

    //Checks if the value is valid in the column
    public boolean isCol(Variable var, int val) {
        for(int i = 0; i < SIZE; i++) {
            if(i != var.col && board[var.row][i].val == val) return false;
        }

        return true;
    }

    //Checks if the value is valid in the box
    public boolean isBox(Variable var, int val) {
        int cn = var.col - var.col % 3;
        int rn = var.row - var.row % 3;

        for(int i = rn; i < rn + 3; i++) {
            for(int j = cn; j < cn + 3; j++) {
                if(i != var.row && j != var.col && board[i][j].val == val) return false;
            }
        }

        return true;
    }

    //Checks if the value is valid in the cage
    //Returns true if not a killer sudoku
    public boolean isCage(Variable var, int val) {
        if(cages == null) return true;

        for(int i = 0; i < cages.size(); i++) {
            if(cages.get(i).cages_members.contains(var)) {

                int temp_sum = 0;
                // String temp = "";
                for(int j = 0; j < cages.get(i).cages_members.size(); j++) {
                    Variable curr = cages.get(i).cages_members.get(j);

                    temp_sum += curr.val;

                }

                if(temp_sum + val > cages.get(i).cage_sum) return false;
            }
        }

        return true;
    }

    //Checks the validity of value in row, column, box and cage
    public boolean isValid(Variable var, int val) {
        return isRow(var, val) && isCol(var, val) && isBox(var, val) && isCage(var, val);
    }

    //Prints the board
    public String printBoard() {
        String str = "";

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                str += board[i][j].val;
                str += " ";
            }

            str += "\n";
        }

        // System.out.println(str);
        return str;
    }
}