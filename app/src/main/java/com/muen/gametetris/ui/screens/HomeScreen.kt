package com.muen.gametetris.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.muen.gametetris.NavDestination
import com.muen.gametetris.R
import com.muen.gametetris.game.Point
import com.muen.gametetris.settings.SettingsHandler
import com.muen.gametetris.ui.components.DropdownMenuSurface
import com.muen.gametetris.ui.components.TetrisDropdownMenuItem
import com.muen.gametetris.ui.components.TetrisDropdownMenuItemData
import com.muen.gametetris.ui.components.TetrisText
import com.muen.gametetris.ui.screens.tetris.TetrisScreenViewModel

/* AndroidTetris application entry point screen */

// Maybe use Surface here for each option?

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel by remember { mutableStateOf(TetrisScreenViewModel()) }
    var isGhostEnabled by remember { mutableStateOf(viewModel.isGhostEnabled()) }
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = {
                navController.navigate(
                    route = NavDestination.Tetris.route,
                ) {
                    launchSingleTop = true
                }
            }
        ) {
            Text("开始游戏")
        }

        //阴影
        val ghostIconTint = if (isGhostEnabled) Color.Green else Color.Red
        IconButton(
            onClick = {
                isGhostEnabled = !isGhostEnabled
                viewModel.setTheGhostEnabled(isGhostEnabled)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .border(
                    BorderStroke(1.dp, ghostIconTint),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val icon = if (isGhostEnabled) R.drawable.check else R.drawable.close
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "启用阴影",
                    tint = ghostIconTint
                )
                TetrisText(
                    text = "阴影",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
        // Grid size menu
        val gridWidth = SettingsHandler.getGridWidth()
        val gridHeight = SettingsHandler.getGridHeight()
        var gridSizeMenuExpanded by remember { mutableStateOf(false) }
        DropdownMenuSurface(
            title = stringResource(id = R.string.txt_gridSize),
            selectionText = "${gridWidth}x${gridHeight}",
            modifier = Modifier.padding(vertical = 8.dp),
            menuExpanded = gridSizeMenuExpanded,
            onMenuClick = { gridSizeMenuExpanded = !gridSizeMenuExpanded }
        ) {
            DropdownMenu(
                expanded = gridSizeMenuExpanded,
                onDismissRequest = { gridSizeMenuExpanded = false }
            ) {
                val gridSizes = listOf(
                    Point(10, 22), // Default
                    Point(15, 33),
                    Point(20, 44),
                    Point(30, 66)
                )
                gridSizes.forEach {
                    val width = it.x
                    val height = it.y
                    val selected = width == gridWidth && height == gridHeight
                    TetrisDropdownMenuItem(
                        item = TetrisDropdownMenuItemData(
                            title = "${width}x${height}",
                            selected = selected,
                            onClick = {
                                SettingsHandler.setGridWidth(width)
                                SettingsHandler.setGridHeight(height)
                                gridSizeMenuExpanded = false
                            }
                        )
                    )
                }
            }
        }

        // Game level setting menu 游戏等级
        var gameLevelMenuExpanded by remember { mutableStateOf(false) }
        val gameLevel = SettingsHandler.getGameLevel()
        DropdownMenuSurface(
            title = stringResource(id = R.string.txt_gameLevel),
            selectionText = gameLevel.toString(),
            modifier = Modifier.padding(vertical = 8.dp),
            menuExpanded = gameLevelMenuExpanded,
            onMenuClick = { gameLevelMenuExpanded = !gameLevelMenuExpanded }
        ) {
            DropdownMenu(
                expanded = gameLevelMenuExpanded,
                onDismissRequest = { gameLevelMenuExpanded = false }
            ) {
                for (i in 1 until 19) {
                    TetrisDropdownMenuItem(
                        item = TetrisDropdownMenuItemData(
                            title = i.toString(),
                            selected = i == gameLevel,
                            onClick = {
                                SettingsHandler.setGameLevel(i)
                                gameLevelMenuExpanded = false
                            }
                        )
                    )
                }
            }
        }
    }
}