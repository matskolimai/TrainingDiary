# Prototype: Training list screen

Type: prototype
Status: resolved
Blocked by: —

## Question

What should the Training list screen look like? Build one HTML page (`.scratch/trainings/prototypes/training-list.html`)
with at least 3 switchable variants at phone size, Material 3, **dark theme only**, Polish strings. Show the filled list
and the opened bottom sheet (Edytuj / Usuń); a glimpse of the undo snackbar is welcome. The user picks one (or a mix);
the answer records the choice and the concrete visual details the design system must honour.

## Answer

Resolved with the user on 2026-11-05. **Winner: variant E**, which is variant C with B's separated rows and D's standard
FAB.
Prototype: `.scratch/trainings/prototypes/training-list.html` (`?variant=E`). All variants are captured on the throwaway
branch
`prototype/training-list` (never merged).

Visual details the design system must honour (dark theme, M3 baseline dark tokens until the design-system ticket picks a
palette):

- **Top bar**: center-aligned top app bar, title „Treningi” (titleLarge, 22sp).
- **Count label** above the list: „N treningów” using Polish plurals (trening / treningi / treningów → `plurals`
  resource),
  titleSmall 14sp medium, `primary` colour, padding 16dp top, 32dp start. Hidden when the list is empty.
- **List**: horizontal padding 16dp, **12dp gap between rows**. Each row is a separate container: `surfaceContainer`
  (#211F26 on the baseline scheme), **16dp corner radius**, min height 64dp, padding 16dp start / 4dp end, 16dp between
  elements. Bottom padding leaves room for the FAB.
- **Row content**: leading 40dp circle monogram, the first character of the trimmed name, uppercased,
  `primaryContainer` / `onPrimaryContainer`, medium weight. Name in bodyLarge 16sp, `onSurface`, wraps to multiple lines
  (no ellipsis). Trailing 48dp icon button `more_vert` in `onSurfaceVariant`, content description „Więcej opcji”.
- **FAB**: standard 56dp, 16dp corners, `primaryContainer`, `add` icon, bottom-right with 16dp margin, content
  description
  „Dodaj trening”. It moves up when the snackbar is shown (Scaffold behaviour).
- **Bottom sheet**: modal, `surfaceContainerLow`, 28dp top corners, drag handle; two 56dp list items with leading icons:
  `edit` „Edytuj”, `delete` „Usuń”. No title in the sheet.
- **Snackbar**: „Usunięto „<name>”” with action „Cofnij”; standard M3 snackbar, default duration.
- **Empty state**: centred on screen, 64dp icon (`fitness_center`, dimmed), headline „Brak treningów” (22sp,
  `onSurface`),
  body „Dodaj pierwszy trening przyciskiem +” (14sp, `onSurfaceVariant`). FAB stays visible.

> **Superseded in part** (2026-11-07): the monogram was dropped from the row, so `AppMonogram`, the monogram
> function and its unit tests are gone. See [Spec-to-ticket slicing order](15-slicing-order.md).
