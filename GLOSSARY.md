# TrainingDiary

A personal training diary for a single user: configure trainings, and later record performing them.

## Language

**Training**:
A named, reusable template the user configures once (e.g. "Push day") and performs many times.
_Avoid_: Workout, plan, routine

**Training session**:
One occurrence of the user actually performing a Training. Not yet modelled; reserved so it is never confused with
Training.
_Avoid_: Training (for the performed instance), workout log

**Screen**:
One place the user can navigate to. A Screen that does two jobs (adding and editing) is still one Screen.
_Avoid_: Page, view

**Training list**:
The Screen that lists every Training in order and is where the user deletes one.
_Avoid_: Home screen, trainings screen

**Training editor**:
The Screen where the user names a Training and edits its Training exercises; nothing is kept until the user saves.
It is the "new training screen" when adding and the "edit training screen" when changing an existing
Training; both are the same Screen.
_Avoid_: Training form, training details

**Exercise**:
An entry in the app's fixed catalogue of movements (e.g. „Uginanie młotkowe”), belonging to exactly one Exercise
category. Removing it from a Training never removes it from the catalogue.
_Avoid_: Movement, lift; Exercise for the copy inside a Training (that is a Training exercise)

**Exercise category**:
A fixed group of Exercises by the body part they train: Klatka piersiowa, Plecy, Barki, Biceps, Triceps, Nogi, Brzuch.
_Avoid_: Muscle group, body part, type

**Training exercise**:
An Exercise added to one Training, together with its Sets. The Training editor lists a Training's Training exercises.
_Avoid_: Exercise (when the one inside a Training is meant), entry, item

**Set**:
One planned round of a Training exercise: a repetition count and a weight in kilograms (0 kg for bodyweight). A
target in the Training template, not something recorded while training. For timed Exercises such as Plank the
repetition count stands for seconds.
_Avoid_: Series, round; session (that is a Training session)
