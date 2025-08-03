package de.benkralex.partygames.games.findLiar.data

import androidx.compose.runtime.Composable
import de.benkralex.partygames.games.common.domain.Difficulty
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.find_liar_data_liar_question_best_gift_received
import partygames.composeapp.generated.resources.find_liar_data_liar_question_best_movie
import partygames.composeapp.generated.resources.find_liar_data_liar_question_best_school_subject
import partygames.composeapp.generated.resources.find_liar_data_liar_question_best_weekend_activity
import partygames.composeapp.generated.resources.find_liar_data_liar_question_dream_job
import partygames.composeapp.generated.resources.find_liar_data_liar_question_favorite_animal
import partygames.composeapp.generated.resources.find_liar_data_liar_question_favorite_color
import partygames.composeapp.generated.resources.find_liar_data_liar_question_favorite_drink
import partygames.composeapp.generated.resources.find_liar_data_liar_question_favorite_holiday
import partygames.composeapp.generated.resources.find_liar_data_liar_question_favorite_music
import partygames.composeapp.generated.resources.find_liar_data_liar_question_favorite_outfit
import partygames.composeapp.generated.resources.find_liar_data_liar_question_favorite_place_at_home
import partygames.composeapp.generated.resources.find_liar_data_liar_question_hidden_talent
import partygames.composeapp.generated.resources.find_liar_data_liar_question_most_important_value
import partygames.composeapp.generated.resources.find_liar_data_liar_question_most_proud_of
import partygames.composeapp.generated.resources.find_liar_data_liar_question_most_used_app
import partygames.composeapp.generated.resources.find_liar_data_liar_question_most_visited_place
import partygames.composeapp.generated.resources.find_liar_data_liar_question_past_mistake
import partygames.composeapp.generated.resources.find_liar_data_liar_question_role_model
import partygames.composeapp.generated.resources.find_liar_data_liar_question_usual_breakfast
import partygames.composeapp.generated.resources.find_liar_data_question_best_gift_received
import partygames.composeapp.generated.resources.find_liar_data_question_best_movie
import partygames.composeapp.generated.resources.find_liar_data_question_best_school_subject
import partygames.composeapp.generated.resources.find_liar_data_question_best_weekend_activity
import partygames.composeapp.generated.resources.find_liar_data_question_dream_job
import partygames.composeapp.generated.resources.find_liar_data_question_favorite_animal
import partygames.composeapp.generated.resources.find_liar_data_question_favorite_color
import partygames.composeapp.generated.resources.find_liar_data_question_favorite_drink
import partygames.composeapp.generated.resources.find_liar_data_question_favorite_holiday
import partygames.composeapp.generated.resources.find_liar_data_question_favorite_music
import partygames.composeapp.generated.resources.find_liar_data_question_favorite_outfit
import partygames.composeapp.generated.resources.find_liar_data_question_favorite_place_at_home
import partygames.composeapp.generated.resources.find_liar_data_question_hidden_talent
import partygames.composeapp.generated.resources.find_liar_data_question_most_important_value
import partygames.composeapp.generated.resources.find_liar_data_question_most_proud_of
import partygames.composeapp.generated.resources.find_liar_data_question_most_used_app
import partygames.composeapp.generated.resources.find_liar_data_question_most_visited_place
import partygames.composeapp.generated.resources.find_liar_data_question_past_mistake
import partygames.composeapp.generated.resources.find_liar_data_question_role_model
import partygames.composeapp.generated.resources.find_liar_data_question_usual_breakfast
import partygames.composeapp.generated.resources.find_liar_data_topic_ability
import partygames.composeapp.generated.resources.find_liar_data_topic_opinion
import partygames.composeapp.generated.resources.find_liar_data_topic_personal

data class FindLiarQuestionPair(
    val mainQuestion: StringResource,
    val liarQuestion: StringResource,
    val topic: StringResource,
    val difficulty: Difficulty,
)

fun getDefaultQuestionSet(): List<FindLiarQuestionPair> {
    return listOf(
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_color,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_color,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.TRIVIAL,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_movie,
            liarQuestion = Res.string.find_liar_data_liar_question_best_movie,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_usual_breakfast,
            liarQuestion = Res.string.find_liar_data_liar_question_usual_breakfast,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EXTRA_EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_hidden_talent,
            liarQuestion = Res.string.find_liar_data_liar_question_hidden_talent,
            topic = Res.string.find_liar_data_topic_ability,
            difficulty = Difficulty.MEDIUM,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_most_used_app,
            liarQuestion = Res.string.find_liar_data_liar_question_most_used_app,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_dream_job,
            liarQuestion = Res.string.find_liar_data_liar_question_dream_job,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_school_subject,
            liarQuestion = Res.string.find_liar_data_liar_question_best_school_subject,
            topic = Res.string.find_liar_data_topic_ability,
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_most_visited_place,
            liarQuestion = Res.string.find_liar_data_liar_question_most_visited_place,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_drink,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_drink,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.TRIVIAL,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_animal,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_animal,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.TRIVIAL,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_most_proud_of,
            liarQuestion = Res.string.find_liar_data_liar_question_most_proud_of,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.CHALLENGING,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_weekend_activity,
            liarQuestion = Res.string.find_liar_data_liar_question_best_weekend_activity,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_gift_received,
            liarQuestion = Res.string.find_liar_data_liar_question_best_gift_received,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_holiday,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_holiday,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.TRIVIAL,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_most_important_value,
            liarQuestion = Res.string.find_liar_data_liar_question_most_important_value,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_music,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_music,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_role_model,
            liarQuestion = Res.string.find_liar_data_liar_question_role_model,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.CHALLENGING,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_outfit,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_outfit,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_place_at_home,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_place_at_home,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_past_mistake,
            liarQuestion = Res.string.find_liar_data_liar_question_past_mistake,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.VERY_HARD,
        ),

        )
}

fun getTopics(): List<StringResource> {
    return getDefaultQuestionSet().map { it.topic }.toSet().toList()
}