# Animation

The Animations section of the Jetpack Compose Cookbook is designed to enhance your app's user experience with dynamic and engaging animations. Jetpack Compose provides a flexible and intuitive API to create a range of animations, making your UI elements not only interactive but also visually appealing.

| Basic | Content | Gesture |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/animation/BasicAnimation.kt#L49" target="_blank"><img src="/gif/Animation/BasicAnim.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/animation/contentAnimation/ContentIconAnimationActivity.kt#L65" target="_blank"><img src="/gif/Animation/ContentAnim.gif" height="500px"/></a>| <a href="/app/src/main/java/com/jetpack/compose/learning/animation/GestureAnimationActivity.kt" target="_blank"><img src="/gif/Animation/GestureAnim.gif" height="500px"/></a> |

## Basic Animation

Learn how to create fundamental animations for the actions like:

- [Change in color][AnimateAsStateChangeColor]
- [Change in *color* with *size*][AnimateAsStateChangeColorWithSize]
- [Show or hide composable view][AnimatedVisibilityDemo]
- [Rotation of the view][RotatingSquareComponent]

## Conent Animation

A sudden change in content without animation can be jarring and disrupt the user experience. Discover how to smooth out these transitions with animations:

- [Animate the change in size and content of a composable][ContentWithIconAnimation]
- [Create animations that expand short text into longer content and vice versa][ContentAnimation]
- [FAB Button Expansion and Collapse Animation][TabFloatingActionButton]

These techniques will make content updates feel more seamless and engaging.

## Gesture Animation

Gesture animations bring a touch of fluidity and responsiveness to user interactions. By incorporating animations that react to gestures, you can create a more intuitive and engaging user experience.

- [Implement swipe to delete with animation][SwipeToDelete]

| Infinite | Shimmer | TabBar |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/animation/InfiniteTransitionActivity.kt#L59" target="_blank"><img src="/gif/Animation/InfiniteAnim.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/animation/ShimmerAnimationActivity.kt#L54" target="_blank"><img src="/gif/Animation/ShimmerAnim.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/animation/Tabbar.kt#L68" target="_blank"><img src="/gif/Animation/TabBarAnim.gif" height="500px"/></a> |

## Infinite Animation

Infinite animations provide continuous and seamless effects, ideal for capturing user attention. Let's implement some indefinite animation

- [Change color with animation indefinitely][InfiniteAnimation]

## Shimmer Animation

Shimmer animations add a polished touch to loading states, creating an engaging visual effect while content is being loaded.

- [Learn how to impelement shinmmer effect][ShimmerAnimation]

## TabBar Animation

- [Animate tab bar changes][TabBar]

## Other References

- **[Choose an animation API]** - Decide which animation API fits your needs based on your use case.

- **[Material Design Motion]** - Principles of motion design according to Material Design guidelines, useful for creating consistent and engaging animations.

<!-- Code Links -->

[AnimateAsStateChangeColor]: /app/src/main/java/com/jetpack/compose/learning/animation/basic/ChangeColorAnimationWithState.kt#L27

[AnimateAsStateChangeColorWithSize]: /app/src/main/java/com/jetpack/compose/learning/animation/basic/ChangeColorAnimationWithState.kt#L50

[AnimatedVisibilityDemo]: /app/src/main/java/com/jetpack/compose/learning/animation/basic/VisibilityAnimation.kt#L34

[RotatingSquareComponent]: /app/src/main/java/com/jetpack/compose/learning/animation/basic/RotateViewAnimation.kt#L23

[ContentWithIconAnimation]: /app/src/main/java/com/jetpack/compose/learning/animation/contentAnimation/ContentIconAnimationActivity.kt#L88

[ContentAnimation]: /app/src/main/java/com/jetpack/compose/learning/animation/contentAnimation/ContentAnimation.kt#L40

[TabFloatingActionButton]: /app/src/main/java/com/jetpack/compose/learning/animation/contentAnimation/FabButtonWithContent.kt#L21

[SwipeToDelete]: /app/src/main/java/com/jetpack/compose/learning/animation/SwipeToDelete.kt#L53

[InfiniteAnimation]: /app/src/main/java/com/jetpack/compose/learning/animation/InfiniteTransitionActivity.kt#L59

[ShimmerAnimation]: /app/src/main/java/com/jetpack/compose/learning/animation/ShimmerAnimationActivity.kt

[TabBar]: /app/src/main/java/com/jetpack/compose/learning/animation/Tabbar.kt

<!-- Reference Links -->

[Choose an animation API]: https://developer.android.com/develop/ui/compose/animation/choose-api

[Material Design Motion]: https://m3.material.io/styles/motion/overview
