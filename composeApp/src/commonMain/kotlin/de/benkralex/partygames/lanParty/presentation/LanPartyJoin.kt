package de.benkralex.partygames.lanParty.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.app.networkManager
import de.benkralex.partygames.lanParty.domain.IPAddress
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanPartyJoin(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    viewModel: LanPartyJoinViewModel = LanPartyJoinViewModel()
) {
    Scaffold (
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("Join LAN-Party")
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
                .padding(innerPadding)
                .fillMaxSize()
                .padding(8.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                value = viewModel.hostString,
                singleLine = true,
                label = {
                    Text(
                        "Host-Addresse"
                    )
                },
                readOnly = viewModel.isConnected,
                onValueChange = { viewModel.hostString = it },
                isError = !viewModel.hostStringValid,
            )
            AnimatedVisibility(!networkManager.active) {
                Button(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    onClick = {
                        if (viewModel.isConnected)
                            networkManager.disconnect()
                        else
                            viewModel.setHost(viewModel.hostString)
                    },
                ) {
                    if (viewModel.isConnected)
                        Text("Trennen")
                    else
                        Text("Verbindend")
                }
            }
            AnimatedVisibility(viewModel.isConnected) {
                OutlinedTextField (
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    value = viewModel.name,
                    singleLine = true,
                    label = {
                        Text(
                            "Name"
                        )
                    },
                    readOnly = networkManager.active,
                    onValueChange = { viewModel.name = it },
                    isError = (viewModel.nameUsed || !viewModel.nameValid) && !networkManager.active,
                )
            }
            AnimatedVisibility(viewModel.isConnected) {
                Button(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    onClick = {
                        if (networkManager.active) {
                            networkManager.disconnect()
                        } else {
                            viewModel.join()
                        }
                    },
                    content = {
                        Row (
                            modifier = Modifier
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (networkManager.active) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.Logout,
                                    contentDescription = null,
                                )
                                Text("Verlassen")
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.PlayArrow,
                                    contentDescription = null,
                                )
                                Text("Beitreten")
                            }
                        }
                    },
                )
            }

            AnimatedVisibility(!viewModel.isConnected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    QRCodeScanner { viewModel.hostString = it }
                }
            }
        }
    }
}