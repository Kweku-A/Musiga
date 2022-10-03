# Musiga
This is a demo app developed to demonstrate the loading of music feed from a network endpoint

## Tech Stack
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
- Ktlint - for testing


##Build 
Development was done using Android Studio Preview (Falmingo) hence using the gradle plugin version 8.0.0-alpha02. To Run this on a lower/more stable version change the gradle version

## Info
This is a one screen demo app and shows the integration of an animated toolbar with search component. A custom implementation with MotionLayout to anumate the toolbar on scroll.

##Structure
The app is structured to have 3 basic layers
- Domain
- Data
- Presentation
The app is modularised so as to make it more maintainable and testable.

##Test
Not every aspect is tested due to time constraint. But tests have been added as much as possible 

