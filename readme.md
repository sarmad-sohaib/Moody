# Moody: A Modern Android Application

**Moody** - An Android app that enables users to log their daily mood with current weather information,
navigate through mood log history, and see minimal Insights about
weather and mood correlation, such as "You are mostly happy on rainy days."

## Table of Contents

- [Project Overview](#project-overview)
- [Assumptions](#assumptions)
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
- [Continuous Integration (CI) Workflows](#continuous-integration-ci-workflows)
    - [Pre-Merge Checks](#pre-merge-checks)
    - [Debug APK Build (on `working` branch)](#debug-apk-build-on-working-branch)
- [Code Style](#code-style)

## Project Overview

Moody is an Android application for logging daily moods alongside current weather information. It allows users to browse their mood history and view simple insights, such as correlations between mood and weather (e.g., "You are mostly happy on rainy days"). The app is built with a modern tech stack, emphasizing clean architecture, Room, and a UI constructed with Jetpack Compose. It leverages Kotlin, Hilt for dependency injection, and Ktor for network operations. The project serves as a showcase for contemporary Android development and testing practices.

## Assumptions

Here are some assumption taken into accoount while developing this project:
- **Location Permission:** Assumed that user has given location permission. Didn’t asked for simplicity. Instead, test latitued and longitudes are used to load weather data.

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
- **[Turbine](https://github.com/cashapp/turbine):** For testing of Kotlin Flows. Not all Flows are tested via Turbine, just in scenarios where manually testing them is difficult like test immediate state change.
- **[Junit 5](https://docs.junit.org/current/user-guide/):** Used to test buisniss logic accross app.

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
./gradlew :app:testDebugUnitTest
```

The test results will be available in the `app/build/reports/tests/testDebugUnitTest` directory.

## Continuous Integration (CI) Workflows

This project utilizes GitHub Actions to automate various CI tasks. The workflows are defined in the `.github/workflows` directory.

### Pre-Merge Checks (`pre-merge.yml`)

This workflow is designed to ensure code quality and stability before merging changes into the `main` branch.

**Triggers:**

-   On pull requests targeting the `main` branch.

**Key Jobs & Steps:**

1.  **`lint-and-test` Job:**
    -   **Checkout Code:** Fetches the latest code from the repository.
    -   **Setup JDK:** Configures the Java Development Kit (version 17).
    -   **Setup Gradle Cache:** Caches Gradle dependencies to speed up subsequent builds.
    -   **Run Lint:** Executes `./gradlew lintDebug` to perform static code analysis. This step uses `continue-on-error: true` to ensure that reports are generated even if lint issues are found.
    -   **Upload Lint Report:** Uploads the HTML lint report (`app/build/reports/lint-results-debug.html`) as a workflow artifact named `lint-report`.
    -   **Run Unit Tests:** Executes `./gradlew testDebugUnitTest` to run all unit tests in the `app` module. This step also uses `continue-on-error: true` to ensure reports are generated regardless of test outcomes.
    -   **Upload Unit Test Report:** Uploads the entire unit test report directory (`app/build/reports/tests/testDebugUnitTest/`) as a workflow artifact named `unit-test-report`. This includes `index.html` and all associated assets.
    -   **Comment Reports on PR:** Posts a sticky comment on the pull request. The comment includes:
        -   The outcome (success/failure) of the Lint and Unit Test steps.
        -   Direct links to the workflow run page where the uploaded `lint-report` and `unit-test-report` artifacts can be downloaded.
    -   **Check Lint and Test Outcomes:** A crucial step that explicitly checks the `outcome` of the "Run Lint" and "Run Unit Tests" steps. If either of these steps failed (even if `continue-on-error` was true), this step will execute `exit 1`, causing the entire `lint-and-test` job to be marked as "failed". This ensures that the GitHub check associated with the workflow fails if there are lint issues or test failures, preventing merges if branch protection rules are set accordingly.

**Purpose:** This workflow helps maintain code quality by automatically running linters and unit tests on every proposed change to the `main` branch. It provides direct feedback on the pull request with links to detailed reports, and critically, it ensures that the PR check accurately reflects the status of these quality gates.

### Debug APK Build (on `working` branch) (`debug-apk-on-working-push.yml`)

This workflow automates the process of building a debug APK whenever changes are pushed to the `working` branch. This is useful for testing, internal distribution, or quick validation of changes on a development branch.

**Triggers:**

-   On any push to the `working` branch.

**Key Jobs & Steps:**

1.  **`build-debug-apk` Job:**
    -   **Checkout Code:** Fetches the latest code.
    -   **Setup JDK:** Configures JDK 17.
    -   **Setup Gradle Cache:** Caches Gradle dependencies.
    -   **Build Debug APK:** Executes `./gradlew assembleDebug` to compile the debug version of the application. The step has an `id: build_apk` to reference its outcome.
    -   **Upload Debug APK:** Uploads the generated `app-debug.apk` (located at `app/build/outputs/apk/debug/app-debug.apk`) as a workflow artifact. The artifact is named `debug-apk-${{ github.sha }}` to include the commit SHA for easy identification and uniqueness. Artifacts are retained for 7 days.
    -   **Check Build Outcome:** This final step checks the `outcome` of the "Build Debug APK" step. If the APK build failed, this step executes `exit 1`, causing the `build-debug-apk` job (and thus the workflow run) to be marked as "failed".

**Purpose:** This workflow streamlines the generation of debug builds from the `working` branch, providing quick access to installable APKs and giving feedback directly on associated pull requests about the build status.

## Code Style

This project follows the
official [Kotlin style guide](https://kotlinlang.org/docs/coding-conventions.html). The code is also
formatted using the default Android Studio code style settings.
