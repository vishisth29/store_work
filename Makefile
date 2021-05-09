JCC = javac
JCR = java
JFLAGS = -g

all:
	$(JCC) $(JFLAGS) *.java

run:
	$(JCR) store

clean:
	$(RM) *.class