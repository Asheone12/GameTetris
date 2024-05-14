package com.muen.gametetris.ui.screens.tetris

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.muen.gametetris.R
import com.muen.gametetris.game.Controller
import com.muen.gametetris.game.Direction
import com.muen.gametetris.settings.SettingsHandler
import com.muen.gametetris.ui.components.TetrisGrid
import com.muen.gametetris.ui.components.TetrisText
import com.muen.gametetris.ui.components.UpcomingTetrominoesBox
import com.muen.gametetris.ui.theme.LocalColors
import kotlinx.coroutines.delay

/* AndroidTetris TetrisScreen: the composable that actually displays the gameplay */

@Composable
fun TetrisScreen() {
    val viewModel by remember { mutableStateOf(TetrisScreenViewModel()) }
    var isGhostEnabled by remember { mutableStateOf(viewModel.isGhostEnabled()) }
    val colors = LocalColors.current.colors
    Column(
        modifier = Modifier
            .background(colors.BackgroundColor)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.weight(0.35f)
            ) {
                // Left side column, contains upcoming tetrominoes grid, stats, ghost chip
                UpcomingTetrominoesBox(
                    width = 80.dp,
                    height = 150.dp,
                    viewModel = viewModel,
                    modifier = Modifier.padding(top = 16.dp,bottom = 16.dp, start = 16.dp)
                )
                Stats(viewModel)
                TimeText(viewModel)
                //重新开始
                IconButton(
                    onClick = { viewModel.restartGame() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 16.dp)
                        .border(
                            BorderStroke(1.dp, colors.ForegroundColor),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.restart),
                            contentDescription = "重新开始游戏",
                            tint = colors.ForegroundColor
                        )
                        TetrisText(
                            text = "重玩",
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
                // Pause stuff 暂停
                IconButton(
                    onClick = {
                        if (viewModel.gameState.gamePaused) {
                            viewModel.unpauseGame()
                        } else {
                            viewModel.pauseGame()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 16.dp)
                        .border(
                            BorderStroke(1.dp, colors.ForegroundColor),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val icon =
                            if (viewModel.gameState.gamePaused) R.drawable.play else R.drawable.pause
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = "暂停游戏",
                            tint = colors.ForegroundColor
                        )
                        TetrisText(
                            text = if (viewModel.gameState.gamePaused) "继续" else "暂停",
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.weight(0.65f).padding(top = 16.dp, bottom = 16.dp,end = 16.dp),
                horizontalAlignment = Alignment.End
            ) {
                // Right side column, contains the tetris game grid 右侧屏幕
                val fraction = 0.8f
                TetrisGrid(
                    width = 180.dp,
                    height = 396.dp,
                    viewModel = viewModel,
                    gridWidth = SettingsHandler.getGridWidth(),
                    gridHeight = SettingsHandler.getGridHeight()
                ){
                    when(it){
                        Controller.Up -> viewModel.rotate()
                        Controller.Left -> viewModel.move(Direction.Left)
                        Controller.Right -> viewModel.move(Direction.Right)
                        Controller.Down -> viewModel.move(Direction.Down)
                        Controller.DoubleClick ->viewModel.move(Direction.Down)
                    }
                }

            }
        }
    }
}

@Composable
private fun Stats(
    viewModel: TetrisScreenViewModel,
    modifier: Modifier = Modifier
) {
    val gameStats by remember { derivedStateOf { viewModel.statsState } }
    Column(modifier = modifier) {
        //TetrisText("Lines: ${gameStats.lines}")
        TetrisText("分数: ${gameStats.score}")
        //TetrisText("难度: ${gameStats.level}")
    }
}

@Composable
fun TimeText(viewModel: TetrisScreenViewModel) {
    val keepCounting = viewModel.gameState.gameRunning && !viewModel.gameState.gamePaused
    var count by remember { mutableStateOf(viewModel.gameTimeSeconds) }
    val convertedCount = when (count) {
        in 0..9 -> "00:0$count"
        in 10..60 -> "00:$count"
        else -> {
            val seconds = count % 60
            val minutes = (count - seconds) / 60
            val minutesString = if (minutes < 10) "0$minutes" else "$minutes"
            val secondsString = if (seconds < 10) "0$seconds" else "$seconds"
            "$minutesString:$secondsString"
        }
    }
    LaunchedEffect(keepCounting) {
        while(keepCounting) {
            delay(1000)
            count = viewModel.increaseGameTimer()
        }
    }
    TetrisText(text = "时间: $convertedCount")
}