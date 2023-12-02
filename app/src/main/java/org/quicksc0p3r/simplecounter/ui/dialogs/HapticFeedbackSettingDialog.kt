@file:OptIn(ExperimentalMaterial3Api::class)

package org.quicksc0p3r.simplecounter.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.quicksc0p3r.simplecounter.settings.SettingsManager
import org.quicksc0p3r.simplecounter.R
import org.quicksc0p3r.simplecounter.ui.components.CheckboxItem

@Composable
fun HapticFeedbackSettingDialog(
    dismiss: () -> Unit,
    manager: SettingsManager,
    currentTouchSetting: Boolean,
    currentVolumeSetting: Boolean
) {
    var hapticFeedbackOnTouch by remember { mutableStateOf(currentTouchSetting) }
    var hapticFeedbackOnVolume by remember { mutableStateOf(currentVolumeSetting) }

    AlertDialog(
        onDismissRequest = dismiss,
        title = { Text(stringResource(R.string.haptic_feedback)) },
        confirmButton = {
            TextButton(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        manager.storeHapticFeedbackOnTouchSetting(hapticFeedbackOnTouch)
                        manager.storeHapticFeedbackOnVolumeSetting(hapticFeedbackOnVolume)
                    }
                    dismiss()
                },
                content = { Text(stringResource(R.string.save)) }
            )
        },
        dismissButton = {
            TextButton(
                onClick = dismiss,
                content = { Text(stringResource(R.string.cancel)) }
            )
        },
        text = {
            Column {
                CheckboxItem(
                    name = stringResource(R.string.haptic_feedback_touch),
                    isToggled = hapticFeedbackOnTouch,
                    changeValue = { hapticFeedbackOnTouch = it }
                )
                CheckboxItem(
                    name = stringResource(R.string.haptic_feedback_volume),
                    isToggled = hapticFeedbackOnVolume,
                    changeValue = { hapticFeedbackOnVolume = it }
                )
            }
        }
    )
}