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
import com.jetpack.compose.learning.sharedelementtransition.model.AlbumInfoModel
import com.jetpack.compose.learning.sharedelementtransition.model.CoffeeModel
import com.jetpack.compose.learning.sharedelementtransition.model.ProfileModel
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

    fun getAlbumsData(): List<AlbumInfoModel> {
        return listOf(
            AlbumInfoModel(0,R.drawable.img_album_01, "It happened Quiet", "Aurora", 2018),
            AlbumInfoModel(1, R.drawable.img_album_02, "All My Daemons", "Aurora", 2016),
            AlbumInfoModel(2,R.drawable.img_album_03, "Running", "Aurora", 2015),
            AlbumInfoModel(3,R.drawable.img_album_04, "Paradise", "Aurora", 2015),
            AlbumInfoModel(4,R.drawable.img_album_05, "Heroz Falling", "Aurora", 2015),
            AlbumInfoModel(5,R.drawable.img_album_06, "Better World", "Aurora", 2015),
            AlbumInfoModel(6,R.drawable.img_album_07, "Master Memories", "Aurora", 2015),
            AlbumInfoModel(7,R.drawable.img_album_08, "Chamber", "Aurora", 2015)
        )
    }

    fun getSearchSuggestionsData() : List<String> {
        return listOf("Conference Invitations", "Dinner with Dinner Club", "Herilooom Recepeies")
    }

    fun getSearchProfiles(): List<String> {
        return listOf("Lily", "Thea", "Lily", "Thea", "Lily", "Thea", "Lily", "Thea")
    }

    fun getFabProfiles(): List<ProfileModel> {
        return listOf(
            ProfileModel(0, R.drawable.ic_profile_image_1, "Lily Thea"),
            ProfileModel(1, R.drawable.ic_profile_image_2, "Lily Thea"),
            ProfileModel(2, R.drawable.ic_profile_image_3, "Lily Thea"),
            ProfileModel(3, R.drawable.ic_profile_image_4, "Lily Thea")
        )
    }

    fun getCoffeDetails(): List<CoffeeModel> {
        return listOf(
            CoffeeModel(
                id = 0,
                name = "Espresso",
                description = "Espresso as a standalone coffee is served everywhere. It contains literally the basic essence. Coffee and water.Ideal serving: 30ml Espresso in a 90ml cup.",
                image = R.drawable.ic_espresso
            ),
            CoffeeModel(
                id = 1,
                name = "Doppio",
                description = "Doppio in Italian literally means ‘double.’ It is a double shot of Espresso coffee.Ideal serving: 60ml Espresso in a 90ml cup.",
                image = R.drawable.ic_doppio
            ),
            CoffeeModel(
                id = 2,
                name = "Macchiato",
                description = "Macchiato in Italian means ‘stained.’ This is because a serving of Macchiato is a normal Espresso shot with a little-foamed milk on the top.Ideal serving: 30ml Espresso + Foamed milk on top in a 90ml cup.",
                image = R.drawable.ic_espresso_macchiato
            ),
            CoffeeModel(
                id = 3,
                name = "Cappuccino",
                description = "Everyone’s favourite and the most well-known and standard coffee drink, cappuccino contains more milk-to-coffee ratio.Ideal serving: 60ml Espresso + 60ml steamed milk + 60ml foamed milk (in that order) in a 200ml cup.",
                image = R.drawable.ic_cappuccino
            ),
            CoffeeModel(
                id = 4,
                name = "Café au Lait",
                description = "Café au Lait literally means ‘coffee with milk.’ It is a French press coffee preparation with equal amounts coffee brew and scalded milk. Scalded milk is milk that is heated to 82° C to kill off bacteria and remove many proteins.\n" +
                        "Ideal serving: 90ml French press coffee + 90ml scalded milk in a 200ml cup.",
                image = R.drawable.ic_cafe_au_lait
            ),
            CoffeeModel(
                id = 5,
                name = "Turkish",
                description = "The Turkish like their coffee light and sweet. Hence, a majority of this coffee is sugar water.\n" +
                        "Ideal serving: 10g (or 2 tsp.) ground coffee + 180ml sugar water + köpük (foam) (in that order) in a 200ml cup.\n" +
                        "\n",
                image = R.drawable.ic_turkish
            ),
            CoffeeModel(
                id = 6,
                name = "Americano",
                description = "Those who say they like their coffee black talk about Café Americano. If you might have seen in Western media, Americans like to drink their coffee straight out of the pot.\n" +
                        "Ideal serving: 60ml Espresso + 120ml hot water in a 200ml cup.",
                image = R.drawable.ic_americano
            ),
            CoffeeModel(
                id = 7,
                name = "Vienna Mocha",
                description = "Or simply known as Vienna Coffee, this coffee is a fun-loving twist to your regular Espresso shot. What’s the twist, you ask? WHIPPED CREAM!\n" +
                        "Ideal serving: 60ml Espresso + Whipped cream on the top in a 150ml cup.",
                image = R.drawable.ic_vienna_mocha
            ),
            CoffeeModel(
                id = 8,
                name = "Latte",
                description = "Caffé Latte can be seen as the more mainstream brother of Café au Lait. The name literally means, yes, you guessed it right, ‘milk coffee.’\n" +
                        "Ideal serving: 60ml Espresso + 180-300ml steamed milk (depending on container)",
                image = R.drawable.ic_caffe_latte
            )
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
