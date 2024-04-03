package com.muen.gametetris.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.muen.gametetris.BuildConfig
import com.muen.gametetris.R
import com.muen.gametetris.ui.components.AppBar
import com.muen.gametetris.ui.components.BodyLarge
import com.muen.gametetris.ui.components.DisplayLarge
import com.muen.gametetris.ui.components.TitleLarge
import com.muen.gametetris.ui.theme.border2dp
import com.muen.gametetris.ui.theme.padding16dp
import com.muen.gametetris.ui.theme.padding8dp

@Composable
fun AboutScreen(navController: NavHostController) {
    AppBar(
        title = stringResource(R.string.title_about),
        onBackClicked = { navController.popBackStack() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = padding16dp,
                    start = padding16dp,
                    end = padding16dp
                )
                .border(width = border2dp, color = MaterialTheme.colorScheme.onBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DisplayLarge(text = stringResource(id = R.string.app_name))
            TitleLarge(
                modifier = Modifier.padding(padding8dp),
                text = BuildConfig.VERSION_NAME
            )
            BodyLarge(
                modifier = Modifier.padding(padding16dp),
                text = stringResource(R.string.about_game),
                textAlign = TextAlign.Justify
            )
        }
    }
}