# Google Maps Nav Cue Detector

A minimal Android proof-of-concept for testing whether Google Maps exposes
turn-by-turn navigation information through Android Accessibility APIs.

## What it does

- Watches accessibility events from Google Maps only.
- Reads visible text/content descriptions exposed by the accessibility tree.
- Filters for likely navigation-related phrases/distances.
- Displays detected cues locally in the app.
- Uses no INTERNET permission.
- Has no analytics, ads, trackers, WebView, or external network service.

## Build

1. Open this folder in Android Studio.
2. Let Android Studio sync/download the Android Gradle Plugin and dependencies.
3. Build > Make Project.
4. Build > Build APK(s).
5. The debug APK will be under:
   `app/build/outputs/apk/debug/app-debug.apk`

## Test

1. Install the debug APK on an Android phone.
2. Open Nav Cue Detector.
3. Tap "Open Accessibility Settings".
4. Enable "Nav Cue Detector".
5. Start a Google Maps navigation session.
6. Return to Nav Cue Detector and inspect detected text.

## Important limitation

This is a feasibility probe, not yet a complete Google Maps TBT bridge.
Google Maps may expose different accessibility information across versions,
devices, languages, and UI states. If the required cue is not exposed,
the next prototype can investigate other Android-observable sources.

The app deliberately does not attempt to bypass Google Maps security,
modify Google Maps, or transmit captured information over the network.


## Build directly on GitHub (phone-friendly)

1. Create a GitHub repository.
2. Upload the contents of this project to the repository (make sure `.github/workflows/build-apk.yml` is included).
3. Open the repository's **Actions** tab.
4. Select **Build APK**.
5. Tap **Run workflow**.
6. Wait for the workflow to finish successfully.
7. Open the completed workflow run.
8. Under **Artifacts**, download **NavCue-debug-apk**.
9. Extract the ZIP and install `app-debug.apk` on your phone.

The workflow builds a debug APK only. It does not contain signing credentials or
publish the APK to Google Play.

For a security check, you can upload the resulting APK to your preferred
multi-engine antivirus scanner before installing it.
