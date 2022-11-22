## Sudoku Solver

The goal of this project is to solve different kinds of sudoku puzzles 
- [basic 9x9 grid](https://sudoku.com/)
- three overlapping 9x9 puzzles, making 15x15 grid
- [killer sudokus with cages indicating the sum of the values](https://sudoku.com/killer)

with various algorithms and heuristics and to compare the efficiency of the algorithms on each of the sudoku types. Four different kinds of algorithms were implemented to solve the sudoku puzzles: 
- simple backtracking
- backtracking with AC-3 preprocessing
- minimum-remaining-value with least constraining values
- forward checking with minimum-remaining-value

## Constraint Satisfaction Problem
The objective of the agent is to fill in the grid with numbers from 1 to 9, so that each row, column, and box has all digits from 1-9. The sudoku puzzles can be considered as a CSP as each cell in the grid is a Variable whose domain is {1,2,3,4,5,6,7,8,9} if empty at the initial state or {its value} if it was prefilled. Moreover, every variable needs to satisfy the constraints that no two variables in the same row, column, box (or cage in killer sudoku) should have the same value. 

## Input and Output files
Ten puzzles are stored in config_basic, config_overlapping, and config_killer text files for each type of sudokus. The empty grids are represented as zeros and each sudoku is separated by a line. In the overlapping puzzle, the spaces outside the grid are represented with an underline (_). 

The output files are named report_basic, report_killer, and report_overlapping placed in the report_files folder. After the program is run, it prints the initial state and solution of the puzzle and how many values were assigned to solve the puzzle for each algorithm.

## Compilation and Execution

A makefile is created for this project, so the user can run make or make clean (if the user run the program before) in the terminal to compile and run java Main to execute the program.

Further analysis of the algorithms can be found [here](https://docs.google.com/document/d/1qqR1eGb7CcaPVYWyM6tLAGI8RLJa2DsMiFR241m2qis/edit?usp=sharing).
