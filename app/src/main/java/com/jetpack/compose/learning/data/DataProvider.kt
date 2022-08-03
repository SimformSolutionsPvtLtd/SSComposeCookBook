package com.jetpack.compose.learning.data

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.UrlTileProvider
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.demosamples.instagramdemo.data.Items
import com.jetpack.compose.learning.maps.basic.TouristPlace
import com.jetpack.compose.learning.maps.cluster.SSClusterItem
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.maps.plus
import java.net.MalformedURLException
import java.net.URL

object DataProvider {
    var postList = listOf(
        Items(
            id = 1,
            title = "icecampus",
            subTitle = "2013 film",
            image = R.drawable.ic_post_image_1,
            profilePic = R.drawable.ic_profile_image_1
        ),
        Items(
            id = 2,
            title = "tasteecho",
            subTitle = "2020 film",
            image = R.drawable.ic_post_image_2,
            profilePic = R.drawable.ic_profile_image_2
        ),
        Items(
            id = 3,
            title = "clamexercise",
            subTitle = "2019 film",
            image = R.drawable.ic_post_image_3,
            profilePic = R.drawable.ic_profile_image_3
        ),
        Items(
            id = 4,
            title = "glandford",
            subTitle = "2018 film",
            image = R.drawable.ic_post_image_4,
            profilePic = R.drawable.ic_profile_image_4
        ),
        Items(
            id = 5,
            title = "worldbus",
            subTitle = "2002 film",
            image = R.drawable.ic_post_image_5,
            profilePic = R.drawable.ic_profile_image_5
        ),
        Items(
            id = 6,
            title = "cheesays",
            subTitle = "2001 film",
            image = R.drawable.ic_post_image_6,
            profilePic = R.drawable.ic_profile_image_6
        ),
        Items(
            id = 7,
            title = "skimaker",
            subTitle = "2001 film",
            image = R.drawable.ic_post_image_7,
            profilePic = R.drawable.ic_profile_image_7
        ),
        Items(
            id = 8,
            title = "playroofer",
            subTitle = "2001 film",
            image = R.drawable.ic_post_image_8,
            profilePic = R.drawable.dp8
        ),
        Items(
            id = 9,
            title = "seekerrisk",
            subTitle = "2001 film",
            image = R.drawable.ic_post_image_9,
            profilePic = R.drawable.dp9
        ),
        Items(
            id = 10,
            title = "poetrylove",
            subTitle = "2001 film",
            image = R.drawable.ic_post_image_10,
            profilePic = R.drawable.dp10
        ),
        Items(
            id = 11,
            title = "foodish",
            subTitle = "2001 film",
            image = R.drawable.ic_post_image_11,
            profilePic = R.drawable.dp11
        ),
        Items(
            id = 12,
            title = "wanderlust",
            subTitle = "2001 film",
            image = R.drawable.ic_post_image_12,
            profilePic = R.drawable.dp12
        ),
        Items(
            id = 13,
            title = "dualipa",
            subTitle = "2001 film",
            image = R.drawable.ic_post_image_13,
            profilePic = R.drawable.dp13
        ),
        Items(
            id = 14,
            title = "billie",
            subTitle = "2001 film",
            image = R.drawable.ic_post_image_14,
            profilePic = R.drawable.dp14
        ),
        Items(
            id = 15,
            title = "foundreturn",
            subTitle = "2001 film",
            image = R.drawable.ic_post_image_15,
            profilePic = R.drawable.dp15
        )
    )

    fun getTouristPlaceList(): List<TouristPlace> {
        return listOf(
            TouristPlace(1, "Statue of Liberty", currentMarkerLatLong),
            TouristPlace(2, "Empire State Building", LatLng(40.748817, -73.985428)),
            TouristPlace(3, "Central Park", LatLng(40.785091, -73.968285)),
            TouristPlace(4, "Times Square", LatLng(40.758896, -73.985130)),
            TouristPlace(5, "Fifth Avenue", LatLng(40.773998, -73.966003)),
            TouristPlace(6, "Grand Canyon", LatLng(36.056595, -112.125092)),
            TouristPlace(7, "Golden Gate Bridge", LatLng(37.8199, -122.4783)),
            TouristPlace(8, "Venice Beach", LatLng(33.9850, -118.4695)),
            TouristPlace(9, "White House", LatLng(38.897957, -77.036560)),
            TouristPlace(10, "United States Capitol Building", LatLng(38.889805, -77.009056)),
        )
    }

    fun getGroundOverlayImages(): List<Int> {
        return listOf(
            R.drawable.ic_statue_of_liberty,
            R.drawable.ic_post_image_1,
            R.drawable.ic_post_image_2,
            R.drawable.ic_post_image_3,
            R.drawable.ic_post_image_4,
            R.drawable.ic_post_image_5,
            R.drawable.ic_post_image_6,
            R.drawable.ic_post_image_7
        )
    }

    fun getPolygonPositions(): List<LatLng> {
        return listOf(
            LatLng(40.748817, -73.985428),
            LatLng(40.749607, -73.9728967),
            LatLng(40.7450187, -73.9827565),
            LatLng(40.7441246, -73.9871124),
        )
    }

    fun getPolygonHolesPositions(): List<List<LatLng>> {
        return listOf(
            listOf(
                LatLng(40.7475017, -73.9847417),
                LatLng(40.747648, -73.9812007),
                LatLng(40.7470303, -73.9845052),
            )
        )
    }

    fun getMapColors(): List<Color> {
        return listOf(
            Color.Black,
            Color.Blue,
            Color.Cyan,
            Color.LightGray,
            Color.Red,
            Color.Green,
            Color.Magenta,
            Color.DarkGray
        )
    }

    fun getClusterItems(): List<SSClusterItem> {
        val itemList = mutableListOf<SSClusterItem>()
        repeat(50) {
            val offset = (it + 1) / 1000.0
            val ssClusterItem = SSClusterItem(
                currentMarkerLatLong + offset,
                "Statue Of Liberty",
                "Offset from SL ${it + 1}"
            )
            itemList.add(ssClusterItem)
        }
        return itemList
    }

    fun getViewPagerImages(): List<Int> {
        return listOf(
            R.drawable.ic_profile_image_1,
            R.drawable.ic_profile_image_2,
            R.drawable.ic_profile_image_3,
            R.drawable.ic_profile_image_4,
            R.drawable.ic_profile_image_5,
            R.drawable.ic_profile_image_6,
            R.drawable.ic_profile_image_7
        )
    }
}

class MapTileProvider(private val context: Context) : UrlTileProvider(250, 250) {
    override fun getTileUrl(x: Int, y: Int, zoom: Int): URL? {
        return try {
            URL(context.getString(R.string.random_image_url))
        } catch (e: MalformedURLException) {
            throw AssertionError(e)
        }
    }
}
