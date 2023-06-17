package com.ygs.common.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import com.ygs.common.R

@Composable
private fun createLightColors() = lightColorScheme(
    primary = colorResource(R.color.colorPrimaryLight),
    onPrimary = colorResource(R.color.colorOnPrimary),
    primaryContainer = colorResource(R.color.colorPrimary),
    onPrimaryContainer = colorResource(R.color.colorOnPrimary),
    inversePrimary = colorResource(R.color.colorSecondaryLight),
    secondary = colorResource(R.color.colorSecondary),
    secondaryContainer = colorResource(R.color.colorSecondaryDark),
    background = colorResource(R.color.backgroundLight),
    surface = colorResource(R.color.backgroundLight),
    error = colorResource(R.color.colorSecondaryDark),
    onError = colorResource(R.color.colorOnSecondary),
    onBackground = colorResource(R.color.onBackgroundLight),
    onSurface = colorResource(R.color.onBackgroundLight)
)
@Composable
private fun createDarkColors() = darkColorScheme(
    primary = colorResource(R.color.colorPrimary),
    onPrimary = colorResource(R.color.colorOnPrimary),
    primaryContainer = colorResource(R.color.colorPrimaryDark),
    onPrimaryContainer = colorResource(R.color.colorOnPrimary),
    inversePrimary = colorResource(R.color.colorSecondary),
    secondary = colorResource(R.color.colorSecondary),
    secondaryContainer = colorResource(R.color.colorSecondaryDark),
    background = colorResource(R.color.backgroundDark),
    surface = colorResource(R.color.backgroundDark),
    error = colorResource(R.color.colorSecondaryLight),
    onError = colorResource(R.color.colorOnSecondary),
    onBackground = colorResource(R.color.onBackgroundDark),
    onSurface = colorResource(R.color.onBackgroundDark)
)


@Composable
fun AIMoviesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> createDarkColors()
        else -> createLightColors()
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}