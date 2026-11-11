# 10: Teach `android-mutation-testing` to try the JVM first

**What to build:** the `android-mutation-testing` skill (in the user's skills directory, outside this repo) judges
mutants on the ViewModel channel first, so most mutants are judged in seconds and the device only checks what its own
cases can catch. See `../spec.md` (Skills).

**Blocked by:** 05 (Run the Training editor cases on the JVM).

**Status:** done

- [x] Each mutant runs first against the Screen's ViewModel channel test class.
- [x] Only mutants that survive the ViewModel channel run against the Screen's device test class.
- [x] Mutants in Screen or Root composables go straight to the device.
- [x] The skill's script, if it has one, supports both channels.
- [x] Follows the `writing-for-agents` skill; lines at most 120 characters.

## Comments

- The skill is versioned separately in `C:/Users/matsk/.claude/skills`. Its changes are isolated on branch
  `ticket/10-mutation-jvm-first` in `C:/Users/matsk/.codex/worktrees/d9c9/skills`, alongside this repo's worktree.
  The installed skills checkout has unrelated edits, which remain untouched. The user approved both merges;
  TrainingDiary merged the ticket in `7d1170c`, and the skills repository merged the skill in `c890413`.
- Skills commit: `4cc614ff86132b72aae1108dcec68d8596c7c9cb` (Try ViewModel acceptance cases before device mutation
  tests). This repo's commit records completion; the skill and runner are committed in their own repository.
- The runner's `run` command takes JVM and device task/class pairs. It tries the ViewModel classes with repeated
  `--tests` filters, then sends only survivors to the device with its instrumentation class filter. `run-device`
  handles Screen/Root mutants directly. Routing follows the mutated code, including files with mixed contents.
- The source stays mutated across channels and is restored after the final result or an interruption. Only device
  runs acquire the shared device lock. Compile/build errors stop for repair instead of triggering device fallback.
- Survivor settlement now considers both channels: UI limitations alone no longer justify accepting a survivor.
- `scripts/test-mutant.sh` in the skill exercises 14 CLI scenarios with a Gradle stand-in: routing, class filters,
  kills, survivors, errors, interruption cleanup, lock isolation and byte-for-byte restoration, including CRLF files
  and paths with spaces. All pass. Bash syntax, whitespace and the Markdown line limit also pass.
- Gate: `./gradlew check :app:pixel9DebugAndroidTest --no-daemon` passes, including static analysis, unit tests and
  screenshot verification. The JVM runs four list cases and six editor cases, skipping the device-only focus case;
  all 11 device cases pass.
- Real runner validation: temporarily replacing the editor's name-change state update with `Unit` fails four
  editor cases on the ViewModel channel. The runner reports `KILLED`, never starts the device channel, restores the
  production file byte-for-byte and reports `clean`. The editor class passes again after restoration: six passed,
  one skipped. No production Kotlin change remains.
- Code review: independent Standards and Spec reviews report no findings. Standards checked repo conventions,
  writing-for-agents and the smell baseline; Spec checked every ticket criterion against the skill and runner.
- Post-merge validation: `./gradlew check :app:pixel9DebugAndroidTest --no-daemon --console=plain` passes, with
  unchanged tests up to date. The installed runner's 14 CLI scenarios pass. The spec is marked done after checking
  all implementation tickets, the shared cases and drivers, the headless app, and both skills.

## Additions

None.
