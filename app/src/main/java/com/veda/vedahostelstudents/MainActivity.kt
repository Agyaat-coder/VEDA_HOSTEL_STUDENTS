package com.veda.vedahostelstudents

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import com.veda.vedahostelstudents.data.repository.HostelRepository
import com.veda.vedahostelstudents.data.repository.StudentFcmManager
import com.veda.vedahostelstudents.ui.navigation.AppNavGraph
import com.veda.vedahostelstudents.ui.theme.VEDAHOSTELSTUDENTSTheme

class MainActivity : ComponentActivity() {

    private val currentIntentState = mutableStateOf<Intent?>(null)

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            StudentFcmManager.registerCurrentFcmToken(applicationContext)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HostelRepository.init(applicationContext)
        currentIntentState.value = intent

        requestNotificationPermissionIfNeeded()

        enableEdgeToEdge()
        setContent {
            val appearancePref by HostelRepository.appearancePreference.collectAsState()
            val intentState by currentIntentState
            VEDAHOSTELSTUDENTSTheme(appearancePreference = appearancePref) {
                AppNavGraph(initialIntent = intentState)
            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                StudentFcmManager.registerCurrentFcmToken(applicationContext)
            }
        } else {
            StudentFcmManager.registerCurrentFcmToken(applicationContext)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        currentIntentState.value = intent
    }
}
