package com.app.saffron.ui.flower

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.saffron.data.models.FlowerDto
import com.app.saffron.data.models.FlowerListState
import com.app.saffron.ui.flower.components.FlowerListItem
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import saffron.composeapp.generated.resources.Res
import saffron.composeapp.generated.resources.compose_multiplatform

@Composable
fun FlowerListScreen(
    viewModel: FlowerViewModel = koinViewModel(),
    onFlowerClick: (FlowerDto?) -> Unit,
    onProfileClick: () -> Unit
) {
    val state by viewModel.listState.collectAsStateWithLifecycle()

    FlowerList(
        state = state,
        onFlowerClick = onFlowerClick,
        onProfileClick = onProfileClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun FlowerList(
    state: FlowerListState,
    onFlowerClick: (FlowerDto?) -> Unit,
    onProfileClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Flowers")
                },
                actions = {
                    IconButton(onClick = { onProfileClick() }) {
                        Icon(
                            painter = painterResource(Res.drawable.compose_multiplatform),
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->

        Spacer(modifier = Modifier.height(100.dp))

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center).padding(innerPadding)
                )
            }
        } else {
            when {
                state.error != null -> {
                    Text(text = state.error)
                }

                state.flowers.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding).padding(top = 10.dp),
                        state = rememberLazyListState(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(
                            count = state.flowers.size,
                            key = { idx -> state.flowers[idx].id },
                            itemContent =
                                { idx ->
                                    FlowerListItem(
                                        flower = state.flowers[idx],
                                        modifier = Modifier
//                                            .widthIn(max = 700.dp)
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp)
                                            .wrapContentHeight(),
                                        onClick = {
                                            onFlowerClick(state.flowers.get(idx) ?: null)
                                        }
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}
