# Compose components and design system

Type: grilling
Status: resolved
Blocked by: 03, 04, 05

## Question

Which parts of `android-compose-components` does the user accept or override (IconButton wrapping, icon objects,
previews, parameter design, slots, modifier conventions, string resources), and what goes into the design system for
this app given the chosen prototypes: dark-only theme (colors, typography, shapes), which components are wrapped vs used
straight from Material 3, preview conventions for a dark-only app, and Polish string-resource conventions?

Resolve point by point, then rewrite `~/.claude/skills/android-compose-components/SKILL.md` with
`/mattpocock-skills:writing-for-agents`.

Also settle the one open point from Prototype: Training form screen: does the keyboard's Done (IME) action on the
name field save the Training?

## Answer

Settled with the user on 2026-11-07, point by point against `android-compose-components`. The skill was rewritten to
match (`~/.claude/skills/android-compose-components/SKILL.md`, Android-only, written with
`/mattpocock-skills:writing-for-agents`). References to skills that don't exist here were removed.

- **Accepted unchanged**:
  - Parameter design and no local state except Compose-internal state.
  - Clickable icons in an `IconButton` at the default 48dp, `contentDescription` from string resources.
  - Visibility table (`private` → `internal` → `public` only in the design system).
  - Modifier extensions never `@Composable`. The `composed {}` example became a plain chain or `Modifier.Node`.
  - Animated values read in `graphicsLayer {}` / `offset {}`. Effects only for Compose-internal concerns (add-mode
    autofocus).
- **Icons** (override): `material-icons-extended`, Outlined style, reached only through `AppIcons` (`ImageVector`),
  so swapping an icon is one line. A missing icon goes to `res/drawable` and joins the same object.
- **Theme**:
  - `AppTheme(content)`, dark-only: plain `darkColorScheme()`, default `Typography()` and `Shapes()`.
  - `Color.kt`, `Type.kt` and the light scheme are deleted.
  - `enableEdgeToEdge` with dark system-bar styles; the XML window theme has a dark parent.
- **Spacing**: a plain `Spacing` object (4/8/12/16/24/32dp). Component dimensions stay as `dp` literals.
- **Design system holds every component** (override of "use M3 directly"):
  - App-agnostic, `App` prefix, so the module could become a shared AAR.
  - Set: `AppScaffold`, `AppTopBar`, `AppTopBarWithBack`, `AppIconButton`, `AppFab`, `AppTextField`, `AppButton`,
    `AppListRow`, `AppMonogram`, `AppBottomSheet` + `AppSheetItem`, `AppEmptyState`.
  - Slots only on `AppListRow` and `AppBottomSheet`.
  - The feature keeps only `TrainingRow` and the monogram function.
- **Feature M3 allowlist**: `MaterialTheme`, `Text`, `SnackbarHostState`, `SnackbarResult`. Checked in review, no
  Detekt rule.
- **M3 first, reshaped**: wrap the direct M3 counterpart where one exists. Containers and layouts (row, monogram, empty
  state) are `Surface` + `Row`/`Column`/`Box`; the user rejected M3 `ListItem` for the row as harder to read.
- **Previews**: one `private` preview per design-system component; per-State Screen previews named
  `<Screen>Preview<State>` with literal State; all through the public `AppPreview { }` wrapper.
- **Strings**:
  - Polish in the default `values/`, in the module that uses it.
  - Names `<screen>_<element>` and `<screen>_cd_<thing>`; `<plurals>` with Polish forms; „…” written literally.
  - Design-system strings prefixed `design_system_`; only `design_system_cd_back` exists now.
- **Row tap**: does nothing for now; only ⋮ opens the sheet. Other interactions come later.
- **Keyboard Done** saves, as already settled in Presentation: MVI.
- No glossary changes and no ADR.
