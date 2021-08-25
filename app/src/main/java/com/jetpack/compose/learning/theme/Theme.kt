package com.jetpack.compose.learning.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// dark palettes
private val DarkPinkColorPalette = darkColors(
    primary = pink200,
    primaryVariant = pink500,
    secondary = teal200,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    error = Color.Red,
)

private val DarkGreenColorPalette = darkColors(
    primary = green200,
    primaryVariant = green700,
    secondary = teal200,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    error = Color.Red,
)

private val DarkPurpleColorPalette = darkColors(
    primary = purple200,
    primaryVariant = purple700,
    secondary = teal200,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    error = Color.Red,
)

private val DarkBlueColorPalette = darkColors(
    primary = blue200,
    primaryVariant = blue700,
    secondary = teal200,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    error = Color.Red,
)

private val DarkOrangeColorPalette = darkColors(
    primary = orange200,
    primaryVariant = orange700,
    secondary = teal200,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    error = Color.Red,
)

private val DarkRedColorPalette = darkColors(
    primary = red200,
    primaryVariant = red500,
    secondary = teal200,
    surface = Color.Black
)

private val DarkYellowColorPalette = darkColors(
    primary = yellow200,
    primaryVariant = yellow500,
    secondary = teal200,
    surface = Color.Black
)

private val DarkBrownColorPalette = darkColors(
    primary = brown200,
    primaryVariant = brown500,
    secondary = teal200,
    surface = Color.Black
)

private val DarkGreyColorPalette = darkColors(
    primary = grey200,
    primaryVariant = grey500,
    secondary = teal200,
    surface = Color.Black
)

private val DarkIndigoColorPalette = darkColors(
    primary = indigo200,
    primaryVariant = indigo500,
    secondary = teal200,
    surface = Color.Black
)

// Light pallets
private val LightPinkColorPalette = lightColors(
    primary = pink500,
    primaryVariant = pink700,
    secondary = teal200,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val LightGreenColorPalette = lightColors(
    primary = green500,
    primaryVariant = green700,
    secondary = teal200,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val LightPurpleColorPalette = lightColors(
    primary = purple500,
    primaryVariant = purple700,
    secondary = teal200,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val LightBlueColorPalette = lightColors(
    primary = blue500,
    primaryVariant = blue700,
    secondary = teal200,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val LightOrangeColorPalette = lightColors(
    primary = orange500,
    primaryVariant = orange700,
    secondary = teal200,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val LightRedColorPalette = lightColors(
    primary = red500,
    primaryVariant = red700,
    secondary = teal200,
    surface = Color.White
)

private val LightYellowColorPalette = lightColors(
    primary = yellow500,
    primaryVariant = yellow700,
    secondary = teal200,
    surface = Color.White
)

private val LightBrownColorPalette = lightColors(
    primary = brown500,
    primaryVariant = brown700,
    secondary = teal200,
    surface = Color.White
)

private val LightGreyColorPalette = lightColors(
    primary = grey500,
    primaryVariant = grey700,
    secondary = teal200,
    surface = Color.White
)

private val LightIndigoColorPalette = lightColors(
    primary = indigo500,
    primaryVariant = indigo700,
    secondary = teal200,
    surface = Color.White
)

enum class ColorPalette {
    PINK, PURPLE, GREEN, ORANGE, BLUE, RED, INDIGO, BROWN, GREY
}

@Composable
fun ComposeCookBookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorPalette: ColorPalette = ColorPalette.GREEN,
    content: @Composable() () -> Unit
) {

    val colors = colorPalette.getMaterialColors(darkTheme)

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun BaseView(
    appThemeState: AppThemeState,
    systemUiController: SystemUiController?,
    content: @Composable () -> Unit
) {

    val color = appThemeState.pallet.getMaterialColor()

    systemUiController?.setStatusBarColor(color = color, darkIcons = appThemeState.darkTheme)
    ComposeCookBookTheme(darkTheme = appThemeState.darkTheme, colorPalette = appThemeState.pallet) {
        content()
    }
}

fun ColorPalette.getMaterialColors(darkTheme: Boolean): Colors {
    return when (this) {
        ColorPalette.GREEN -> if (darkTheme) DarkGreenColorPalette else LightGreenColorPalette
        ColorPalette.PURPLE -> if (darkTheme) DarkPurpleColorPalette else LightPurpleColorPalette
        ColorPalette.ORANGE -> if (darkTheme) DarkOrangeColorPalette else LightOrangeColorPalette
        ColorPalette.BLUE -> if (darkTheme) DarkBlueColorPalette else LightBlueColorPalette
        ColorPalette.RED -> if (darkTheme) DarkRedColorPalette else LightRedColorPalette
        ColorPalette.PINK -> if (darkTheme) DarkPinkColorPalette else LightPinkColorPalette
        ColorPalette.INDIGO -> if (darkTheme) DarkIndigoColorPalette else LightIndigoColorPalette
        ColorPalette.BROWN -> if (darkTheme) DarkBrownColorPalette else LightBrownColorPalette
        ColorPalette.GREY -> if (darkTheme) DarkGreyColorPalette else LightGreyColorPalette
    }
}

fun ColorPalette.getMaterialColor(): Color {
    return when (this) {
        ColorPalette.GREEN -> green700
        ColorPalette.BLUE -> blue700
        ColorPalette.ORANGE -> orange700
        ColorPalette.PURPLE -> purple700
        ColorPalette.RED -> red700
        ColorPalette.PINK -> pink700
        ColorPalette.INDIGO -> indigo700
        ColorPalette.BROWN -> brown700
        ColorPalette.GREY -> grey700
    }
}