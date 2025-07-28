package de.benkralex.partygames.games.common.domain

interface Game {
    //-------------
    // Information
    //-------------
    val information: GameInformation

    //-------------
    // Settings
    //-------------
    val settings: Map<String, Any>

    //-------------
    // AI (Coming soon)
    //-------------
    val generateContentWithAI: Boolean
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
    }
}