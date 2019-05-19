JFLAGS = -d .
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	StringUtil.java \
	RSA.java \
	transaction.java \
	block.java \
	chain.java \
	user.java \
	test.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) myPackage/*.class