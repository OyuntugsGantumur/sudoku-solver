import java.util.LinkedList;
import java.util.*;

public class AC3 {

    public class Arc {
        Variable var1;
        Variable var2;

        public Arc(Variable v1, Variable v2) {
            this.var1 = v1;
            this.var2 = v2;
        }
    }

    Queue<Arc> arc_list;
    Variable[][] board;

    public AC3(Variable[][] board) {
        this.board = board;
        arc_list = new LinkedList<>();
        
        for(int i = 0; i < 9; i++) {
            add_row_arcs(i);
            add_col_arcs(i);

            for(int j = 0; j < 9; j++) {
                add_box_arcs(i, j);
            }
        }

        run_ac3();
    }

    public boolean run_ac3() {
        while(!arc_list.isEmpty()) {
            Arc arc = arc_list.remove();
            
            if(revise(arc)) {
                if(arc.var1.domain.size() == 0) return false;

                for(int m = 0; m < 9; m++) {
                    if(arc.var1.row != m && board[m][arc.var1.col] != arc.var2) {
                        arc_list.add(new Arc(board[m][arc.var1.col], arc.var1));
                    }
                    if(arc.var1.col != m && board[arc.var1.row][m] != arc.var2) {
                        arc_list.add(new Arc(board[arc.var1.row][m], arc.var1));
                    }
                }

                int rn = arc.var1.row - arc.var1.row % 3;
                int cn = arc.var1.col - arc.var1.col % 3;
                for(int rownum = rn; rownum < rn + 3; rownum++) {
                    for(int colnum = cn; colnum < cn + 3; colnum++) {
                        if (rownum != arc.var1.row && colnum != arc.var1.col && board[rownum][colnum] != arc.var2) {
                            arc_list.add(new Arc(board[rownum][colnum], arc.var1));
                        }
                    }
                }
            }

        }

        return true;
    }

    public boolean revise(Arc arc) {
        boolean revised = false;

        for(int k = 0; k < arc.var1.domain.size(); k++) { 
            Integer i = arc.var1.domain.get(k);

            for(Integer j : arc.var2.domain) {
                if(arc.var2.val != 0 && i == j) {
                    arc.var1.domain.remove(i);
                    revised = true;
                }
            }
        }

        return revised;
    }

    public void add_row_arcs(int row) {
        for(int i = 0; i < 9; i++) {
            for(int m = i + 1; m < 9; m++) {
                Arc one = new Arc(board[row][i], board[row][m]);
                Arc two = new Arc(board[row][m], board[row][i]);
                arc_list.add(one);
                arc_list.add(two);
            }
        }
    }

    public void add_col_arcs(int col) {
        for(int i = 0; i < 9; i++) {
            for(int m = i + 1; m < 9; m++) {
                arc_list.add(new Arc(board[i][col], board[m][col]));
                arc_list.add(new Arc(board[m][col], board[i][col]));
            }
        }
    }

    public void add_box_arcs(int row, int col) {
        int rn = row - row % 3;
        int cn = col - col % 3;
        ArrayList<Variable> list = new ArrayList<>();

        for(int i = rn; i < rn + 3; i++) {
            for(int j = cn; j < cn + 3; j++) {
                list.add(board[i][j]);
            }
        }

        for(int i = 0; i < list.size(); i++) {
            for(int j = i + 1; j < list.size(); j++) {
                arc_list.add(new Arc(list.get(i), list.get(j)));
                arc_list.add(new Arc(list.get(j), list.get(i)));
            }
        }
    }

    public void printBoard() {
        String str = "";

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                str += board[i][j].val;
                str += " ";
            }
            str += "\n";
        }

        System.out.println(str);
    }
}