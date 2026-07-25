# Implementation Plan - Support 16 KB Page Sizes

The project is currently hitting an issue where the APK is incompatible with 16 KB page size devices. This is because the native library `libdatastore_shared_counter.so` (part of Jetpack DataStore) is not aligned at 16 KB zip boundaries.

Starting November 1st, 2025, Google Play will require all new apps and updates targeting Android 15+ (API 35+) to support 16 KB page sizes. The current project targets API 36.

## Proposed Changes

To fix the alignment issue and ensure 16 KB page size support, we will upgrade the Android Gradle Plugin (AGP) and the Gradle wrapper.

### Build Configuration

#### [MODIFY] [gradle-wrapper.properties](file:///C:/Users/dounga222/Documents/Default%20Project/gradle/wrapper/gradle-wrapper.properties)
- Upgrade Gradle from `8.5` to `8.7`. AGP 8.5.1+ requires Gradle 8.7 or higher.

#### [MODIFY] [build.gradle.kts](file:///C:/Users/dounga222/Documents/Default%20Project/build.gradle.kts)
- Upgrade Android Gradle Plugin from `8.2.2` to `8.5.2`. AGP 8.5.1 and higher automatically handle 16 KB zip alignment for uncompressed shared libraries.

#### [MODIFY] [app/build.gradle.kts](file:///C:/Users/dounga222/Documents/Default%20Project/app/build.gradle.kts)
- Upgrade `androidx.datastore:datastore-preferences` from `1.1.1` to `1.2.1` to ensure the library itself is also 16 KB compatible (although AGP upgrade is the primary fix for zip alignment).

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to ensure the project builds successfully with the new AGP and Gradle versions.
- (Self-verification) Check the APK alignment using `zipalign` if available in the environment, or rely on the fact that AGP 8.5.1+ is designed to fix this.

### Manual Verification
- The user can verify the fix by running the same check that produced the error message.
- Verify app functionality, especially DataStore usage.
