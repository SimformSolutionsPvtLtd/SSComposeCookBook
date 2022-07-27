package com.jetpack.compose.learning.maps

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.CameraPositionState
import kotlin.random.Random

fun convertVectorToBitmap(context: Context, @DrawableRes id: Int, color: Color): BitmapDescriptor {
    val vectorDrawable: Drawable = ResourcesCompat.getDrawable(context.resources, id, null)
        ?: return BitmapDescriptorFactory.defaultMarker()
    val bitmap = Bitmap.createBitmap(128, 128, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    DrawableCompat.setTint(vectorDrawable, color.toArgb())
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun getRandomColor(): Color {
    return Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
}

suspend fun CameraPositionState.animateBound(vararg position: LatLng, padding: Int = 50) {
    animate(CameraUpdateFactory.newLatLngBounds(createLatLngBound(*position), padding))
}

suspend fun CameraPositionState.animateBound(bounds: LatLngBounds, padding: Int = 50) {
    animate(CameraUpdateFactory.newLatLngBounds(bounds, padding))
}

suspend fun CameraPositionState.animatePosition(
    target: LatLng,
    zoom: Float = 16f,
    bearing: Float = 0f
) {
    animate(CameraUpdateFactory.newCameraPosition(createPosition(target, zoom, bearing)))
}

fun createLatLngBound(vararg position: LatLng): LatLngBounds {
    return LatLngBounds.Builder().apply {
        position.forEach {
            include(it)
        }
    }.build()
}

fun createPosition(target: LatLng, zoom: Float = 16f, bearing: Float = 0f): CameraPosition {
    return CameraPosition.Builder()
        .target(target)
        .zoom(zoom).bearing(bearing)
        .build()
}

/**
 * Rotation between to position. So that marker can be rotated.
 */
fun getRotation(previousPosition: LatLng, currentPosition: LatLng) =
    SphericalUtil.computeHeading(previousPosition, currentPosition).toFloat()