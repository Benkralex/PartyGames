package de.benkralex.partygames.games.truthOrDare.domain

import de.benkralex.partygames.datasets.Dataset
import de.benkralex.partygames.games.common.domain.TranslatableString

class TruthOrDareDataset(
    override val uid: String,
    override var active: Boolean,
    override val title: TranslatableString,
    override val description: TranslatableString,
    override val author: TranslatableString
) : Dataset