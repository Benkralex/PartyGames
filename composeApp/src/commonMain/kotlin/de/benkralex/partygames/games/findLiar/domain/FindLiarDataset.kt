package de.benkralex.partygames.games.findLiar.domain

import de.benkralex.partygames.datasets.Dataset
import de.benkralex.partygames.games.common.domain.TranslatableString

data class FindLiarDataset (
    override val uid: String,
    override val title: TranslatableString,
    override val description: TranslatableString,
    override val author: TranslatableString,
    val topics: Map<String, TranslatableString>,
    val questionPairs: List<FindLiarQuestionPair>,
) : Dataset