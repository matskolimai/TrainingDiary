# Error handling

Type: grilling
Status: resolved
Blocked by: 03

## Question

Which parts of `android-error-handling` does the user accept or override: the generic `Result<T, E>` wrapper vs Kotlin's
`Result`/exceptions, the `DataError` taxonomy, `EmptyResult`, extension helpers, and where they live? Given a local-only
Room app today, how much error modelling is warranted now vs when a backend arrives?

Resolve point by point, then rewrite `~/.claude/skills/android-error-handling/SKILL.md` with
`/mattpocock-skills:writing-for-agents`.

## Answer

Partly settled with the user on 2026-11-06. The user stopped after round 1. It was hard to weigh error-handling theory
without seeing real code, so the rest is to be revisited once error-related code exists. **The skill file
`~/.claude/skills/android-error-handling/SKILL.md` was deliberately left unchanged.** It still holds baseline content
that contradicts decisions 4–6 below, so the spec must carry these decisions explicitly.

**Decided (accepted by the user):**

1. **Failure transport**: a typed `Result<D, E>` in `:core:domain`. Expected failures are values, never thrown.
2. **Missing Training on edit** is plain absence: lookup by id returns `Training?`, and `null` means navigate back.
   There is no `NOT_FOUND` for local data.
3. **Blank name** is not an error type. A pure domain check (trimmed name not blank) drives `canSave`, and the save
   path also rejects a blank name as a safety net. "Validation returns a single typed error" stays as the general
   rule, but only for errors shown to the user.
4. **Catch policy**: the data layer catches only exception types it can name (e.g. `SQLiteFullException` →
   `DISK_FULL`). Anything unexpected crashes. There is no broad `Exception`/`Throwable` catch; if one is unavoidable,
   it rethrows `CancellationException`. No `printStackTrace()` and no logging library.
5. **Ktor `safeCall` / `DataError.Network` material** moves out of this skill into `android-data-layer`, as a cleaned-up
   section that applies only when a remote API exists. The Data layer ticket reviews it.
6. **`toUiText()`** is an exhaustive `when` with no `else`. Errors never shown to the user get no mapping. Feature
   errors are mapped in the feature's `presentation` module, shared errors in `:core:presentation`, and all strings
   are Polish resources.

**Provisional defaults (proposed, not reviewed by the user; follow them unless the user overrides them on seeing
code):**

7. Naming: `Result<out D, out E : RootError>` with `Success(data)` / `Failure(error)`, plus
   `typealias EmptyResult<E> = Result<Unit, E>`, in package `...core.domain.result`. This avoids shadowing
   `kotlin.Error`.
8. Helpers: only `map`, `onSuccess` and `onFailure`. Others (`asEmptyResult`, `flatMap`, `fold`, …) are added only
   when first needed.
9. Taxonomy: `sealed interface DataError : RootError` with only `enum class Local { DISK_FULL, UNKNOWN }`. There is no
   `Network` enum until a backend exists.
10. Repository writes (add, update, delete, undo-restore) return a typed `Result`. Reads don't: the list is a plain
    `Flow<List<Training>>` and the by-id lookup returns `Training?`.
11. Write failures are shown as a Polish snackbar message and the user stays where they are. The MVI ticket owns the
    mechanism.

No glossary changes and no ADR.

## Comments

- 2026-11-06 (from Data layer and backend-ready seam): the user dropped `SQLiteFullException` / `DISK_FULL` handling, so
  writes return `Unit` and nothing in this feature produces a typed error. Decisions 1 and 6 and provisional defaults
  7–11 are **dormant**: the spec builds no `Result`, `DataError`, `toUiText()` or write-failure snackbar. They apply
  from the first expected failure the user must see, most likely a backend. Decisions 2–4 still apply: missing = `null`,
  blank name is not an error, named catches only. Decision 5 is done: the Ktor material now lives in
  `android-data-layer`.
