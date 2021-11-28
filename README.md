## GOW (game of words)
Sample project, that implement's a simple fizzbuzz game

| Config screen | Result screen |
|--|--|
| <img src="https://github.com/medhdj/GameOfWords/blob/main/docs/config_screen.jpg?raw=true" width="300" style="inline"> | <img src="https://github.com/medhdj/GameOfWords/blob/main/docs/result_screen.jpg?raw=true" width="300" style="inline"> |


#### How to play
- Fill in the desired configuration and click on "GO" button to see the result
> Long click on the "GO" button to fill in automatically the form with default fizzbuzz config

## Project architecture
The choice of the architecture implemented in this project is based on my previous experiences on different types of projects, I mixed concepts of clean architecture and modularization to make the exploration and understanding of the code as easy as possible, also giving the possibility to extend and evolve the project beyond the constraint of a specific platform.
With this architecture and the help of  Kotlin KMM, we can easily migrate and share all our business logic and  the data sources between iOS and Android.

<img src="https://github.com/medhdj/GameOfWords/blob/main/docs/project_arch.png?raw=true" width="500" style="inline">

**App layer:**
 Hosts all the android platform specifics like views, view models, livedata, this layer is also responsible for dependency injection using hilt

**Business:**
In this layer we find the different use cases and models that contribute to fulfilling the business requirements, this layer also defines the contracts that the repositories must follow.

**Data:** This layer is responsible for fetching data from the possible sources, in this app it's generating the data locally based on the fizzbuzz algorithm

**Core:** helper layer containing utilities and extensions

## Technologies:

- clean architecture + modularization
- MVVM
- Kotlin
- Hilt
- Coroutines
- Paging library 3
- .... see [BuildSrc](https://github.com/medhdj/GameOfWords/blob/main/buildSrc/src/main/kotlin/Dependencies.kt) for more