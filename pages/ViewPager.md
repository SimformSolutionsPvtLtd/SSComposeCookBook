# ViewPager

| Horizontal Pager | Horizontal Pager with Tabs | Horizontal Pager with indicator |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/viewpager/HorizontalViewPager.kt#L82" target="_blank"><img src="/gif/ViewPager/horizontal_pager.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/viewpager/ViewPagerWithTabActivity.kt#L67" target="_blank"><img src="/gif/ViewPager/horizontal_page_with_tab.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/viewpager/HorizontalViewPager.kt#L82" target="_blank"><img src="/gif/ViewPager/horizontal_pager_with_indicator.gif" height="500px"/></a> |

## Horizontal Pager
 A component that allows users to swipe horizontally between pages of content. It displays a sequence of pages where each page is shown one at a time, providing a straightforward navigation experience. Also we can configure its behaviour to enable reverse pager behaviour or whether to disable swipe gestures for it.

## Horizontal Pager with Tabs
 Combines horizontal paging with a tab layout at the top or bottom, enabling users to quickly switch between pages using tabs. Each tab corresponds to a page, providing an easy way to navigate through different sections. This way we can manually navigate between pages instead of swiping.

## Horizontal Pager with Indicator
 Includes visual indicators, such as dots or bars, to represent the number of pages and the current page. These indicators give users a visual cue about their position within the pager and allow for smooth navigation between pages.

| Vertical Pager with indicator | Pager with Zoom-in</br>transformation | Pager with Fling Behavior |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/viewpager/VerticalViewPagerWithIndicatorActivity.kt#L70" target="_blank"><img src="/gif/ViewPager/vertical_pager_with_indicator.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/viewpager/ViewPagerWithSwipeAnimationActivity.kt#L99" target="_blank"><img src="/gif/ViewPager/pager_with_zoom_in_transformation.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/viewpager/HorizontalPagerWithFlingBehaviorActivity.kt#L79" target="_blank"><img src="/gif/ViewPager/pager_with_fling_behavior.gif" height="500px"/></a> |

## Vertical Pager with indicator 
Similar to a horizontal pager with indicators, but allows users to swipe vertically between pages. The indicators, such as dots or bars, represent the total number of pages and the current page position, but are aligned vertically rather than horizontally.

## Pager with Zoom-in transformation
Features a zoom-in effect on the current page, creating a dynamic visual experience where the active page is enlarged while adjacent pages are scaled down.

## Pager with Fling Behavior
Supports fling gestures to swipe between pages with momentum, simulating a natural scrolling experience. You can adjust the fling sensitivity to control how responsive the page transitions are to user interactions.

| Add/Remove pager |
| -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/viewpager/AddRemovePagerActivity.kt#L84" target="_blank"><img src="/gif/ViewPager/add_remove_pager.gif" height="500px"/></a> |

## Add/Remove Pager
A demo showcasing dynamic page management in a pager allows users to add or remove pages in real-time. This functionality enables the pager to update its content dynamically, reflecting changes immediately without requiring a restart. Users can interact with controls to add new pages or remove existing ones, demonstrating the pager's flexibility and responsiveness to content updates.