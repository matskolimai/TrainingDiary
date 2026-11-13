# 18: S7d Hold to repeat

**What to build:** holding a − or + button repeats its step until release or a limit: first repeat after ~400 ms,
then every ~90 ms. Applies to every `AppRepeatIconButton`, so both the Set count and the focused row's ±. See
`../spec.md` (Product rules).

**Blocked by:** 15 (S7a Focus and reps).

**Status:** ready-for-agent

- [ ] Hold-to-repeat lives in `AppRepeatIconButton` and sends repeated clicks; it stops at a limit (button disabled).
- [ ] Case, test-first, `@OnlyOn(Channel.DEVICE)`: holding + on weight for about 1 s shows at least 3 kg.
- [ ] `./gradlew check` and the device suite pass; mutation testing done.
