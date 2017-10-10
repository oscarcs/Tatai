#!/bin/bash

javac -cp .:src src/game/Main.java -d bin &&
cd src &&
find . -name '*.css' -o -name '*.fxml' -o -name '*.png' -exec cp --parents \{\} ../bin \; &&
cd .. &&
java -ea -cp bin game.Main