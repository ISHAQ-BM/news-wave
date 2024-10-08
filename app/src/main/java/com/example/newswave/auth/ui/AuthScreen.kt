package com.example.newswave.auth.ui

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newswave.R
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun AuthRoute(
    authViewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val msg = stringResource(id = R.string.no_internet)

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            try {
                val credentials =
                    authViewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                val googleIdToken = credentials.googleIdToken
                val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
                authViewModel.signUserWithCredential(googleCredentials)
            } catch (it: ApiException) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

        }
    }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    val uiState by authViewModel.uiState.collectAsStateWithLifecycle()

    AuthScreen(
        uiState = uiState,
        onLoginSuccess = onLoginSuccess,
        onGoogleLoginClick = { authViewModel.oneTapSignIn { it -> launch(it) } },
        modifier = modifier
    )

}


@Composable
fun AuthScreen(
    uiState: AuthUiState,
    modifier: Modifier = Modifier,
    onLoginSuccess: (Boolean) -> Unit,
    onGoogleLoginClick: () -> Unit
) {

    val context = LocalContext.current
    if (uiState.generalMessage != null)
        Toast.makeText(
            context,
            uiState.generalMessage.asString(LocalContext.current), Toast.LENGTH_SHORT
        ).show()

    if (uiState.isLoginSuccessful == true)
        onLoginSuccess(uiState.isNewUser)


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 32.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val infiniteTransition = rememberInfiniteTransition()
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 3000,
                    easing = FastOutLinearInEasing,
                ),
            ),
            label = ""
        )
        Icon(
            painter = painterResource(id = R.drawable.logo_icon),
            contentDescription = null,
            modifier = modifier
                .size(145.dp)
                .then(if (uiState.isLoginSuccessful == null) modifier.rotate(rotation) else modifier),
            tint = Color.White
        )
        var visible by remember { mutableStateOf(false) }
        if (uiState.isLoginSuccessful == false)
            visible = true


        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(
                initialOffsetY = {
                    it / 4
                },
            ),
            exit = fadeOut()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 32.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = stringResource(id = R.string.make_it_easy),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = { onGoogleLoginClick() },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(Color.White),
                ) {
                    if (uiState.isLoading)
                        CircularProgressIndicator(
                            modifier = Modifier.size(28.dp)
                        )
                    else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .border(1.dp, Color.Gray, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_google),
                                    contentDescription = "Google Icon",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(16.dp),

                                    )
                            }
                            Spacer(modifier = modifier.width(16.dp))
                            Text(
                                text = stringResource(id = R.string.sign_with_google),
                                color = Color.DarkGray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }


                }
            }

        }

    }


}


