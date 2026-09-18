#!/usr/bin/env bash
set -e
mkdir -p bin
javac -d bin src/com/edutrack/model/*.java src/com/edutrack/exception/*.java src/com/edutrack/repository/*.java src/com/edutrack/service/*.java src/com/edutrack/test/*.java src/com/edutrack/EduTrackApp.java
if [ "$1" = "test" ]; then
  java -cp bin com.edutrack.test.EduTrackTestSuite
else
  java -cp bin com.edutrack.EduTrackApp
fi
