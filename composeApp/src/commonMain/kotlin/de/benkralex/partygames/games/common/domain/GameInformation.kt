package de.benkralex.partygames.games.common.domain

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource

@Serializable
data class GameInformation (
    @Contextual val name: StringResource,
    @Contextual val description: StringResource,
    @Contextual val author: StringResource,
    @Contextual val howToPlay: StringResource,
    val image: String,
    @Contextual val colorLightTheme: Color,
    @Contextual val colorDarkTheme: Color,
)