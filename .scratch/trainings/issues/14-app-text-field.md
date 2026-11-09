# 14: AppTextField

**What to build:** the outlined single-line text field used for a Training's name. See the spec's "Visual details".

**Blocked by:** 07 (Design-system foundation)

**Status:** done

- [x] `AppTextField` wrapping the M3 outlined text field: `value` / `onValueChange`, label, single line and an IME
      action callback; no supporting or error text
- [x] Callers can request focus (for add-mode autofocus) through a standard Compose mechanism
- [x] App-agnostic and public; `internal` previews (empty with label, filled) through `AppPreview`, snapshotted;
      reference images committed
- [x] `./gradlew check` is green
