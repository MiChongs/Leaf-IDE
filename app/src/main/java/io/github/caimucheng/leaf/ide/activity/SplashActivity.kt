package io.github.caimucheng.leaf.ide.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.caimucheng.leaf.common.component.LaunchMode
import io.github.caimucheng.leaf.common.component.LeafApp
import io.github.caimucheng.leaf.common.component.PrivacyPolicy
import io.github.caimucheng.leaf.common.component.UserAgreement
import io.github.caimucheng.leaf.common.ui.theme.LeafIDETheme
import io.github.caimucheng.leaf.common.util.LAUNCH_MODE
import io.github.caimucheng.leaf.common.util.setupApp
import io.github.caimucheng.leaf.common.util.setupRootApp
import io.github.caimucheng.leaf.common.util.sharedPreferences
import io.github.caimucheng.leaf.ide.R
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {

    companion object {
        private const val MAX_STEP = 2
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val launchModeValue = sharedPreferences.getString(LAUNCH_MODE, null)
        if (launchModeValue != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContent {
            LeafIDETheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var titleResId by rememberSaveable {
                        mutableIntStateOf(R.string.privacy_policy)
                    }
                    var step by rememberSaveable {
                        mutableIntStateOf(0)
                    }
                    var launchMode by rememberSaveable {
                        mutableStateOf("external")
                    }
                    val coroutineScope = rememberCoroutineScope()
                    val snackbarHostState = remember { SnackbarHostState() }
                    val readAndWritePermissionDenied =
                        stringResource(id = R.string.read_and_write_permission_denied)
                    val readPermissionDenied = stringResource(id = R.string.read_permission_denied)
                    val writePermissionDenied =
                        stringResource(id = R.string.write_permission_denied)
                    val rootPermissionDenied = stringResource(id = R.string.root_permission_denied)
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestMultiplePermissions(),
                        onResult = {
                            when {
                                !it[Manifest.permission.READ_EXTERNAL_STORAGE]!! && !it[Manifest.permission.WRITE_EXTERNAL_STORAGE]!! -> {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(readAndWritePermissionDenied, withDismissAction = true)
                                    }
                                }

                                !it[Manifest.permission.READ_EXTERNAL_STORAGE]!! -> {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(readPermissionDenied, withDismissAction = true)
                                    }
                                }

                                !it[Manifest.permission.WRITE_EXTERNAL_STORAGE]!! -> {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(writePermissionDenied, withDismissAction = true)
                                    }
                                }

                                else -> {
                                    setupApp(launchMode, MainActivity::class.java)
                                }
                            }
                        }
                    )
                    val navController = rememberNavController()
                    LeafApp(
                        title = stringResource(id = titleResId),
                        snackbarHost = {
                            SnackbarHost(snackbarHostState)
                        },
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(it)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    NavHost(
                                        navController = navController,
                                        startDestination = "privacyPolicy"
                                    ) {
                                        composable("privacyPolicy") {
                                            titleResId = R.string.privacy_policy
                                            step = 0
                                            PrivacyPolicy()
                                        }
                                        composable("userAgreement") {
                                            titleResId = R.string.user_agreement
                                            step = 1
                                            UserAgreement()
                                        }
                                        composable("launchMode") {
                                            titleResId = R.string.launch_mode
                                            step = 2
                                            LaunchMode(launchMode) { type ->
                                                launchMode = type
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        bottomBar = {
                            BottomAppBar {
                                FilledTonalIconButton(
                                    onClick = {
                                        when (step) {
                                            0 -> {
                                                finish()
                                            }

                                            1 -> {
                                                navController.popBackStack()
                                            }

                                            else -> {
                                                navController.popBackStack()
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .padding(start = 20.dp)
                                        .fillMaxHeight()
                                        .padding(top = 15.dp, bottom = 15.dp)
                                ) {
                                    AnimatedContent(
                                        targetState = step,
                                        label = "AnimatedCloseButton"
                                    ) {
                                        Icon(
                                            imageVector = if (it == 0) Icons.Filled.Close else Icons.Filled.ArrowBack,
                                            contentDescription = stringResource(
                                                id = if (it == 0) R.string.refuse else R.string.previous
                                            )
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                                FilledIconButton(
                                    onClick = {
                                        when (step) {
                                            0 -> {
                                                navController.navigate("userAgreement")
                                            }

                                            1 -> {
                                                navController.navigate("launchMode")
                                            }

                                            else -> {
                                                when (launchMode) {
                                                    "external" -> launcher.launch(
                                                        arrayOf(
                                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                                                        )
                                                    )

                                                    "internal" -> {
                                                        setupApp(
                                                            launchMode,
                                                            MainActivity::class.java
                                                        )
                                                    }

                                                    "root" -> {
                                                        setupRootApp(
                                                            launchMode,
                                                            MainActivity::class.java
                                                        ) {
                                                            coroutineScope.launch {
                                                                snackbarHostState.showSnackbar(
                                                                    rootPermissionDenied,
                                                                    withDismissAction = true
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .padding(end = 20.dp)
                                        .fillMaxHeight()
                                        .padding(top = 15.dp, bottom = 15.dp)
                                ) {
                                    AnimatedContent(
                                        targetState = step,
                                        label = "AnimatedAllowButton"
                                    ) {
                                        Icon(
                                            imageVector = if (it < MAX_STEP) Icons.Filled.ArrowForward else Icons.Filled.Done,
                                            contentDescription = stringResource(
                                                id = if (it < MAX_STEP) R.string.next else R.string.allow
                                            )
                                        )
                                    }
                                }
                            }
                        })
                }
            }
        }
    }

}