import java.util.*;

public class BacktrackSolver extends SudokuSolver {
    LinkedList<Variable> unassignedVars;

    public BacktrackSolver(String str) {
        super(str);
        unassignedVars = new LinkedList<>(super.unassignedVars);
    }

    @Override
    public boolean solve() {
        if(!isSolved()) {
            Variable var = unassignedVars.removeFirst();

            for(int i = 1; i <= 9; i++) {  
                super.cntr++;   
                if(isValid(var, i)) {
                    board[var.row][var.col].val = i;
                    boolean solved = solve();
                    if(solved) {
                        return true;
                    }

                    board[var.row][var.col].val = 0;
                }
            }

            unassignedVars.addFirst(var);
            return false;
        }

        super.output = "Solved by BT: " + super.cntr + "\n";
        return true;
    }

    @Override
    public boolean isSolved() {
        return unassignedVars.size() == 0;
    }
}
