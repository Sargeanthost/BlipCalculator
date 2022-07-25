# Parkour Tool

## What is the point of this tool?

Centralize parkour tools in one place.

## Interface

![Imgur](https://i.imgur.com/2MM5v5K.png)

## Compile from source

Make sure the `JAVA_HOME` environment variable is set to version 11 or greater. In IntelliJ, open the Maven tab 
(default is the right side of the editor) and navigate to `Plugins > compiler > compiler:compile` and run
with `Plugins > javafx > javafx:run`.

## Future features
* Node based gui for plug and play capability 
* Inverse jump solver
* Jump solver

### In progress

* Add general Minecraft parkour functionality
* Add jump check box for blip calculator
* Prevent "Offsets: " from being printed when blip is not possible
* Add slime calculator

### Completed

* Have a blip-chain feature
* Have a label to say if the blip you are attempting is possible or not
* Have entrance co-ordinates print for all coordinates 
* Correct blip calculation (1;1;1 => yes when should be no, doesn't work for starting heights a less than blip bottom
  height)

### Dropped

* Add 1.9 support