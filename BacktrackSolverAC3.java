import java.util.*;

public class BacktrackSolverAC3 extends SudokuSolver {
    LinkedList<Variable> unassignedVars;
    AC3 ac3;

    public BacktrackSolverAC3(String str) {
        super(str);
        unassignedVars = new LinkedList<>(super.unassignedVars);
        ac3 = new AC3(this.board);
    }

    @Override
    public boolean solve() {
        if(!isSolved()) {
            Variable var = unassignedVars.removeFirst();
            ArrayList<Integer> pos_values = var.domain;

            for(int i = 0; i < pos_values.size(); i++) {
                int val = pos_values.get(i);
                super.cntr++;
                
                if(isValid(var, val)) {
                    board[var.row][var.col].val = val;
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

        super.output = "Solved by BT with AC3: " + super.cntr + "\n";
        return true;
    }

    @Override
    public boolean isSolved() {
        return unassignedVars.size() == 0;
    }
}