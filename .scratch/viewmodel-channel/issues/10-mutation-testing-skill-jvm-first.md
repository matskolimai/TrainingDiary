# 10: Teach `android-mutation-testing` to try the JVM first

**What to build:** the `android-mutation-testing` skill (in the user's skills directory, outside this repo) judges
mutants on the ViewModel channel first, so most mutants are judged in seconds and the device only checks what its own
cases can catch. See `../spec.md` (Skills).

**Blocked by:** 05 (Run the Training editor cases on the JVM).

**Status:** ready-for-agent

- [ ] Each mutant runs first against the Screen's ViewModel channel test class.
- [ ] Only mutants that survive the ViewModel channel run against the Screen's device test class.
- [ ] Mutants in Screen or Root composables go straight to the device.
- [ ] The skill's script, if it has one, supports both channels.
- [ ] Follows the `writing-for-agents` skill; lines at most 120 characters.
