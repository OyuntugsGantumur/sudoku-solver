import java.util.*;

public class MRVandLCVsolver extends SudokuSolver{
    LinkedList<Variable> unassignedVars;
    AC3 ac3;

    public MRVandLCVsolver(String str) {
        super(str);
        unassignedVars = new LinkedList<>(super.unassignedVars);
        ac3 = new AC3(this.board);
    }

    @Override
    public boolean solve() {
        if(!isSolved()) {
            unassignedVars.sort((var1, var2) -> this.compare(var1, var2));
            Variable var = unassignedVars.removeFirst();
            List<Integer> valid_values = new ArrayList<>();

            for(int i = 1; i <= 9; i++) {
                if(isValid(var, i)) {
                    valid_values.add(i);
                }
            }

            valid_values.sort((val1, val2) -> this.compare_LCValues(val1, val2, var));

            for(int value : valid_values) {
                board[var.row][var.col].val = value;
                super.cntr++;
                if(solve()) {
                    return true;
                }

                board[var.row][var.col].val = 0;
            }

            unassignedVars.addFirst(var);
            return false;
        }

        super.output = "Solved by MRV + LCV: " + super.cntr + "\n";
        return true;
    }

    @Override
    public boolean isSolved() {
        return unassignedVars.size() == 0;
    }

    public int compare(Variable var1, Variable var2) {
        int comp = Integer.compare(var1.domain.size(), var2.domain.size());

        if(comp == 0) return Integer.compare(degree_heuristic(var1), degree_heuristic(var2));

        return comp;
    }

    //Returns the degree heuristic for a given cell
    public int degree_heuristic(Variable var1) {
        int degree_num = 0;

        for(Variable comp: unassignedVars) {
            if(connected(comp, var1)) 
                degree_num++;
        }

        return degree_num;
    }

    //Checks if two variables are neighbors to each other
    public boolean connected(Variable var1, Variable var2) {
        if((var2.row == var1.row || var2.col == var1.col || 
            ((var2.row/3 == var1.row/3) && (var2.col/3 == var1.col/3))) && !var2.equals(var1)) 
            return true;

        return false;
    }

    //Returns the least constraining value
    public int LCValue(Variable var) {
        int num = 0;
    
        for(Variable comp: unassignedVars) {
            if(connected(var, comp)) {
                for(int i = 1; i <= 9; i++) {
                    if(isValid(comp, i)) {
                        num++;
                    }
                }
            }
        }
        
        return num;
    }

    //Determines the least constraining value for the given cell and 2 values
    public int compare_LCValues(int value1, int value2, Variable currVar) {
        currVar.val = value1;
        int cnt1 = LCValue(currVar);

        currVar.val = value2;
        int cnt2 = LCValue(currVar);
        currVar.val = 0;

        return Integer.compare(cnt2, cnt1);
    }
}
