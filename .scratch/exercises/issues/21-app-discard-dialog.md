# 21: AppDiscardDialog

**What to build:** one reusable discard dialog: Material 3 `AlertDialog`, title „Odrzucić zmiany?”, no body, buttons
„Anuluj” and „Odrzuć”. See `../spec.md` (Design system).

**Blocked by:** None (can start immediately).

**Status:** done

- [x] `AppDiscardDialog(onDiscard, onDismiss)` in `:core:design-system`, strings in its resources; „Anuluj”, a tap
      outside and back call `onDismiss`.
- [x] Preview snapshotted.
- [x] `./gradlew check` passes.

## Mutation testing

Skill run; its diff leaves out `:core:design-system` (screenshot-covered), so no production Kotlin
remained to mutate. The dialog snapshot covers the component.
