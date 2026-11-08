package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.ui.graphics.vector.ImageVector

/** Every icon in the app; features use these, never `Icons.*`. */
object AppIcons {
    val Add: ImageVector get() = Icons.Outlined.Add
    val Back: ImageVector get() = Icons.AutoMirrored.Outlined.ArrowBack
    val Delete: ImageVector get() = Icons.Outlined.Delete
    val Edit: ImageVector get() = Icons.Outlined.Edit
    val FitnessCenter: ImageVector get() = Icons.Outlined.FitnessCenter
    val MoreVert: ImageVector get() = Icons.Outlined.MoreVert
}
