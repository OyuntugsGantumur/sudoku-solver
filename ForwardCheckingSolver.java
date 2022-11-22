import java.util.*;

public class ForwardCheckingSolver extends SudokuSolver {
    LinkedList<Variable> unassignedVars;
    Stack<Pair> removedValues;

    class Pair {
        Variable var;
        int removed_val;

        public Pair(Variable var, int val) {
            this.var = var;
            this.removed_val = val;
        }
    }

    public ForwardCheckingSolver(String str) {
        super(str);
        unassignedVars = new LinkedList<>(super.unassignedVars);
        removedValues = new Stack<>();

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                getNeighbors(board[i][j]);
            }
        }
    }

    @Override
    public boolean solve() {
        if(!isSolved()) {
            
            unassignedVars.sort((var1, var2) -> this.compare(var1, var2));
            Variable var = unassignedVars.removeFirst();
            int curr_size = removedValues.size();

            for(int i = 0; i < var.domain.size(); i++){
                super.cntr++;
                int value = var.domain.get(i);
                board[var.row][var.col].val = value;

                if(forwardCheck(board[var.row][var.col]) == false) {
                    updateBT(curr_size);
                    curr_size = removedValues.size();

                } else {
                    if(solve()) return true;
                }

                board[var.row][var.col].val = 0;
            }

            unassignedVars.addFirst(var);
            return false;
        }

        super.output = "Solved with FC in " + super.cntr + "\n";
        return true;
    }

    //Reinforces the constraint on neighboring cells
    public boolean forwardCheck(Variable var) {
        ArrayList<Variable> neighbors = var.neighbors;
        
        for(Variable neighbor : neighbors) {

            if(neighbor.domain.contains(var.val) && neighbor.domain.size() == 1) {
                return false;
            } else if (neighbor.domain.contains(var.val)) {
                removedValues.push(new Pair(neighbor, var.val));
                neighbor.domain.remove(Integer.valueOf(var.val));
            }
        }

        return true;
    }

    //Adds back the values that have been removed
    public void updateBT(int curr_size) {
        while(curr_size < removedValues.size()) {
            Pair pair = removedValues.pop();
            pair.var.domain.add(pair.removed_val);
        }
    }

    //Adds the neighbors of the cell to the neighbors list
    public void getNeighbors(Variable var) {
        if(var.neighbors.size() != 0) return;

        for(int i = 0; i < 9; i++) {
            if(i != var.col) var.neighbors.add(board[var.row][i]);
        }

        for(int i = 0; i < 9; i++) {
            if(i != var.row) var.neighbors.add(board[i][var.col]);
        }

        int rn = var.row - var.row % 3;
        int cn = var.col - var.col % 3;
        for(int i = rn; i < rn + 3; i++) {
            for(int j = cn; j < cn + 3; j++) {
                if(i != var.row || j != var.col) var.neighbors.add(board[i][j]);
            }
        }
    }

    public int compare(Variable var1, Variable var2) {
        int legal_values1 = 0, legal_values2 = 0;

        for(int i = 1; i <= 9; i++) {
            if(isValid(var1, i)) legal_values1++;
            if(isValid(var2, i)) legal_values2++;
        }

        if(legal_values1 == legal_values2) { //use degree heuristic
            return Integer.compare(degree_heuristic(var1), degree_heuristic(var2));
        } else {
            return Integer.compare(legal_values1, legal_values2);
        }
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

    @Override
    public boolean isSolved() {
        return unassignedVars.size() == 0;
    }
}