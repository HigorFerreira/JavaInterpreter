# Java language interpreter

A college project that implements a simple language interpreter to intruduce the knowledge of compilers and how they works.
This matter is taught by **Claudio Martins Garcia** at **Pontifícia Universidade Católica de Goiás** \
Date: **30/08/2021**

####Student
Higor Ferreira Alves Santos

####Resume
This is a college project that challenges students in building a language interpreter that interprets a language specified by the class teacher. \
The specification could been found in **docs/Interpretador.docx**


####Compile & run
This code was developed using **openjdk 14.0.2**. To compile it, it needs the Java Development Kit to be installed in the machine. Once it's installed, run:

```bash
javac *.java -d .
```
This command will compile the entire project in java bytecodes to be interpreted by JVM.
When compiled, it needs to pass the file that would be interpreted. This file have to be writen under specifications defined by class teacher. \
There are two ways in which the file can be passed: by command line, or when the program is called. There are an example of file called **input.txt** on the root folder, this one could be passed by command line using:
```bash
java Interpreter ./input.txt
```
If the file isn't passed on command line, the program will prompt the user for the file in which he want to be interpreted
```bash
java Interpreter
```