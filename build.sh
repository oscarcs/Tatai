#!/bin/bash

javac -cp .:src src/game/Main.java -d bin &&
cd src &&
find . -name '*.css' -exec cp --parents \{\} ../bin \; &&
find . -name '*.fxml' -exec cp --parents \{\} ../bin \; &&
find . -name '*.png' -exec cp --parents \{\} ../bin \; &&
cd .. &&
java -ea -cp bin game.Main