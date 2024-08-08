package com.jetpack.compose.learning.parallaxeffect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlin.math.max
import kotlin.math.min

class ParallaxEffectActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                MainContent()
            }
        }
    }

    @Preview
    @Composable
    fun MainContent() {
        val scrollState = rememberScrollState()
        Scaffold (
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier
                        .offset {
                            IntOffset(x = 0, y = max(15, scrollState.value))
                                },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = Color.White,
                    onClick = {}
                ) {
                    Icon(Icons.Filled.Add, "", Modifier.background(MaterialTheme.colors.primary), tint = Color.White)
                }
            }
        ) { contentPadding ->
            ParallaxView(modifier = Modifier.padding(contentPadding), scrollState)
        }
    }

    @Composable
    fun ParallaxView(modifier: Modifier, scrollState: ScrollState) {
        val text = "Goku looks almost exactly like his father, possessing the same spiky black hairstyle, dark-colored eyes, and facial features. However, he has softer eyes, a kind demeanor, and a lighter-pale skin complexion from his mother. Goku's most distinguishing physical characteristic is his hair. He has three bangs hanging to the right of his forehead and two bangs hanging to the left. His hair also stands up in the front with four spikes and three bangs in the back (later in the anime, he has five spikes in the front and four in the back). Goku was born with the signature tail of the Universe 7 Saiyans, which was long and prehensile with brown fur. It was removed and regrown multiple times in his early childhood, but in his late teens, Goku's tail was permanently removed by Kami in order to prevent his Great Ape transformation.At age 12, Goku was rather short and appeared even younger than he was, being considered less than 10 years old (people were surprised when learning his actual age). His somewhat simple-minded personality also gave people the impression that he was as young as he looked. During the next few years, Goku did not go through any physical changes, other than his muscles becoming more pronounced. By age 15, he began showing noticeable increases in height. By age 18, he had a considerable growth spurt, growing taller than most of his friends and developing a well-built physique. He is also considered very handsome, as noted by various women including Bulma and Heles. His physical changes made him almost indistinguishable from before and was only recognized by his signature naïve personality and trademark hairstyle. By age 25, Goku had another growth spurt, and became similar in height to his taller human allies and much more muscular.Due to his love of training, he is most commonly seen wearing a Gi. Originally, Goku wore an open blue gi secured with a white bow-tied obi over his waist, red wristbands, and dark blue kung fu shoes and also a tank top. After training under Master Roshi, Goku wore the basic Turtle School Uniform; consisting of a closed red gi (later orange) secured by a black knot-tied obi over his waist, blue wristbands, and blue kung fu shoes, with Roshi's kanji on the back and front-left side of his shirt. After training with Kami and Mr. Popo, Goku wore a black short-sleeved undershirt (later dark blue) with this gi and replaced his shoes with dark blue boots with a yellow border and red laces. After training under King Kai, Goku wore King Kai's kanji on the back of his gi shirt. During the Battle on Planet Namek, Goku wore his own kanji on both sides of his gi shirt. During the Android conflict and Majin Buu conflict, Goku wore a more plain version of this gi that lacked any kanji and featured a blue sash-style obi over his waist, as well as the same dark blue undershirt, same wristbands and the same boots from his previous gi, but with a red border and yellow/tan laces. He later retains this outfit throughout most of Dragon Ball Super as he regains his own kanji on both sides of his gi shirt."
        Box(modifier = modifier.fillMaxWidth()) {
            Column {
                TopAppBar(
                    title = { Text("Parallax Effect") },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                ) {
                    val height = 400.dp
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height)
                            .graphicsLayer {
                                alpha = min(
                                    1f,
                                    max(
                                        0.01f,
                                        1 - (scrollState.value / ((height.value * 2) + (height.value / 1.5f)))
                                    )
                                )
                            },
                        painter = painterResource(id = R.drawable.dp12),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                    Text(
                        text = text,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}