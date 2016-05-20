echo off
cls
set JAVA_HOME=C:\Tools\Java\jdk1.7.0_65
set CLASSPATH=%CLASSPATH%

%JAVA_HOME%\bin\javac -cp "./lib/colt.jar;./lib/concurrent.jar" RandomVariables.java

rem 3pause