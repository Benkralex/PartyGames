package de.benkralex.partygames.games.common.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StringListInputWidget(
    label: String,
    initialValue: List<String> = emptyList(),
    onValueChange: (List<String>) -> Unit,
    minCount: Int = -1,
    maxCount: Int = -1,
    modifier: Modifier = Modifier,
    textFieldLabel: String = ""
) {
    val initialValuesMap = initialValue.mapIndexed { index, value -> index to value }.toMap()
    val strings = remember { mutableStateOf(initialValuesMap) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    if (maxCount != -1 && strings.value.size >= maxCount) {
                        return@IconButton
                    }
                    val nextKey = if (strings.value.isEmpty()) 0 else (strings.value.keys.maxOrNull() ?: 0) + 1
                    strings.value = strings.value + (nextKey to "")
                    onValueChange(strings.value.values.toList())
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                )
            }
        }
        strings.value.forEach { entry ->
            val index = entry.key
            val value = entry.value
            key(index) {
                /*Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {*/
                    StringInputWidget(
                        initialValue = value,
                        modifier = Modifier
                            .fillMaxWidth(),
                        onValueChange = { newValue: String ->
                            strings.value = strings.value + (index to newValue)
                            onValueChange(strings.value.values.toList())
                        },
                        label = textFieldLabel,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    if (minCount != -1 && strings.value.size <= minCount) {
                                        return@IconButton
                                    }
                                    strings.value = strings.value - index
                                    onValueChange(strings.value.values.toList())
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.error,
                                )
                            }
                        }
                    )
                //}
            }
        }
    }
}