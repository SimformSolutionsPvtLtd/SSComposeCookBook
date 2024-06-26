# List

Explore the various ways to show a list of items in Jetpack Compose and how to use them in your application.
| LazyRow/Column | LazyVerticalGrid | Advance List |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/list/LazyColumnRowActivity.kt" target="_blank"><img src="/gif/List/ListRowColumn.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/list/LazyGridActivity.kt" target="_blank"><img src="/gif/List/ListGrid.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/list/AdvanceListActivity.kt" target="_blank"><img src="/gif/List/AdvanceList.gif" height="500px"/></a> |

## LayRow/Column
As the name suggests, the difference between LazyColumn and LazyRow is the orientation in which they lay out their items and scroll. LazyColumn produces a vertically scrolling list, and LazyRow produces a horizontally scrolling list.
Find example of the currently shown screen here

- [Lazy Row]

- [Lazy Column]

## Lazy Vertical Grid
The LazyVerticalGrid composable provides support for displaying items in a grid. A Lazy vertical grid will display its items in a vertically scrollable container, spanned across multiple columns.

- [Lazy Vertical Grid]

## Advance List 
For implementing an advanced list, data can be efficiently retrieved from remote APIs, processed using Kotlin Flows for reactive data handling, and then displayed using the Paging library to ensure smooth and performant data loading. This approach allows for dynamic content updates, seamless pagination, and an overall optimized user experience.

- [Advance List]

<!-- Code Links -->

[Lazy Row]: /app/src/main/java/com/jetpack/compose/learning/list/LazyColumnRowActivity.kt#L111 

[Lazy Column]: /app/src/main/java/com/jetpack/compose/learning/list/LazyColumnRowActivity.kt#L82

[Lazy Vertical Grid]: /app/src/main/java/com/jetpack/compose/learning/list/LazyGridActivity.kt#L109

[Advance List]: /app/src/main/java/com/jetpack/compose/learning/list/advancelist/MovieList.kt