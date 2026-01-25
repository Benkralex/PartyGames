package de.benkralex.partygames.lanParty.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
expect fun QRCodeScanner(
    modifier: Modifier = Modifier,
    qrSize: Dp = 275.dp,
    onScan: (String) -> Unit = {}
)
