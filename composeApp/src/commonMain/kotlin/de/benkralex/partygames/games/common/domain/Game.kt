package de.benkralex.partygames.games.common.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface Game {
    //-------------
    // Information
    //-------------
    val information: GameInformation

    //-------------
    // Settings
    //-------------
    val settings: Map<String, Any?>

    //-------------
    // UI
    //-------------
    val setupWidget: @Composable (modifier: Modifier) -> Unit

    //-------------
    // AI (Coming soon)
    //-------------
    /*val generateContentWithAI: Boolean
    val prompt: String
    val argsNames: List<String>

    fun getPrompt(args: List<String>): String {
        if (args.size != argsNames.size) {
            throw IllegalArgumentException("Args count must be ${argsNames.size} but was ${args.size}")
        }
        val finalPrompt = prompt
        for ((name, arg) in argsNames.zip(args)) {
            finalPrompt.replace("{$name}", arg)
        }
        finalPrompt.replace("\\}", "}")
        finalPrompt.replace("\\{", "{")
        return finalPrompt
    }*/
}