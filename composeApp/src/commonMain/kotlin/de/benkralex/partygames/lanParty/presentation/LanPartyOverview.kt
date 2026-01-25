package de.benkralex.partygames.lanParty.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Lan
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.app.PLATFORM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanPartyOverview(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onNavigateToHostGame: () -> Unit = {},
    onNavigateToJoinGame: () -> Unit = {},
) {
    Scaffold (
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("LAN-Party")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "",
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding),
        ) {
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(CardDefaults.shape)
                    .clickable {
                        if (PLATFORM != "WEB") onNavigateToHostGame()
                    },
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .size(50.dp),
                        imageVector = Icons.Outlined.Lan,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Column (
                        modifier = Modifier
                            .weight(1f),
                    ) {
                        Text(
                            text = "Spiel hosten",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        if (PLATFORM != "WEB") {
                            Text(
                                text = "Starte einen lokalen Server, dem andere im selben Netzwerk beitreten können.",
                            )
                        } else {
                            Text(
                                text = "Lade dir die App für Android oder den PC runter, um ein Spiel zu hosten"
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                        contentDescription = null,
                    )
                }
            }
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(CardDefaults.shape)
                    .clickable {
                        onNavigateToJoinGame()
                    },
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .size(50.dp),
                        imageVector = Icons.Outlined.QrCodeScanner,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Column (
                        modifier = Modifier
                            .weight(1f),
                    ) {
                        Text(
                            text = "Spiel beitreten",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = "Scanne einen QR-Code oder gib eine Addresse ein, um einem lokalen Server beizutreten",
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
