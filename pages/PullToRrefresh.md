# Pull To Refresh

| Simple Pull To Refresh | Custom Background Pull To Refresh | Custom View Pull To Refresh |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/pulltorefresh/SimplePullToRefresh.kt#L75" target="_blank"><img src="/gif/PullToRefresh/SimplePullToRefresh.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/pulltorefresh/CustomBackgroundPullToRefreshActivity.kt#L75" target="_blank"><img src="/gif/PullToRefresh/CustomBackgroundPullToRefresh.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/pulltorefresh/CustomViewPullToRefreshActivity.kt#L82" target="_blank"><img src="/gif/PullToRefresh/CustomViewPullToRefresh.gif" height="500px"/></a> |

## Simple pull to refresh
A Simple Pull to Refresh is a common UI pattern that allows users to refresh content in a scrollable view by pulling it downward. In Jetpack Compose, this can be easily implemented using the SwipeRefresh composable from the accompanist library. The SwipeRefresh component wraps your scrollable content and triggers a refresh action when the user pulls down on the screen. This approach provides a straightforward way to integrate pull-to-refresh functionality with minimal customization.

## Pull to refresh with custom background
When implementing a Custom Background for Pull to Refresh in Jetpack Compose, the SwipeRefreshIndicator is typically used to create a tailored visual experience during the refresh interaction. The SwipeRefreshIndicator allows developers to customize not only the appearance of the refresh indicator but also the background that appears as the user pulls down to refresh the content.

## Custom View pull to refresh
Creating a Custom View for Pull to Refresh involves building a completely custom composable from scratch to handle the pull-to-refresh interaction. This approach allows for the highest level of customization, where you can design a unique interaction pattern and visual effect for the pull-to-refresh gesture. For instance, you might want to implement a rotating logo, a progress bar that fills up as the user pulls down, or any other creative interaction. This requires handling touch gestures, managing the state of the pull distance, and triggering the refresh action when a certain threshold is reached. Although this method is more complex, it provides the most control over the user experience.