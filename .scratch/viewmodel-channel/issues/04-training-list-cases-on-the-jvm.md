# 04: Run the Training list cases on the JVM

**What to build:** the four Training list cases run on the ViewModel channel as ordinary unit tests of `:app` in
`./gradlew check`. They run over the real Koin graph, real repository and in-memory Room, with no UI, no Robolectric
and no Android `Context`. See `../spec.md` (ViewModel channel).

- The JVM base class starts the app's own Koin modules and overrides only the database: Room's context-free in-memory
  builder, the bundled SQLite driver (JVM artifact), and the test dispatcher as query coroutine context. The same
  standard test dispatcher is set as Main.
- The headless app stands in for `AppNavDisplay` and the Activity:
  - it applies `AppNavigator`'s requests to a real Navigation 3 back stack with the real push and pop guards;
  - it keeps one ViewModel store per entry and clears it on pop;
  - it collects the top entry's state, and its events where it has any, while in the foreground;
  - it maps each nav key to its ViewModel in one explicit `when`;
  - `settle()` runs everything due at the current virtual time.
- The Training list ViewModel driver sends the action a tap would send, settles, then reads state. It finds rows by
  name and reads "undo offered" from the last Training list event.
- `given.trainings` goes through the Training editor, so the Training editor ViewModel driver gets the steps `given`
  uses now (type a name, save). Its other steps are `TODO("ticket 05")`; nothing calls them until 05.

**Blocked by:** 02 (Put the acceptance cases behind driver interfaces).

**Status:** ready-for-agent

- [ ] A one-line JVM test class runs the Training list cases; all 4 pass in `./gradlew check`.
- [ ] Navigation on the JVM goes through the real `AppNavigator`, screen navigators and push and pop guards.
- [ ] The database is the only Koin override; no fake repository or fake navigator exists.
- [ ] Every coroutine of a case runs on one test dispatcher; the cases pass repeatedly without timing flakes.
- [ ] Re-applying a mutant that stops the undo event being sent fails a case on the JVM.
- [ ] The device suite is unchanged and passes.
