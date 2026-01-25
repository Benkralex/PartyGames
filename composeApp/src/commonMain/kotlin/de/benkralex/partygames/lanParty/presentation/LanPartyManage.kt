package de.benkralex.partygames.lanParty.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.lanParty.data.getIPAddress
import de.benkralex.partygames.lanParty.domain.getServerManager
import de.benkralex.partygames.lanParty.domain.stopServer
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LanPartyManage(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    port: Int = 3333,
    hostPlays: Boolean = false,
    hostName: String = "",
) {
    var showClosingLoadingIndicator by remember { mutableStateOf(false) }
    val stopServerAndNavigateBack: () -> Unit = {
        showClosingLoadingIndicator = true
        stopServer()
        onNavigateBack()
    }
    if (showClosingLoadingIndicator) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0,0,0,50)),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }
    BackHandler {
        showClosingLoadingIndicator = true
        stopServerAndNavigateBack()
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("LAN-Party")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            showClosingLoadingIndicator = true
                            stopServerAndNavigateBack()
                        },
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
        Box (
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .padding(24.dp)
            ) {
                val isWide = maxWidth > 600.dp

                if (isWide) {
                    Card {
                        Row(
                            modifier = Modifier
                                .padding(64.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            QRCode(
                                data = "ws://${getIPAddress()}:${port}/game",
                                backgroundColor = MaterialTheme.colorScheme.primary,
                                foregroundColor = MaterialTheme.colorScheme.surface,
                                qrModifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                            )
                            Spacer(modifier = Modifier.width(64.dp))
                            Names(hostName, hostPlays)
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        QRCode(
                            data = "ws://${getIPAddress()}:${port}/game",
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            foregroundColor = MaterialTheme.colorScheme.surface,
                            qrModifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Names(hostName, hostPlays)
                    }
                }
            }
        }
    }
}

@Composable
private fun Names(hostName: String, hostPlays: Boolean) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max),
    ) {
        for (name in getServerManager()?.connections?.values ?: emptyList()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (name == hostName && hostPlays) {
                        Icon(
                            modifier = Modifier
                                .size(16.dp),
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "Host",
                        )
                    } else {
                        IconButton(
                            modifier = Modifier
                                .size(16.dp),
                            onClick = {
                                getServerManager()?.leave(name)
                            }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(16.dp),
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "Kick",
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .width(4.dp)
                    )
                    Text(name)
                }
            }
        }
    }
}