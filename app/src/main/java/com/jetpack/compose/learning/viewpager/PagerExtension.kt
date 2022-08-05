package com.jetpack.compose.learning.viewpager

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.HorizontalPager

/**
 * This view use to show horizontal pager with visible previous and next page boundaries
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PreviewHorizontalPager(
    modifier: Modifier = Modifier,
    pageCount: Int,
    pagerState: PagerState = rememberPagerState(),
    content: @Composable PagerScope.(page: Int) -> Unit,
) {
    HorizontalPager(
        count = pageCount,
        state = pagerState,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 55.dp),
    ) {
        content(it)
    }
}

private val HorizontalScrollConsumer = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource) =
        available.copy(y = 0f)

    override suspend fun onPreFling(available: Velocity) = available.copy(y = 0f)
}

fun Modifier.disabledHorizontalPointerInputScroll(disabled: Boolean = true) =
    if (disabled) this.nestedScroll(HorizontalScrollConsumer) else this

/**
 * infinite scroll index return first position when you reach pager list size
 */
fun Int.infiniteScrollIndex(itemCount: Int): Int = when (itemCount) {
    0 -> this
    else -> this - floorDiv(itemCount) * itemCount
}

typealias ComposableFun = @Composable () -> Unit

fun showToastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
