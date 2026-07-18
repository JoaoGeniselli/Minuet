package com.dosei.music.repertoire.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.dosei.music.repertoire.ui.setlist.SetListsScreen
import com.dosei.music.repertoire.ui.song.AddSongScreen
import com.dosei.music.repertoire.ui.song.SongDetailScreen
import com.dosei.music.repertoire.ui.song.SongListScreen

@Composable
fun MinuetNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Routes.Songs, modifier = modifier) {
        composable<Routes.Songs> {
            SongListScreen(
                onSongClick = { id -> navController.navigate(Routes.SongDetail(id)) },
                onAddSongClick = { navController.navigate(Routes.AddSong) },
            )
        }
        composable<Routes.SetLists> {
            SetListsScreen()
        }
        composable<Routes.SongDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<Routes.SongDetail>()
            SongDetailScreen(id = route.id, onBack = { navController.popBackStack() })
        }
        composable<Routes.AddSong> {
            AddSongScreen(onBack = { navController.popBackStack() })
        }
    }
}
