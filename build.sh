#!/bin/bash

javac -cp .:src src/tatai/Main.java -d bin &&
java -ea -cp bin tatai.Main