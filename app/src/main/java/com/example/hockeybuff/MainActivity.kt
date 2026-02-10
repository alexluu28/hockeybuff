package com.example.hockeybuff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hockeybuff.model.Score
import com.example.hockeybuff.ui.boxscore.BoxScoreScreen
import com.example.hockeybuff.ui.sharks.SharksScreen
import com.example.hockeybuff.ui.theme.HockeybuffTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HockeybuffTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "sharks",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("sharks") {
                            SharksScreen(onScoreClick = { score ->
                                navController.currentBackStackEntry?.savedStateHandle?.set("score", score)
                                navController.navigate("boxscore")
                            })
                        }
                        composable("boxscore") { 
                            val score = navController.previousBackStackEntry?.savedStateHandle?.get<Score>("score")
                            if (score != null) {
                                BoxScoreScreen(score = score)
                            }
                        }
                    }
                }
            }
        }
    }
}
