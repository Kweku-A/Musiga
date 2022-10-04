# Musiga
This is a demo app developed to demonstrate the loading of music feed from a network endpoint

## Tech Stack and libraries
- Kotlin 
- Jetpack compose
- Hilt - for dependency injection
- ViewModel - managing UI related data.
- Kotlin Flows
- Coroutines
- Ktor - for network calls
- Moshi - A JSON library 
- Room - for local data storage
- Paging 3 - for pagination
- Ktlint - static code analysis


## Build 
Development was done using Android Studio Preview (Falmingo) hence using the gradle plugin version '8.0.0-alpha02'. To Run this on a lower/more stable version change the gradle version

## Info
- This is a one screen demo app and shows the integration of an animated toolbar with search component, using a an implementation of MotionLayout to animate the toolbar on scroll.
- Delay has been added to network calls so as to show activity progress to the user

## Structure
The app is structured to have 3 basic/main layers
- Domain
- Data
- Presentation

The app is modularised so as to make it more maintainable and testable.

## Test
Not every aspect is tested due to time constraint. But tests have been added as much as possible 

