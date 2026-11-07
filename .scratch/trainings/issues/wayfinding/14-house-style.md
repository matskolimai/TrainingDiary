# Cross-cutting house style

Type: grilling
Status: resolved
Blocked by: —

## Question

Which coding conventions that no single Android skill owns does the user want written down before implementation
starts?

- File and package layout inside a module, beyond what the existing skills fix (one type per file, one package per
  screen).
- Kotlin idioms: expression bodies, `when` vs `if`, scope functions, trailing commas, named arguments, `data` vs plain
  classes, extension functions vs members.
- Comment and KDoc policy.
- Test code style shared by the acceptance, unit and screenshot suites, if it differs from production code.

Module naming and visibility are already settled in Module structure and build-logic. Resolve point by point. Also
decide where the result lives: a new reusable skill (e.g. `android-house-style`), edits spread across the existing
skills, or `.editorconfig` / Detekt rules where a rule can be enforced by a tool. Write any skill with
`/mattpocock-skills:writing-for-agents`.

## Answer

**Tools are the whole style guide.** A convention only lands if ktlint or Detekt enforces it with the configuration
as shipped; anything else is caught in the user's manual review and becomes a rule only if it keeps hurting. No
`android-code-style` skill and no `AGENTS.md` style section.

- **ktlint**: root `.editorconfig` with `ktlint_code_style = ktlint_official` and `max_line_length = 120`; nothing
  else. This brings trailing commas, no wildcard imports and `function-expression-body` (a single-`return` body
  becomes `= …`).
- **Detekt**: defaults as shipped, plus the Compose rules plugin. `detekt.yml` holds only the Compose adaptations a
  composable proves necessary (`FunctionNaming`, `LongParameterList` ignore `@Composable`), without reason comments.
  No style rule is switched on or off: `ElseCaseInsteadOfExhaustiveWhen`, `UseIfInsteadOfWhen`,
  `UnnamedParameterUse`, `UseDataClass`, widened `ForbiddenComment`, test excludes for `LongMethod` / `LargeClass`
  were all offered and declined.
- **Package layout**: left as the existing skills fix it; revisited only if it looks bad.
- **Gotchas for the static-analysis implementation ticket**: type-resolving Detekt rules run only in
  `detektMain` / `detektTest`, so the gate runs those tasks; ktlint (120) and Detekt `MaxLineLength` (120) both apply
  to backtick test names, which may need wrapping.
- **Skill**: `android-module-structure`'s Static analysis section rewritten with writing-for-agents (tools are the
  whole style guide; ktlint_official/120; Detekt defaults plus Compose adaptations only; type-resolving tasks).

**Review preferences, not enforced** (the user likes these; a reviewer may point them out, nothing fails on them):

- Expression body for any single-expression function, even when it wraps.
- `if` for one condition; `when` for three or more branches or a sealed/enum subject, listing every case with no
  `else`.
- `?.let` for a null-safe transform, `apply` to configure a just-built object, `also` for a side effect in a chain.
  Nothing is banned.
- Named arguments for literal `true`/`false`/`null`/numbers and for neighbouring same-typed parameters; trailing
  lambdas outside the parentheses; `modifier` passed by name.
- `data class` for values compared by content, `data object` for argument-free sealed cases, plain `class` for
  anything with behaviour or dependencies; no `value class` yet.
- Members for behaviour core to an owned type; extensions for types you don't own, cross-layer mapping, or one
  caller's private helper; no "utils" files.
- No KDoc; comments explain why; no commented-out code; no committed `TODO`/`FIXME`.
- Tests: no `// given/when/then` comments, private helpers at the bottom, test data inline in domain words.
- Explicitly *not* preferred as rules: `val` over `var`, `!!`, `lateinit`, explicit return types (Q11 declined).
