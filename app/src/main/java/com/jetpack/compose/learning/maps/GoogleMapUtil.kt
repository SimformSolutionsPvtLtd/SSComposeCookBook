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
import androidx.core.graphics.toColorInt
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonLineString
import com.google.maps.android.data.geojson.GeoJsonLineStringStyle
import com.google.maps.android.data.geojson.GeoJsonPoint
import com.google.maps.android.data.geojson.GeoJsonPointStyle
import com.google.maps.android.data.geojson.GeoJsonPolygon
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle
import com.google.maps.android.data.kml.KmlLayer
import com.google.maps.android.data.kml.KmlPolygon
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

/**
 * Extension operator function to add offset on LatLng with the + operator
 */
operator fun LatLng.plus(offset: Double): LatLng {
    return LatLng(latitude + offset, longitude + offset)
}

/**
 * Sets style from the geo json file according to type of the feature.
 */
fun GeoJsonLayer.setFeatureStyle() {
    for (feature in features) {
        when (feature.geometry.geometryType) {
            "Point" -> {
                val pointStyle = GeoJsonPointStyle()
                pointStyle.title = feature.getProperty("title")
                feature.pointStyle = pointStyle
            }
            "Polygon" -> {
                val polygonStyle = GeoJsonPolygonStyle()
                polygonStyle.strokeColor =
                    feature.getProperty("stroke").toColorInt()
                polygonStyle.strokeWidth =
                    feature.getProperty("stroke-width").toFloat()
                polygonStyle.fillColor =
                    feature.getProperty("fill").toColorInt()
                feature.polygonStyle = polygonStyle
            }
            "LineString" -> {
                val lineStringStyle = GeoJsonLineStringStyle()
                lineStringStyle.color =
                    feature.getProperty("stroke").toColorInt()
                lineStringStyle.width =
                    feature.getProperty("stroke-width").toFloat()
                feature.lineStringStyle = lineStringStyle
            }
        }
    }
}

/**
 * Fetches LatLngBound of all features from the layer.
 */
fun GeoJsonLayer.getFeatureBound(): LatLngBounds {
    val latLngBoundsBuilder = LatLngBounds.Builder()
    for (feature in features) {
        when (feature.geometry.geometryType) {
            "Point" -> {
                latLngBoundsBuilder.include((feature.geometry as GeoJsonPoint).coordinates)
            }
            "Polygon" -> {
                val coordinates =
                    (feature.geometry as GeoJsonPolygon).coordinates
                coordinates.flatten()
                    .forEach { latLngBoundsBuilder.include(it) }
            }
            "LineString" -> {
                val coordinates =
                    (feature.geometry as GeoJsonLineString).coordinates
                coordinates.forEach { latLngBoundsBuilder.include(it) }
            }
        }
    }
    return latLngBoundsBuilder.build()
}

/**
 * Fetches LatLngBound from KML layer
 */
fun KmlLayer.getBound(): LatLngBounds {
    val latLngBoundsBuilder = LatLngBounds.Builder()
    if (hasContainers()) {
        val outerContainer = containers.first()
        if (outerContainer.hasContainers()) {
            val innerContainer = outerContainer.containers.first()
            if (innerContainer.hasPlacemarks()) {
                val placemark = innerContainer.placemarks.first()
                val polygon = placemark.geometry as KmlPolygon
                for (latLng in polygon.outerBoundaryCoordinates) {
                    latLngBoundsBuilder.include(latLng!!)
                }
            }
        }
    }
    return latLngBoundsBuilder.build()
}
