package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.ui.unit.dp

/** Every fixed component dimension, shared so components of one kind stay the same size. */
object Sizes {
    val buttonHeight = 56.dp
    val compactButtonHeight = 40.dp
    val countBadge = 28.dp
    val fab = 56.dp

    // Bottom padding that keeps a list's last item clear of the FAB: the FAB, its margin and a gap above it.
    // The margin is Scaffold's own 16dp, which M3 doesn't expose.
    val fabClearance = fab + Spacing.large + Spacing.large
    val iconBig = 64.dp
    val listRowCompactMinHeight = 56.dp
    val listRowMinHeight = 64.dp
    val listRowTallMinHeight = 72.dp
    val sheetItemHeight = 56.dp
}
