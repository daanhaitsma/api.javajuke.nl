# Java Juke Backend
The magic of a jukebox in the twenty-first century!


## Installation
These steps will guide you through installing JavaJuke on a Raspberry Pi. This has been tested on a Raspberry Pi 3, Model B running [Raspbian Stretch Lite](https://www.raspberrypi.org/downloads/raspbian/). This guide assumes that Java and Maven have already been installed on the machine.

Firstly, there is a handful of dependencies that needs to be installed. These are git and mysql. Installation guides for these are available on the internet, but in general it is along these lines:
```
$ sudo apt-get install git
$ sudo apt-get install mysql-server mysql-client
$ npm install -g polymer-cli
```

The final required installation is a modified version of [JACo MP3 Player](http://jacomp3player.sourceforge.net/). The compiled jar of this version is available [here](https://github.com/daanhaitsma/javajuke-mp3-player/blob/master/lib/javajuke-mp3-player-0.1.2.jar). Place this file in the `jre/lib/ext` folder.
* With jdk version 1.8.0_131 on Windows the complete path would be `C:\Program Files\Java\jdk1.8.0_131\jre\lib\ext\javajuke-mp3-player-0.1.2.jar`
* With jdk version 1.8.0_131 on Raspbian Stretch Lite the complete path would be `/usr/lib/jvm/jdk-8-oracle-arm32-vfp-hflt/jre/lib/ext/javajuke-mp3-player-0.1.2.jar`

After all the depencencies have been installed, the application itself can be installed. This is done using git.

```
$ git clone https://github.com/daanhaitsma/api.javajuke.nl.git
```
The backend requires a bit of configuration. A file named `application.properties` must be created in the `src/main/resources` directory. An example of this file is available.

## Running locally
Locally, the backend project can be run from either the command line or within IntelliJ. The frontend is served using the Polymer cli.

### Run it from the command line:
```
$ mvn clean package cargo:run
```

### Run it from IntelliJ:
1. Press CTRL + E and select Maven
2. Expand the Lifecycle and Plugins folder
3. Run clean (double click it) in the Lifecycle folder
4. Run package in the Lifecycle folder
5. Expand cargo in the Plugins folder and run cargo:run
