JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		Main.java \
		AC3.java \
		BacktrackSolver.java \
		BacktrackSolverAC3.java \
		Cage.java \
		ForwardCheckingSolver.java \
		MRVandLCVsolver.java \
		SudokuSolver.java \
		Variable.java \

default: classes

classes: $(CLASSES:.java=.class)

run: classes
	$(JVM) $(MAIN)

clean:
		$(RM) *.class