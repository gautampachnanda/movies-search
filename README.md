# Movies Search Service
This is a service which uses IMDB database to search movies, tv programmes/ series etc

# Pre-requisites
	Spring Boot
	MySql
	Gradle
	Java 8

# Setup
The setup instructions are based on a mac

## Install Java 8
Assuming you have brew installed you can install JKD 8 using
```
brew tap caskroom/versions
brew cask install java8
```

You can verify java using
```
java -version
```

## Install Gradle
Assuming you have brew installed you can install gradle using
```
brew install gradle
```
You can verify version of gradle using

gradle -version

## Setup Gradle wrapper

From the root of the project folder run command
```
gradle wrapper
```

Once completed you can start using gradlew command

## Initialise as gradle project

You can run the following command to intialise as a gradle project. This will create a build.gradle and settings.gradle to configure your build

```
gradle init
```