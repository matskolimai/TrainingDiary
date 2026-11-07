# Cross-cutting house style

Type: grilling
Status: open
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
