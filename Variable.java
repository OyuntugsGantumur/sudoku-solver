import java.util.*;

public class Variable{

    int row;
    int col;
    int val;
    ArrayList<Integer> domain;
    ArrayList<Variable> neighbors;

    public Variable(int row, int col) {
        this.row = row;
        this.col = col;
        this.val = 0;
        domain = new ArrayList<>();
        neighbors = new ArrayList<>();

        for(int i = 1; i <= 9; i++) {
            domain.add(i);
        }
    }

    public Variable(int row, int col, int val) {
        this.row = row;
        this.col = col;
        this.val = val;
        domain = new ArrayList<>();
        domain.add(val);
        neighbors = new ArrayList<>();
    }

    public boolean equals(Variable var) {
        if(this == var) return true;
        if(this.row == var.row && this.col == var.col) return true;

        return false;
    }

    public void print_domain() {
        String str = "";

        for(int i = 0; i < domain.size(); i++) {
            str += domain.get(i) + " ";
        }

        System.out.println(str + "\n");
    }
}