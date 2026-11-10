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
The Screen where the user names a Training. It is the "new training screen" when adding and the "edit training
screen" when changing an existing Training; both are the same Screen.
_Avoid_: Training form, training details
