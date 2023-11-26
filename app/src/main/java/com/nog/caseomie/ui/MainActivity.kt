package com.nog.caseomie.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.nog.caseomie.navigation.CaseOmieApp
import com.nog.caseomie.ui.theme.CaseOmieTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaseOmieTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CaseOmieApp()
                }
            }
        }
    }
}