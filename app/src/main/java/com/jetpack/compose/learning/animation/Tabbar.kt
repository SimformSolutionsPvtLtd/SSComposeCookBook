package com.jetpack.compose.learning.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.animation.ui.theme.colorPrimaryDark
import com.jetpack.compose.learning.animation.ui.theme.pinkColor
import com.jetpack.compose.learning.animation.ui.theme.pinkColorDark
import com.jetpack.compose.learning.theme.lightBlue200

@Composable
private fun TabBar(
    backgroundColor: Color, tabPage: TabPage1, onTabSelected: (tabPage: TabPage1) -> Unit
) {
    TabRow(selectedTabIndex = tabPage.ordinal,
        backgroundColor = backgroundColor,
        indicator = { tabPositions ->
            HomeTabIndicator1(tabPositions, tabPage)
        }) {
        HomeTab(icon = Icons.Default.Home,
            title = stringResource(R.string.home),
            onClick = { onTabSelected(TabPage1.Home) })
        HomeTab(icon = Icons.Default.AccountBox,
            title = stringResource(R.string.work),
            onClick = { onTabSelected(TabPage1.Work) })
    }
}

@Composable
fun TabBarContent() {

    // The currently selected tab.
    var tabPage by remember { mutableStateOf(TabPage1.Home) }

    // The background color. The value is changed by the current tab.
    val backgroundColor by animateColorAsState(if (tabPage == TabPage1.Home) lightBlue200 else pinkColor)

    Scaffold(topBar = {
        TabBar(backgroundColor = backgroundColor,
            tabPage = tabPage,
            onTabSelected = { tabPage = it })
    }, backgroundColor = backgroundColor) {}
}

@Composable
private fun HomeTabIndicator1(
    tabPositions: List<TabPosition>, tabPage: TabPage1
) {
    val transition = updateTransition(
        tabPage, label = "Tab indicator"
    )
    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            if (TabPage1.Home isTransitioningTo TabPage1.Work) {
                // Indicator moves to the right.
                // Low stiffness spring for the left edge so it moves slower than the right edge.
                spring(stiffness = Spring.StiffnessVeryLow)
            } else {
                // Indicator moves to the left.
                // Medium stiffness spring for the left edge so it moves faster than the right edge.
                spring(stiffness = Spring.StiffnessMedium)
            }
        }, label = "Indicator left"
    ) { page ->
        tabPositions[page.ordinal].left
    }
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            if (TabPage1.Home isTransitioningTo TabPage1.Work) {
                // Indicator moves to the right
                // Medium stiffness spring for the right edge so it moves faster than the left edge.
                spring(stiffness = Spring.StiffnessMedium)
            } else {
                // Indicator moves to the left.
                // Low stiffness spring for the right edge so it moves slower than the left edge.
                spring(stiffness = Spring.StiffnessVeryLow)
            }
        }, label = "Indicator right"
    ) { page ->
        tabPositions[page.ordinal].right
    }
    val color by transition.animateColor(
        label = "Border color"
    ) { page ->
        if (page == TabPage1.Home) colorPrimaryDark else Color.White
    }
    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, color), RoundedCornerShape(4.dp)
            )
    )
}

private enum class TabPage1 {
    Home, Work
}

@Composable
private fun HomeTab(
    icon: ImageVector, title: String, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon, contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title)
    }
}

@Preview
@Composable
private fun PreviewTabBar() {
    TabBarContent()
}