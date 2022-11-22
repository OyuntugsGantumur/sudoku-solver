import java.util.*;

public class Cage {
    ArrayList<Variable> cages_members;  
    int cage_sum;

    public Cage() {
        this.cage_sum = 0;
        this.cages_members = new ArrayList<>();
    }

    public Cage(int sum) {
        this.cage_sum = sum;
        cages_members = new ArrayList<>();
    }

    public void add_cage_member(Variable var) {
        cages_members.add(var);
    }

    public void print() {
        String str = "";

        for(int i = 0; i < cages_members.size(); i++) {
            str += (cages_members.get(i).row * 9 + cages_members.get(i).col) + "   ";
        }

        str += "sum = " + cage_sum;
        System.out.println(str);
    }
}
