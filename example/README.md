# SeamlessPay Android Example App

[![License](https://img.shields.io/github/license/seamlesspay/seamlesspay-android)](https://github.com/seamlesspay/seamlesspay-android/blob/master/LICENSE)

## Overview

The Android Example App demonstrates the basic usage of UI components. 
It includes examples of initializing UI components with various configurations. 
With this app, you can carry out tokenization, refund, and charge requests. 
All code samples are found in the ```MainActivity``` class.

Ensure to replace the placeholder authorization data with your own to successfully 
perform the requests.


```java 
Authorization authorization = Authorization
    .fromKeys("staging", "pk_XXXXXXXXXXXXXXXXXXXXXXXXXX", "MRT_XXXXXXXXXXXXXXXXXXXXXXXXXX");
```

You can also modify the field configuration yourself and initialize UI Components with this data.

```java 
FieldOptions option = new FieldOptions(
		new FieldConfiguration(DisplayConfiguration.REQUIRED),
		new FieldConfiguration(DisplayConfiguration.REQUIRED)
		);
```
Once you have completed the setup, select 'demo' under the Run/Debug Configuration 
and then run the project.

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