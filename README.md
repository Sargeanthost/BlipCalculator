# Parkour Tool

## What is the point of this tool?

Centralize parkour tools in one place.

## Interface

![Imgur](https://i.imgur.com/2MM5v5K.png)

## Compile from source

The project can be run from a terminal. Make sure `JAVA_HOME` is set to 11 or greater, and run `mvn clean javafx:run` in
the same directory. Alternatively in IntelliJ, open `Plugins > compiler > compiler:compile` and run
with `Plugins > javafx > javafx:run`.

## Future features

~~Print the co-ordinates for entrances after a blip is completed~~

~~Have a blip-chain feature~~

~~Have a label to say if the blip you are attempting is possible or not~~

~~Have entrance co-ordinates print for starting heights above Y=255~~

~~Add 1.9 support~~ No longer working on

Add general Minecraft parkour functionality

Correct blip calculation (1;1;1 => yes when should be no)