package de.benkralex.partygames.lanParty.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanPartyHost(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    viewModel: LanPartyHostViewModel = LanPartyHostViewModel(),
    onGameStarted: (Int, Boolean, String) -> Unit = { port, hostPlays, name -> },
) {
    Scaffold (
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("Host LAN-Party")
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
        val coroutineScope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = viewModel.portStr,
                onValueChange = { viewModel.changePort(it) },
                label = { Text("Port") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword
                ),
                isError = viewModel.isPortError,
                prefix = { Text(viewModel.ipAddress.toString() + ":") }
            )
            Row (
                modifier = Modifier
                    .clickable {
                        viewModel.hostPlays = !viewModel.hostPlays
                    },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = "Selbst mitspielen"
                )
                Switch(
                    checked = viewModel.hostPlays,
                    onCheckedChange = { viewModel.hostPlays = it }
                )
            }
            AnimatedVisibility(viewModel.hostPlays) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = viewModel.name,
                    onValueChange = { viewModel.name = it },
                    label = { Text("Name") },
                    singleLine = true,
                    isError = viewModel.name.isBlank(),
                )
            }
            Spacer(Modifier.weight(1f))
            SnackbarHost(hostState = snackbarHostState)
            AnimatedVisibility(viewModel.noErrors) {
                Button(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .imePadding(),
                    onClick = {
                        viewModel.startLanParty(
                            onSuccess = {
                                onGameStarted(viewModel.port, viewModel.hostPlays, viewModel.name)
                            },
                            onError = {
                                Napier.e("Error")
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Error while starting Server",
                                        withDismissAction = true,
                                    )
                                }
                            }
                        )
                    },
                    content = {
                        Row (
                            modifier = Modifier
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (viewModel.startingServer) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(24.dp),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.PlayArrow,
                                    contentDescription = null,
                                )
                            }
                            Text(
                                text = "Start"
                            )
                        }
                    },
                )
            }
        }
    }
}