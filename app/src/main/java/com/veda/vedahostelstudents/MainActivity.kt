package com.veda.vedahostelstudents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.veda.vedahostelstudents.data.repository.HostelRepository
import com.veda.vedahostelstudents.ui.navigation.AppNavGraph
import com.veda.vedahostelstudents.ui.theme.VEDAHOSTELSTUDENTSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HostelRepository.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            VEDAHOSTELSTUDENTSTheme {
                AppNavGraph()
            }
        }
    }
}
