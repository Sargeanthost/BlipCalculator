# Parkour Tool

## What is the point of this tool?

Centralize parkour tools in one place.

## Interface

![Imgur](https://i.imgur.com/2MM5v5K.png)

## Compile from source

Make sure the `JAVA_HOME` environment variable is set to version 11 or greater. In IntelliJ, open the Maven tab 
(default is the right side of the editor) and navigate to `Plugins > compiler > compiler:compile` and run
with `Plugins > javafx > javafx:run`.

To create and exe, make sure [wix tooling](https://wixtoolset.org/) is installed and run `Plugins > javafx > javafx:jlink` and then `Parkour Tool > jpackage > jpackage:jpackage`.

## Prebuilt binaries
There are prebuilt binaries for Windows (and Linux soon). Simply download `ParkourTool-x-x-x.exe` and run it. It 
currently installs into `C:\Program Files\ParkourTool`. 

## Future features
* Node based gui for plug and play capability 
* Inverse jump solver
* Jump solver

### In progress

* Add general Minecraft parkour functionality
* Add jump check box for blip calculator
* Prevent "Offsets: " from being printed when blip is not possible

### Completed

* Have a blip-chain feature
* Have a label to say if the blip you are attempting is possible or not
* Have entrance co-ordinates print for all coordinates 
* Correct blip calculation (1;1;1 => yes when should be no, doesn't work for starting heights a less than blip bottom
  height)
* Add slime calculator

### Dropped

* Add 1.9 support