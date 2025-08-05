package de.benkralex.partygames.games.findLiar.data

import de.benkralex.partygames.games.common.domain.Difficulty
import org.jetbrains.compose.resources.StringResource
import partygames.composeapp.generated.resources.*

data class FindLiarQuestionPair(
    val mainQuestion: StringResource,
    val liarQuestion: StringResource,
    val topic: StringResource,
    val difficulty: Difficulty,
)

fun getDefaultQuestionSet(): List<FindLiarQuestionPair> {
    return listOf(
        // TRIVIAL (8 questions)
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_color,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_color,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.TRIVIAL,
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
            mainQuestion = Res.string.find_liar_data_question_favorite_holiday,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_holiday,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.TRIVIAL,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_season,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_season,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.TRIVIAL,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_snack,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_snack,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.TRIVIAL,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_weather,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_weather,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.TRIVIAL,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_icecream,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_icecream,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.TRIVIAL,
        ),

        // EXTRA_EASY (8 questions)
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_usual_breakfast,
            liarQuestion = Res.string.find_liar_data_liar_question_usual_breakfast,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EXTRA_EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_fruit,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_fruit,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.EXTRA_EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_dessert,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_dessert,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.EXTRA_EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_color_to_wear,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_color_to_wear,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EXTRA_EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_time_of_day,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_time_of_day,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EXTRA_EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_shoes,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_shoes,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EXTRA_EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_weekday,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_weekday,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EXTRA_EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_drink_in_summer,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_drink_in_summer,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.EXTRA_EASY,
        ),

        // EASY (8 questions)
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_movie,
            liarQuestion = Res.string.find_liar_data_liar_question_best_movie,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_most_used_app,
            liarQuestion = Res.string.find_liar_data_liar_question_most_used_app,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_school_subject,
            liarQuestion = Res.string.find_liar_data_liar_question_best_school_subject,
            topic = Res.string.find_liar_data_topic_ability,
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_weekend_activity,
            liarQuestion = Res.string.find_liar_data_liar_question_best_weekend_activity,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_music,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_music,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_place_at_home,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_place_at_home,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_hobby,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_hobby,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EASY,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_subject,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_subject,
            topic = Res.string.find_liar_data_topic_ability,
            difficulty = Difficulty.EASY,
        ),

        // MODERATE (8 questions)
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_dream_job,
            liarQuestion = Res.string.find_liar_data_liar_question_dream_job,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_most_visited_place,
            liarQuestion = Res.string.find_liar_data_liar_question_most_visited_place,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_gift_received,
            liarQuestion = Res.string.find_liar_data_liar_question_best_gift_received,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_outfit,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_outfit,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_game,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_game,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_sport,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_sport,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_pet,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_pet,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.MODERATE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_gift_given,
            liarQuestion = Res.string.find_liar_data_liar_question_best_gift_given,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.MODERATE,
        ),

        // MEDIUM (8 questions)
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_hidden_talent,
            liarQuestion = Res.string.find_liar_data_liar_question_hidden_talent,
            topic = Res.string.find_liar_data_topic_ability,
            difficulty = Difficulty.MEDIUM,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_friend_trait,
            liarQuestion = Res.string.find_liar_data_liar_question_best_friend_trait,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.MEDIUM,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_most_relaxing_activity,
            liarQuestion = Res.string.find_liar_data_liar_question_most_relaxing_activity,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.MEDIUM,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_book,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_book,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.MEDIUM,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_meal,
            liarQuestion = Res.string.find_liar_data_liar_question_best_meal,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.MEDIUM,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_smell,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_smell,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.MEDIUM,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_sound,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_sound,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.MEDIUM,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_instrument,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_instrument,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.MEDIUM,
        ),

        // CHALLENGING (8 questions)
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_most_proud_of,
            liarQuestion = Res.string.find_liar_data_liar_question_most_proud_of,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.CHALLENGING,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_role_model,
            liarQuestion = Res.string.find_liar_data_liar_question_role_model,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.CHALLENGING,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_childhood_dream,
            liarQuestion = Res.string.find_liar_data_liar_question_childhood_dream,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.CHALLENGING,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_funniest_memory,
            liarQuestion = Res.string.find_liar_data_liar_question_funniest_memory,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.CHALLENGING,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_childhood_memory,
            liarQuestion = Res.string.find_liar_data_liar_question_best_childhood_memory,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.CHALLENGING,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_teacher,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_teacher,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.CHALLENGING,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_vacation,
            liarQuestion = Res.string.find_liar_data_liar_question_best_vacation,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.CHALLENGING,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_comedian,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_comedian,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.CHALLENGING,
        ),

        // HARD (8 questions)
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_most_important_value,
            liarQuestion = Res.string.find_liar_data_liar_question_most_important_value,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_most_comfortable_place,
            liarQuestion = Res.string.find_liar_data_liar_question_most_comfortable_place,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_most_motivating_song,
            liarQuestion = Res.string.find_liar_data_liar_question_most_motivating_song,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_birthday_memory,
            liarQuestion = Res.string.find_liar_data_liar_question_best_birthday_memory,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_seat_in_class,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_seat_in_class,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_thing_to_draw,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_thing_to_draw,
            topic = Res.string.find_liar_data_topic_ability,
            difficulty = Difficulty.HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_website,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_website,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_mythical_creature,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_mythical_creature,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.HARD,
        ),

        // VERY_HARD (8 questions)
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_past_mistake,
            liarQuestion = Res.string.find_liar_data_liar_question_past_mistake,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.VERY_HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_superpower,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_superpower,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.VERY_HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_social_media,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_social_media,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.VERY_HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_seat_in_transport,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_seat_in_transport,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.VERY_HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_sleep_position,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_sleep_position,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.VERY_HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_personal_quality,
            liarQuestion = Res.string.find_liar_data_liar_question_best_personal_quality,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.VERY_HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_memory_with_friends,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_memory_with_friends,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.VERY_HARD,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_thing_about_school,
            liarQuestion = Res.string.find_liar_data_liar_question_best_thing_about_school,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.VERY_HARD,
        ),

        // EXTREME (8 questions)
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_way_to_relax,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_way_to_relax,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EXTREME,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_kind_of_day,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_kind_of_day,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EXTREME,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_job_chore,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_job_chore,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EXTREME,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_time_to_study,
            liarQuestion = Res.string.find_liar_data_liar_question_best_time_to_study,
            topic = Res.string.find_liar_data_topic_ability,
            difficulty = Difficulty.EXTREME,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_person_to_talk_to,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_person_to_talk_to,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EXTREME,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_decision_ever_made,
            liarQuestion = Res.string.find_liar_data_liar_question_best_decision_ever_made,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EXTREME,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_language,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_language,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.EXTREME,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_family_activity,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_family_activity,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.EXTREME,
        ),

        // NIGHTMARE (6 questions - remaining)
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_thing_about_yourself,
            liarQuestion = Res.string.find_liar_data_liar_question_best_thing_about_yourself,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.NIGHTMARE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_holiday_tradition,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_holiday_tradition,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.NIGHTMARE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_type_of_movie,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_type_of_movie,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.NIGHTMARE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_dream_home,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_dream_home,
            topic = Res.string.find_liar_data_topic_opinion,
            difficulty = Difficulty.NIGHTMARE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_best_thing_about_weekends,
            liarQuestion = Res.string.find_liar_data_liar_question_best_thing_about_weekends,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.NIGHTMARE,
        ),
        FindLiarQuestionPair(
            mainQuestion = Res.string.find_liar_data_question_favorite_school_event,
            liarQuestion = Res.string.find_liar_data_liar_question_favorite_school_event,
            topic = Res.string.find_liar_data_topic_personal,
            difficulty = Difficulty.NIGHTMARE,
        ),
    )
}

fun getTopics(): List<StringResource> {
    return getDefaultQuestionSet().map { it.topic }.toSet().toList()
}