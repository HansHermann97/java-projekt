@echo off
set MAINCLASS=dk.HansHermann.movebox.MoveBox

rem Opret outputmappen hvis den ikke findes
if not exist out (
    mkdir out
)

echo Kompilerer alle Java-filer i src...
javac -d out src\dk\HansHermann\movebox\*.java
if ERRORLEVEL 1 (
    echo Kompilering fejlede
    pause
    exit /b
)

echo Main-Class: %MAINCLASS% > manifest.txt
echo. >> manifest.txt

echo Bygger JAR...
jar cfm MoveBox.jar manifest.txt -C out .

del manifest.txt

echo Kører programmet...
java -jar MoveBox.jar

pause
