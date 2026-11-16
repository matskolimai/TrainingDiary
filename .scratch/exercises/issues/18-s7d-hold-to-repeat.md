# 18: S7d Hold to repeat

**What to build:** holding a − or + button repeats its step until release or a limit: first repeat after ~400 ms,
then every ~90 ms. Applies to every `AppRepeatIconButton`, so both the Set count and the focused row's ±. See
`../spec.md` (Product rules).

**Blocked by:** 15 (S7a Focus and reps).

**Status:** done

- [x] Hold-to-repeat lives in `AppRepeatIconButton` and sends repeated clicks; it stops at a limit (button disabled).
- [x] Case, test-first, `@OnlyOn(Channel.DEVICE)`: holding + on weight for about 1 s shows at least 3 kg.
- [x] `./gradlew check` and the device suite pass; mutation testing done.

## Mutation testing

No production Kotlin changed outside `:core:design-system`: the only production change is `AppRepeatIconButton`,
which the skill leaves out of mutation testing. The new device case
`holding plus on the weight keeps stepping it until release` failed before the change (1,5 kg, release click only)
and passes after it. Code review then found that a quick tap right after a hold was swallowed; the device case
`holding plus on the weight stops at the limit and a later tap steps once` failed on that and passes with the fix.
