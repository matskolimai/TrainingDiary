# Data layer and backend-ready seam

Type: grilling
Status: resolved
Blocked by: 02, 03, 06

## Question

How is Training persistence shaped so Room can later be swapped for (or fronted by) a backend? Covers: repository
interface location and contract (Flow for the list, suspend for writes), data source split, Room entity/DAO placement,
mappers, identity type (Long autoincrement vs UUID — matters for undo-with-same-identity and a future backend), DTO
conventions for later. Which parts of `android-data-layer` are accepted or overridden?

Resolve point by point, then rewrite `~/.claude/skills/android-data-layer/SKILL.md` with
`/mattpocock-skills:writing-for-agents`. Decide whether this warrants an ADR.

Per Error handling: also take over the Ktor `safeCall` / `DataError.Network` material from
`android-error-handling` as a section that applies only when a remote API exists, cleaned up (no `printStackTrace`,
no catch-all).

## Answer

Settled with the user on 2026-11-06, point by point against `android-data-layer`. The skill was rewritten to match
(`~/.claude/skills/android-data-layer/SKILL.md`, Android-only, written with `/mattpocock-skills:writing-for-agents`).
The Room facts below come from a primary-source research pass on Room 3.0.3 (release notes, reference docs, androidx
source).

- **Seam**: one `TrainingRepository` interface in `feature:trainings:domain`, whatever sits behind it. This overrides
  the skill's "repository only when multi-source" rule. The implementation is `internal` `RoomTrainingRepository` in
  `feature:trainings:data` (no `Impl` suffix). The DAO is the only source. Data-source classes appear only once there
  is more than one source, and they stay `internal` in `data`.
- **Contract**:
  - `observeTrainings(): Flow<List<Training>>`, in creation order via `ORDER BY id`. Ordering is the repository's
    guarantee, so callers never sort.
  - `suspend getTraining(id: Long): Training?`, where `null` means the Training is missing.
  - `suspend addTraining(name: String)`, `updateTraining(training: Training)`, `deleteTraining(id: Long)`,
    `restoreTraining(training: Training)`. All four writes return `Unit`.
  - `restore` re-inserts with the original id, which serves undo.
  - The repository trims the name, and a blank name fails `require` (the safety net from Error handling).
  - No `withContext`: Room 3 runs queries on `Dispatchers.IO` by default.
- **No caught failures**: the user dropped `SQLiteFullException` / `DISK_FULL` handling ("I do not assume my phone will
  be full"). Every SQLite exception crashes. Nothing in this feature produces a typed error, so `Result`, `DataError`,
  `toUiText()` and the write-failure snackbar are **not built**. Error handling decisions stay dormant until a first
  expected failure appears (see the comment on that ticket).
  - Consequence: under Module structure's "create a `:core` module the first time it has content" rule, `:core:domain`
    has nothing to hold yet. The spec should treat it as not created until it gets content.
- **Identity**: a plain `Long`, `@PrimaryKey(autoGenerate = true)`, with no value class. Room 3 defaults to
  `AUTOINCREMENT`, so deleted ids are never reused and an explicit old id can be re-inserted. A UUID was rejected: the
  app is never distributed and has no backend, and Room 3.0.x has no native `Uuid` support anyway.
- **Placement**: `TrainingEntity` (table `trainings`), `TrainingDao` and `TrainingDiaryDatabase` all live in
  `:core:database` and are public, because `@Database` must see every entity. `feature:trainings:data` holds the
  mappers and the repository. If the database package grows too large, the user will reorganise it later.
- **Models and mappers**: `data class Training(val id: Long, val name: String)` in domain. The mappers are extension
  functions in `data`: `TrainingEntity.toTraining()` and `Training.toTrainingEntity()`. DTOs appear only once a remote
  API exists.
  - The user's reservation: they dislike near-duplicate classes across layers and may revisit this later. The skill
    keeps the rule firm.
- **Driver**: set explicitly with `setDriver(AndroidSQLiteDriver())`, because it throws typed framework exceptions. Use
  `BundledSQLiteDriver` only for KMP, or when the app needs a SQLite feature the platform version lacks.
- **Schema and migrations**: `version = 1` and `exportSchema = true`. The `convention.android.room` plugin sets
  `room3 { schemaDirectory(...) }` (plugin `androidx.room3`), and the schema JSON is committed. Use auto-migrations
  first. Never `fallbackToDestructiveMigration`; during development, reinstall the app instead.
- **The skill's remote-API section** (conditional, `:core:data` created when it is first needed): an `HttpClientFactory`
  with an injected engine; tokens in DataStore; offline-first with Room as the single source of truth; and a cleaned-up
  `safeCall` / `responseToResult` taken over from `android-error-handling`. That `safeCall` catches only named
  exceptions and has no `printStackTrace` and no `LogLevel.ALL` logging. KMP is dropped from the skill.
- **No ADR**: every choice here is cheap to reverse, so this closes the "Persistence-seam ADR" fog. No glossary changes.
