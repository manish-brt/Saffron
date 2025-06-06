package com.app.saffron.ui.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class LoginStep {
    PHONE_NUMBER,
    OTP_VERIFICATION
}

@Composable
@Preview
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var currentStep by remember { mutableStateOf(LoginStep.PHONE_NUMBER) }
    var isLoading by remember { mutableStateOf(false) }
    var showWelcomeAnimation by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val otpFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        delay(300)
        showWelcomeAnimation = true
    }

    val isPhoneValid = phoneNumber.length == 10 && phoneNumber.all { it.isDigit() }
    val isOtpValid = otp.length == 4 && otp.all { it.isDigit() }

    Box(
        modifier = Modifier.fillMaxSize().background(
            Color(0xf6b26baa)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Welcome section with animation
            AnimatedVisibility(
                visible = showWelcomeAnimation,
                enter = slideInVertically(
                    initialOffsetY = { -100 },
                    animationSpec = tween(800, easing = LinearOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(800))
            ) {
                WelcomeHeader(currentStep)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Login form card
            AnimatedVisibility(
                visible = showWelcomeAnimation,
                enter = slideInVertically(
                    initialOffsetY = { 100 },
                    animationSpec = tween(800, delayMillis = 200, easing = LinearOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(800, delayMillis = 200))
            ) {
                LoginCard(
                    phoneNumber = phoneNumber,
                    otp = otp,
                    currentStep = currentStep,
                    isLoading = isLoading,
                    isPhoneValid = isPhoneValid,
                    isOtpValid = isOtpValid,
                    onPhoneNumberChange = {
                        if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                            phoneNumber = it
                        }
                    },
                    onOtpChange = {
                        if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                            otp = it
                        }
                    },
                    onSendOtpClick = {
                        if (isPhoneValid) {
                            isLoading = true
                            focusManager.clearFocus()
                            CoroutineScope(Dispatchers.IO).launch {
                                delay(1500)
                                isLoading = false
                                currentStep = LoginStep.OTP_VERIFICATION
                            }
                        }
                    },
                    onVerifyOtpClick = {
                        if (isOtpValid) {
                            isLoading = true
                            focusManager.clearFocus()
                            onLoginSuccess()
                        }
                    },
                    onBackClick = {
                        currentStep = LoginStep.PHONE_NUMBER
                        otp = ""
                    },
                    otpFocusRequester = otpFocusRequester
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WelcomeHeader(currentStep: LoginStep) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedContent(
            targetState = currentStep,
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { if (targetState == LoginStep.OTP_VERIFICATION) 300 else -300 }
                ) + fadeIn() with slideOutHorizontally(
                    targetOffsetX = { if (targetState == LoginStep.OTP_VERIFICATION) -300 else 300 }
                ) + fadeOut()
            }
        ) { step ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = when (step) {
                        LoginStep.PHONE_NUMBER -> "Welcome Back"
                        LoginStep.OTP_VERIFICATION -> "Verify OTP"
                    },
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = when (step) {
                        LoginStep.PHONE_NUMBER -> "Enter your phone number to continue"
                        LoginStep.OTP_VERIFICATION -> "Enter the 4-digit code sent to your phone"
                    },
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginCard(
    phoneNumber: String,
    otp: String,
    currentStep: LoginStep,
    isLoading: Boolean,
    isPhoneValid: Boolean,
    isOtpValid: Boolean,
    onPhoneNumberChange: (String) -> Unit,
    onOtpChange: (String) -> Unit,
    onSendOtpClick: () -> Unit,
    onVerifyOtpClick: () -> Unit,
    onBackClick: () -> Unit,
    otpFocusRequester: FocusRequester
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        AnimatedContent(
            targetState = currentStep,
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { if (targetState == LoginStep.OTP_VERIFICATION) 300 else -300 }
                ) + fadeIn() with slideOutHorizontally(
                    targetOffsetX = { if (targetState == LoginStep.OTP_VERIFICATION) -300 else 300 }
                ) + fadeOut()
            }
        ) { step ->
            when (step) {
                LoginStep.PHONE_NUMBER -> PhoneNumberStep(
                    phoneNumber = phoneNumber,
                    onPhoneNumberChange = onPhoneNumberChange,
                    onSendOtpClick = onSendOtpClick,
                    isLoading = isLoading,
                    isPhoneValid = isPhoneValid
                )

                LoginStep.OTP_VERIFICATION -> OtpVerificationStep(
                    otp = otp,
                    phoneNumber = phoneNumber,
                    onOtpChange = onOtpChange,
                    onVerifyOtpClick = onVerifyOtpClick,
                    onBackClick = onBackClick,
                    isLoading = isLoading,
                    isOtpValid = isOtpValid,
                    otpFocusRequester = otpFocusRequester
                )
            }
        }
    }
}

@Composable
fun PhoneNumberStep(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    onSendOtpClick: () -> Unit,
    isLoading: Boolean,
    isPhoneValid: Boolean
) {
    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Phone number field
        CustomTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            label = "Phone Number",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { if (isPhoneValid) onSendOtpClick() }
            ),
            placeholder = "Enter 10-digit phone number"
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Send OTP button
        AnimatedLoginButton(
            text = "Send OTP",
            isLoading = isLoading,
            onClick = onSendOtpClick,
            enabled = isPhoneValid
        )
    }
}

@Composable
fun OtpVerificationStep(
    otp: String,
    phoneNumber: String,
    onOtpChange: (String) -> Unit,
    onVerifyOtpClick: () -> Unit,
    onBackClick: () -> Unit,
    isLoading: Boolean,
    isOtpValid: Boolean,
    otpFocusRequester: FocusRequester
) {
    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextButton(onClick = onBackClick) {
                Text("Go Back")
            }
        }
        Text(
            text = "Code sent to +91 $phoneNumber",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomTextField(
            value = otp,
            onValueChange = onOtpChange,
            label = "Enter OTP",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { if (isOtpValid) onVerifyOtpClick() }
            ),
            placeholder = "4-digit code",
            modifier = Modifier.focusRequester(otpFocusRequester),

            )

        Spacer(modifier = Modifier.height(16.dp))
        AnimatedLoginButton(
            text = "Verify OTP",
            isLoading = isLoading,
            onClick = onVerifyOtpClick,
            enabled = isOtpValid
        )
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    placeholder: String = "",
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            letterSpacing = 3.sp
        ),
        label = { Text(label) },
        placeholder = { Text(placeholder, color = Color.Gray.copy(alpha = 0.6f)) },

        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF667eea),
            unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
            focusedLabelColor = Color(0xFF667eea)
        ),
        singleLine = true
    )
}

@Composable
fun AnimatedLoginButton(
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit,
    enabled: Boolean
) {
    val buttonColor by animateColorAsState(
        targetValue = if (enabled) MaterialTheme.colorScheme.primary else Color.Gray,
        animationSpec = tween(300)
    )

    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
