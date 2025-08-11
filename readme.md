# Moody: A Modern Android Application

**Moody** - An Android app that enables users to log their daily mood with current weather information,
navigate through mood log history, and see minimal Insights about
weather and mood correlation, such as "You are mostly happy on rainy days."

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Tech Stack & Architecture](#tech-stack--architecture)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Building and Running](#building-and-running)
    - [Running Locally](#running-locally)
    - [Building for Release](#building-for-release)
- [Testing](#testing)
    - [Unit Tests](#unit-tests)
- [Code Style](#code-style)

## Project Overview

Moody is an Android application for logging daily moods alongside current weather information. It allows users to browse their mood history and view simple insights, such as correlations between mood and weather (e.g., "You are mostly happy on rainy days"). The app is built with a modern tech stack, emphasizing clean architecture, Room, and a UI constructed with Jetpack Compose. It leverages Kotlin, Hilt for dependency injection, and Ktor for network operations. The project serves as a showcase for contemporary Android development and testing practices.

## Features

- **Mood Logging:** The User can log mood, and current weather data is logged automatically with mood.
- **Weather Data Loading:** App loads weather data from [OpenWeatherMap Api](https://openweathermap.org/api).
- **Weather Data Caching:** Caches weather data in local database for 1 hour after loading, to reduce network usage and API calls.
- **Mood History:** User can see mood hsitory and filter mood history by weather types, such as (Rainy, Sunny, etc.)
- **Mood Insights:** User can see minimal mood insights based on the logged mood data and weather data, such as "You are mostly happy on Sunny Days."
- **Day/Night Theme Support** User can switch Day/Night themes and their choice is persisted accross sesstions.
## Tech Stack & Architecture

### Architecture

This project follows the **Clean Architecture** principles, which separate the code into three main
layers:

- **Domain Layer:** Contains the core business logic of the application, including use cases,
  entities, and repository interfaces. It is the most independent layer.
- **Data Layer:** Responsible for providing data to the domain layer. It includes implementations of
  the repository interfaces, data sources (both remote and local), and data mapping logic.
- **UI Layer:** The presentation layer of the application. It is responsible for displaying data to
  the user and handling user interactions. It consists of Jetpack Compose screens, ViewModels, and
  UI state management.

### Key Libraries

- **[Kotlin](https://kotlinlang.org/):** The official programming language for Android development.
- **[Jetpack Compose](https://developer.android.com/jetpack/compose):** A modern toolkit for
  building native Android UI via declerative approach.
- **[Hilt](https://dagger.dev/hilt/):** A dependency injection library for Android that reduces the
  boilerplate of doing manual dependency injection in your project.
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel):** A class
  that is responsible for preparing and managing the data for an `Activity` or a `Fragment`.
- **[Room](https://developer.android.com/training/data-storage/room):** A persistence library that
  provides an abstraction layer over SQLite to allow for more robust database access while
  harnessing the full power of SQLite.
- **[Ktor](https://ktor.io/):** A framework for building asynchronous clients and servers in
  connected systems.
- **[Kotlinx.Serialization](https://github.com/Kotlin/kotlinx.serialization):** A multiplatform
  serialization library for Kotlin.
- **[Navigation Compose](https://developer.android.com/jetpack/compose/navigation):** A library that
  allows you to navigate between composables while taking advantage of the Navigation component’s
  infrastructure and features.
- **[Coroutines](https://kotlinlang.org/docs/coroutines-overview.html):** A concurrency design
  pattern that you can use on Android to simplify code that executes asynchronously.
- **[Turbine](https://github.com/cashapp/turbine):** For testing of Kotlin Flows. Not all Flows are tested via Turbine, just in scenarios     where manually testing them is difficult like test immediate state change.

## Getting Started

### Prerequisites

- Android Studio Narwahal - Latest Version
- JDK 11 or later
- Android SDK 34 or later

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/sarmadsohaib/moody.git
   ```
2. Open the project in Android Studio.
3. Let Android Studio download the required Gradle dependencies.

## Building and Running

### Running Locally

To run the app locally, you can either use an Android emulator or a physical device.

1. In Android Studio, select the `app` configuration from the run configurations dropdown.
2. Select the target device (emulator or physical device).
3. Click the "Run" button.

### Building for Release

To build a release version of the app, you can use the following Gradle command:

```bash
./gradlew assembleRelease
```

The signed APK will be located in the `app/build/outputs/apk/release` directory.

## Testing

### Unit Tests

To run the unit tests, execute the following Gradle command:

```bash
./gradlew testDebugUnitTest
```

The test results will be available in the `app/build/reports/tests/testDebugUnitTest` directory.


## Code Style

This project follows the
official [Kotlin style guide](https://kotlinlang.org/docs/coding-conventions.html). The code is also
formatted using the default Android Studio code style settings.
