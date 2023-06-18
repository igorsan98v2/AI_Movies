# AI Movies Shop

![Kotlin](https://img.shields.io/static/v1?label=Language&message=Kotlin&color=blue&style=flat&logo=kotlin) 
![Android](https://img.shields.io/static/v1?label=Platform&message=Android&color=brightgreen&style=flat&logo=android)
![AI-Generated](https://img.shields.io/static/v1?label=Assistance&message=AI-Generated&color=9cf&style=flat)

The AI Movies Shop is a multi-module Android project that showcases movies and their details, utilizing Jetpack Compose and Clean Architecture. The project is purely generated through OpenAI's GPT-4 with the guidance of Ihor Yutsyk.

## Features

- Fetch and display a list of movies including their prices
- Display detailed information about individual movies upon selection
- Uses Kotlin and Jetpack Compose for modern UI development

## Project Modules

- `app`: Serves as the entry point of the application, containing MainActivity, navigation setup, and integration of screens from the feature modules.
- `data`:  Handles the data layer, encompassing Repository implementation along with Remote Data Sources, facilitating movie data fetching and manipulation.
- `domain`: Comprises Use-cases for business logic and the core Entities (Models) of the application.
- `feature-movies`: Dedicated to the UI and supporting logic for the movies list feature, responsible for presenting users with a browsable list of movies.
- `feature-movieDetails`: Focused on UI and associated logic for the movie details feature, allowing users to explore detailed information about a selected movie.

## Tech Stack

- Kotlin with Coroutines and Flow
- Jetpack Compose for UI
- Hilt (Dagger) for Dependency Injection
- Retrofit2 and kotlinx.serialization for API communication
- Clean Architecture with MVI (Model-View-Intent) design pattern

## Pros

- Well-organized project structure based on Clean Architecture principles and Feature Modules
- Modern UI using Jetpack Compose
- Hilt dependency injection simplifies dependency management and testing
- Coil library for efficient image loading
- Efficient API communication with Retrofit2 and Kotlin Serialization
- Asynchronous programming using Kotlin Coroutines and Flow
- MVI architecture for a reactive and unidirectional data flow

## Cons

- As an AI-generated project, some parts might require manual review and adjustments
- The project is created with a specific use-case in mind, which may require modifications for different apps

*This project was fully generated by AI with guidance from Ihor Yutsyk.*
