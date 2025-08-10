
# Moody: A Modern Android Application

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/sarmadsohaib/moody)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![API](https://img.shields.io/badge/API-26%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=26)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.6.0-blue.svg?logo=jetpack-compose)](https://developer.android.com/jetpack/compose)

**Moody** is a demonstration of a modern, offline-first Android application built with the latest technologies and best practices. It serves as a template and a reference for building robust, scalable, and maintainable Android apps.

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
  - [UI Tests](#ui-tests)
- [Code Style](#code-style)
- [Contributing](#contributing)
- [Troubleshooting](#troubleshooting)

## Project Overview

Moody is a simple yet comprehensive application that showcases how to build a modern Android app using a Clean Architecture approach. It demonstrates the integration of various Jetpack libraries and other popular open-source tools to create a feature-rich and resilient user experience. The app fetches data from a remote API, stores it locally for offline access, and presents it to the user in a clean and intuitive UI.

## Features

- **Offline-first:** The app is designed to work seamlessly with or without a network connection.
- **Clean Architecture:** The codebase is organized into `data`, `domain`, and `ui` layers, promoting separation of concerns and testability.
- **Modern UI:** The UI is built entirely with Jetpack Compose, providing a declarative and reactive approach to UI development.
- **Dependency Injection:** Hilt is used for managing dependencies, making the code more modular and easier to test.
- **Asynchronous Operations:** Kotlin Coroutines are used for managing background threads and asynchronous tasks.

## Tech Stack & Architecture

### Architecture

This project follows the **Clean Architecture** principles, which separates the code into three main layers:

- **Domain Layer:** Contains the core business logic of the application, including use cases, entities, and repository interfaces. It is the most independent layer.
- **Data Layer:** Responsible for providing data to the domain layer. It includes implementations of the repository interfaces, data sources (both remote and local), and data mapping logic.
- **UI Layer:** The presentation layer of the application. It is responsible for displaying data to the user and handling user interactions. It consists of Jetpack Compose screens, ViewModels, and UI state management.

### Key Libraries

- **[Kotlin](https://kotlinlang.org/):** The official programming language for Android development.
- **[Jetpack Compose](https://developer.android.com/jetpack/compose):** A modern toolkit for building native Android UI.
- **[Hilt](https://dagger.dev/hilt/):** A dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel):** A class that is responsible for preparing and managing the data for an `Activity` or a `Fragment`.
- **[Room](https://developer.android.com/training/data-storage/room):** A persistence library that provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
- **[Ktor](https://ktor.io/):** A framework for building asynchronous clients and servers in connected systems.
- **[Kotlinx.Serialization](https://github.com/Kotlin/kotlinx.serialization):** A multiplatform serialization library for Kotlin.
- **[Navigation Compose](https://developer.android.com/jetpack/compose/navigation):** A library that allows you to navigate between composables while taking advantage of the Navigation component’s infrastructure and features.
- **[Coroutines](https://kotlinlang.org/docs/coroutines-overview.html):** A concurrency design pattern that you can use on Android to simplify code that executes asynchronously.

## Getting Started

### Prerequisites

- Android Studio Iguana | 2023.2.1 or later
- JDK 11 or later
- Android SDK 34 or later

### Installation

1.  Clone the repository:
    ```bash
    git clone https://github.com/sarmadsohaib/moody.git
    ```
2.  Open the project in Android Studio.
3.  Let Android Studio download the required Gradle dependencies.

## Building and Running

### Running Locally

To run the app locally, you can either use an Android emulator or a physical device.

1.  In Android Studio, select the `app` configuration from the run configurations dropdown.
2.  Select the target device (emulator or physical device).
3.  Click the "Run" button.

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

### UI Tests

To run the UI tests, you need a connected device or an emulator. Execute the following Gradle command:

```bash
./gradlew connectedDebugAndroidTest
```

The test results will be available in the `app/build/reports/androidTests/connected` directory.

## Code Style

This project follows the official [Kotlin style guide](https://kotlinlang.org/docs/coding-conventions.html). The code is also formatted using the default Android Studio code style settings.

## Contributing

Contributions are welcome! If you have any ideas, suggestions, or bug reports, please open an issue or submit a pull request.

## Troubleshooting

- **Build fails with a "Could not resolve all files for configuration" error:** This usually means that some dependencies could not be downloaded. Make sure you have a stable internet connection and try to sync the project with Gradle files again.
- **App crashes on launch:** Check the Logcat in Android Studio for any error messages. The most common cause is a missing API key or a network-related issue.
