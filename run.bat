@echo off
if not exist bin mkdir bin
javac -d bin src\com\edutrack\model\*.java src\com\edutrack\exception\*.java src\com\edutrack\repository\*.java src\com\edutrack\service\*.java src\com\edutrack\test\*.java src\com\edutrack\EduTrackApp.java
if errorlevel 1 exit /b 1
java -cp bin com.edutrack.EduTrackApp
