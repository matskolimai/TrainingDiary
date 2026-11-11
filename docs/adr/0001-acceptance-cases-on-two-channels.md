# Acceptance cases run on two channels: the device and a headless app

The device acceptance suite could not kill mutants that need two taps before the Screen redraws, or the app
left in the background for over 5 s, so those behaviours went untested. Every acceptance case now runs, through
one DSL, on two channels: the managed device with Compose UI drivers, and the JVM with ViewModel drivers over a
headless app. The headless app is the real Koin graph with in-memory Room on the bundled SQLite driver; only
`AppNavDisplay` and the Activity are replaced. A case one channel cannot stage is tagged for the other, and a step a
channel cannot honour fails rather than passing silently.

## Considered Options

- **Pitest on the presentation module**: most surviving mutants were coroutine noise, and it never generated the
  double-tap mutant.
- **Robolectric to run the device suite on the JVM**: ruled out.
- **A fake repository**: the trimming and blank-name rules live in the repository, so cases would test a fake.
- **A second DSL for ViewModel tests**: two descriptions of the same behaviour would drift apart.
- **One ViewModel per test**: cases cross Screens, so their bodies could not be shared.
