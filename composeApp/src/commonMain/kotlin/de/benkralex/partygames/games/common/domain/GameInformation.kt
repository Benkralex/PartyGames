package de.benkralex.partygames.games.common.domain

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class GameInformation (
    val name: StringResource,
    val description: StringResource,
    val author: StringResource,
    val image: String,
    val color: Color,
)