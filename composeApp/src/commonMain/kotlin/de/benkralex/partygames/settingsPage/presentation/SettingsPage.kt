package de.benkralex.partygames.settingsPage.presentation

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.benkralex.partygames.app.gamesRegister
import de.benkralex.partygames.settingsPage.presentation.settingsWidgets.DatasetPathSetting
import de.benkralex.partygames.settingsPage.presentation.settingsWidgets.DatasetSelection
import org.jetbrains.compose.resources.stringResource
import partygames.composeapp.generated.resources.Res
import partygames.composeapp.generated.resources.settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    onNavigateBack: () -> Unit = {},
) {
    Scaffold (
        topBar = {
            SettingsTopAppBar(
                onNavigateBack = onNavigateBack,
            )
        }
    ) { innerPadding ->
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val tabsList = listOf(
            TabData(
                title = stringResource(Res.string.settings),
                content = {
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .scrollable(
                                state = rememberScrollState(),
                                orientation = Orientation.Vertical,
                            ),
                    ) {
                        DatasetPathSetting()
                        //DatasetSelection()
                    }
                },
            ),
        ).plus(gamesRegister.filter { it.settingsWidget != null }.map {
            TabData(
                title = stringResource(it.information.name),
                content = {
                    it.settingsWidget!!(Modifier.fillMaxSize())
                },
            )
        })
        val pagerState = rememberPagerState {
            tabsList.size
        }
        LaunchedEffect(selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress) {
                selectedTabIndex = pagerState.currentPage
            }
        }
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
        ) {
            ScrollableTabRow (
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .fillMaxWidth(),
                divider = {},
            ) {
                tabsList.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = tab.title,
                            )
                        }
                    )
                }
            }
            HorizontalDivider()
            HorizontalPager (
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.Top,
            ) { page ->
                Box (
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    tabsList[page].content()
                }
            }
        }
    }
}

data class TabData(
    val title: String,
    val content: @Composable () -> Unit
)

