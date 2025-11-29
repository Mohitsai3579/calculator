#!/bin/bash
if [ $MODE = "DEVE" ]
  then
      java -jar calculator-1.0-SNAPSHOT-jar-with-dependencies.jar < input.txt
else
      java -jar calculator-1.0-SNAPSHOT-jar-with-dependencies.jar
fi