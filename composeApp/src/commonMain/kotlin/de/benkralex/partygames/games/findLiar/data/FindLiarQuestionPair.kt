package de.benkralex.partygames.games.findLiar.data

import androidx.compose.runtime.Composable
import de.benkralex.partygames.games.common.domain.Difficulty
import kotlinx.serialization.Serializable
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

@Serializable
data class FindLiarQuestionPair(
    val mainQuestion: String,
    val liarQuestion: String,
    val topic: String,
    val difficulty: Difficulty,
)

@Composable
fun getDefaultQuestionSet(): List<FindLiarQuestionPair> {
    return listOf(
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_favorite_color),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_favorite_color),
            topic = stringResource(Res.string.find_liar_data_topic_opinion),
            difficulty = Difficulty.TRIVIAL,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_best_movie),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_best_movie),
            topic = stringResource(Res.string.find_liar_data_topic_opinion),
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_usual_breakfast),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_usual_breakfast),
            topic = stringResource(Res.string.find_liar_data_topic_personal),
            difficulty = Difficulty.EXTRA_EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_hidden_talent),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_hidden_talent),
            topic = stringResource(Res.string.find_liar_data_topic_ability),
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_most_used_app),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_most_used_app),
            topic = stringResource(Res.string.find_liar_data_topic_personal),
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_dream_job),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_dream_job),
            topic = stringResource(Res.string.find_liar_data_topic_opinion),
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_best_school_subject),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_best_school_subject),
            topic = stringResource(Res.string.find_liar_data_topic_ability),
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_most_visited_place),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_most_visited_place),
            topic = stringResource(Res.string.find_liar_data_topic_personal),
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_favorite_drink),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_favorite_drink),
            topic = stringResource(Res.string.find_liar_data_topic_opinion),
            difficulty = Difficulty.TRIVIAL,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_favorite_animal),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_favorite_animal),
            topic = stringResource(Res.string.find_liar_data_topic_opinion),
            difficulty = Difficulty.TRIVIAL,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_most_proud_of),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_most_proud_of),
            topic = stringResource(Res.string.find_liar_data_topic_personal),
            difficulty = Difficulty.CHALLENGING,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_best_weekend_activity),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_best_weekend_activity),
            topic = stringResource(Res.string.find_liar_data_topic_personal),
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_best_gift_received),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_best_gift_received),
            topic = stringResource(Res.string.find_liar_data_topic_personal),
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_favorite_holiday),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_favorite_holiday),
            topic = stringResource(Res.string.find_liar_data_topic_opinion),
            difficulty = Difficulty.TRIVIAL,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_most_important_value),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_most_important_value),
            topic = stringResource(Res.string.find_liar_data_topic_opinion),
            difficulty = Difficulty.HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_favorite_music),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_favorite_music),
            topic = stringResource(Res.string.find_liar_data_topic_opinion),
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_role_model),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_role_model),
            topic = stringResource(Res.string.find_liar_data_topic_personal),
            difficulty = Difficulty.CHALLENGING,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_favorite_outfit),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_favorite_outfit),
            topic = stringResource(Res.string.find_liar_data_topic_personal),
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_favorite_place_at_home),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_favorite_place_at_home),
            topic = stringResource(Res.string.find_liar_data_topic_personal),
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = stringResource(Res.string.find_liar_data_question_past_mistake),
            liarQuestion = stringResource(Res.string.find_liar_data_liar_question_past_mistake),
            topic = stringResource(Res.string.find_liar_data_topic_personal),
            difficulty = Difficulty.VERY_HARD,
        ),

        )
}

@Composable
fun getTopics(): List<String> {
    return getDefaultQuestionSet().map { it.topic }.toSet().toList()
}