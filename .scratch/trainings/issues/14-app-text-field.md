# 14: AppTextField

**What to build:** the outlined single-line text field used for a Training's name. See the spec's "Visual details".

**Blocked by:** 07 (Design-system foundation)

**Status:** ready-for-agent

- [ ] `AppTextField` wrapping the M3 outlined text field: `value` / `onValueChange`, label, single line and an IME
      action callback; no supporting or error text
- [ ] Callers can request focus (for add-mode autofocus) through a standard Compose mechanism
- [ ] App-agnostic and public; `internal` previews (empty with label, filled) through `AppPreview`, snapshotted;
      reference images committed
- [ ] `./gradlew check` is green
