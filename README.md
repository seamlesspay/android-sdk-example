<!-- [<img width="250" height="119" src="https://raw.githubusercontent.com/seamlesspay/seamlesspay-android/master/assets/stripe_logo_slate_small.png"/>](https://seamlesspay.com/docs/mobile/android) -->

# SeamlessPay Android

[![License](https://img.shields.io/github/license/seamlesspay/seamlesspay-android)](https://github.com/seamlesspay/seamlesspay-android/blob/master/LICENSE)

## Overview

SeamlessPay Android provides drop-in UI Components that can be used
to collect your users' payment details and process payments from within your
app. We also expose the Base SDK that allow you to use our API directly.

Get started with [📚 integration guide](https://docs.seamlesspay.com/android-sdk).

## Installation

Your development environment must have minimum requirements configured:

<details><summary><strong>Requirements</strong></summary><p>

- [Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) is installed and available in your `PATH`
- [Android Studio](https://developer.android.com/studio/)
- [Gradle](https://gradle.org/releases/) 5.4.1+
- [Android SDK](https://developer.android.com/studio/releases/sdk-tools) >= 21

Note: If you do have the Android SDK installed, add a `local.properties`
file to the top level directory with `sdk.dir=/path/to/your/sdk/.android-sdk`

</p></details>

## Example Apps

The SeamlessPay Android SDK comes bundled with the following demo apps:

- **[`Demo`](demo)**: Basic setup and usage of UI Components

Run `./gradlew :demo:installDebug` to install the [Demo](demo) app on a device.