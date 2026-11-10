# TrainingDiary

## Agent skills

### Issue tracker

Issues are tracked as local markdown files under `.scratch/<feature>/`. See `docs/agents/issue-tracker.md`.

### Triage labels

Default vocabulary: `needs-triage`, `needs-info`, `ready-for-agent`, `ready-for-human`, `wontfix`. See
`docs/agents/triage-labels.md`.

### Domain docs

Single-context: one `GLOSSARY.md` and `docs/adr/` at the repo root. See `docs/agents/domain.md`.

### Mutation testing

When a ticket changed production Kotlin, call the Skill `android-mutation-testing` once the gate is green and before
`code-review`.

### Markdown formatting

When creating or editing `.md` files, keep each line at most 120 characters; wrap longer lines.

### Additions

An **addition** is user-visible behaviour the user didn't ask for in their own words. Prototypes, specs and tickets
list additions under their own heading for the user to keep or drop; only kept additions carry forward.
