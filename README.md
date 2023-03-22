![](/gif/Compose_Banner.png)

# SSComposeCookBook

[![Compose Version](https://img.shields.io/badge/Jetpack%20Compose-1.2.0--beta03-brightgreen)](https://developer.android.com/jetpack/compose)
[![Kotlin Version](https://img.shields.io/badge/Kotlin-v1.6.21-blue.svg)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg?style=flat)](https://www.android.com/)

A Collection of major Jetpack compose UI components which are commonly used.

## Introduction

[Jetpack Compose](https://developer.android.com/jetpack/compose) is a modern toolkit for building native Android UI. It simplifies and accelerates UI development on Android with less code, powerful tools, and intuitive Kotlin APIs.

In this repository, there are number of UI components demonstrated which can be useful in regular development through Jetpack Compose.

## Prerequisites
- Experience with Kotlin syntax, including lambdas

## What you'll need
- [Latest Stable Android Studio](https://developer.android.com/studio)
- [Google Maps Key](https://developers.google.com/maps/documentation/android-sdk/get-api-key)

## Setup (For Google Map Implementation)
To run the google map samples, you'll have to:

1. Get a Maps API key
2. Add an entry in `local.properties` or `local.defaults.properties` that looks like `MAPS_API_KEY=YOUR_MAPS_KEY`
3. Build and run

## Components

| App Bar | Bottom Navigation | Button |
| -- | -- | -- | 
| <img src="/gif/AppBar.jpg" height="500px"/> | <img src="/gif/BottomNav.gif" height="500px"/>| <img src="/gif/Button.jpg" height="500px"/>

| Checkbox | Dialog | Dropdown |
| -- | -- | -- | 
| <img src="/gif/Checkbox.jpg" height="500px"/> | <img src="/gif/Dialog.gif" height="500px"/>| <img src="/gif/Dropdown.gif" height="500px"/>

| FAB | Modal Drawer | Bottom Drawer |
| -- | -- | -- | 
| <img src="/gif/FAB.png" height="500px"/> | <img src="/gif/ModalDrawer.gif" height="500px"/>| <img src="/gif/BottomDrawer.gif" height="500px"/>

| Radio Button | Slider | Text |
| -- | -- | -- | 
| <img src="/gif/Radio.png" height="500px"/> | <img src="/gif/Sliders.gif" height="500px"/>| <img src="/gif/Text.png" height="500px"/>

| Textfield | Theme | ZoomView |
| -- | -- | -- | 
| <img src="/gif/TextFields.png" height="500px"/> | <img src="/gif/Theme.gif" height="500px"/>| <img src="/gif/Zoomview.gif" height="500px"/>

| Textfield | Theme | MagnifierView |
| -- | -- | -- |
| <img src="/gif/TextFields.png" height="500px"/> | <img src="/gif/Theme.gif" height="500px"/>| <img src="/gif/magnifierView.gif" height="500px"/>

| TabBar | DatePicker | TimePicker |
| -- | -- | -- |
| <img src="/gif/TabBar.gif" height="500px"/> | <img src="/gif/DatePicker.gif" height="500px"/> | <img src="/gif/TimePicker.gif" height="500px"/>

| ImagePicker | ParallaxEffect | Compose views in Xml |
| -- | -- | -- |
| <img src="/gif/ImagePicker.gif" height="500px"/> | <img src="/gif/ParallaxEffect.gif" height="500px"/> | <img src="/gif/ComposeViewsInXml.gif" height="500px"/> |

| XML views in Compose |
| -- |
| <img src="/gif/XmlViewInCompose.png" height="500px"/> |

## List

| LazyRow/Column | LazyVerticalGrid | Advance List |
| -- | -- | -- |
| <img src="/gif/List/ListRowColumn.jpg" height="500px"/> | <img src="/gif/List/ListGrid.jpg" height="500px"/>| <img src="/gif/List/AdvanceList.gif" height="500px"/>

## Pull To Refresh
| Simple Pull To Refresh | Custom Background Pull To Refresh | Custom View Pull To Refresh |
| -- | -- | -- |
| <img src="/gif/PullToRefresh/SimplePullToRefresh.gif" height="500px"/> | <img src="/gif/PullToRefresh/CustomBackgroundPullToRefresh.gif" height="500px"/> | <img src="/gif/PullToRefresh/CustomViewPullToRefresh.gif" height="500px"/>

## Swipe To Delete
| Swipe Left | Swipe Right | Swipe Left + Right |
| -- | -- | -- |
| <img src="/gif/SwipeToDelete/swipeLeft.gif" height="500px"/> | <img src="/gif/SwipeToDelete/swipeRight.gif" height="500px"/>| <img src="/gif/SwipeToDelete/swipeLeft+Right.gif" height="500px"/>

## Constraint Layout

| Barrier | Guideline | Chain |
| -- | -- | -- | 
| <img src="/gif/Constraint/Barrier.png" height="500px"/> | <img src="/gif/Constraint/Guideline.png" height="500px"/>| <img src="/gif/Constraint/Chain.png" height="500px"/>


## Animation

| Basic | Content | Gesture |
| -- | -- | -- | 
| <img src="/gif/Animation/BasicAnim.gif" height="500px"/> | <img src="/gif/Animation/ContentAnim.gif" height="500px"/>| <img src="/gif/Animation/GestureAnim.gif" height="500px"/>

| Infinite | Shimmer | TabBar |
| -- | -- | -- | 
| <img src="/gif/Animation/InfiniteAnim.gif" height="500px"/> | <img src="/gif/Animation/ShimmerAnim.gif" height="500px"/>| <img src="/gif/Animation/TabBarAnim.gif" height="500px"/>

## Sample UI

| Instagram |
| -- |
| <img src="/gif/SampleUI/InstagramDemo.gif" height="500px"/> |

## Canvas

| Overview | Shapes | Text and Image |
| -- | -- | -- |
| <img src="/gif/canvas/canvas.gif" height="500px"/> | <img src="/gif/canvas/shapes.jpg" height="500px"/> | <img src="/gif/canvas/text_and_image.jpg" height="500px"/> | 

| Path | Path Operations | DrawScope Helpers |
| -- | -- | -- |
| <img src="/gif/canvas/path.jpg" height="500px"/> | <img src="/gif/canvas/path_operation.jpg" height="500px"/> | <img src="/gif/canvas/drawscope_helpers.jpg" height="500px"/> |

| Canvas + Touch | Blend Modes | Basic Example |
| -- | -- | -- |
| <img src="/gif/canvas/canvas_and_touch.jpg" height="500px"/> | <img src="/gif/canvas/blendmode.jpg" height="500px"/> | <img src="/gif/canvas/basic_example.jpg" height="500px"/> |

| Android 11 Easter Egg |
| -- |
| <img src="/gif/canvas/android_easter_egg.gif" height="500px"/> |

## Google Maps

| Basic | Marker | Polyline |
| -- | -- | -- |
| <img src="/gif/map/basic_map.gif" height="500px"/> | <img src="/gif/map/marker.jpg" height="500px"/>| <img src="/gif/map/polyline.jpg" height="500px"/>

| Polygon | Circle | Ground Overlay |
| -- | -- | -- |
| <img src="/gif/map/polygon.jpg" height="500px"/> | <img src="/gif/map/circle.jpg" height="500px"/>| <img src="/gif/map/ground_overlay.jpg" height="500px"/>

| Tile Overlay | Indoor Level | Lite Map |
| -- | -- | -- |
| <img src="/gif/map/tile_overlay.jpg" height="500px"/> | <img src="/gif/map/indoor_level.jpg" height="500px"/>| <img src="/gif/map/lite_map.jpg" height="500px"/>

| Place Picker | Navigation Viewer | Projection |
| -- | -- | -- |
| <img src="/gif/map/place_picker.jpg" height="500px"/> | <img src="/gif/map/navigation_viewer.gif" height="500px"/>| <img src="/gif/map/projection.jpg" height="500px"/>

| Cluster | Heat Map | KML |
| -- | -- | -- |
| <img src="/gif/map/cluster.jpg" height="500px"/> | <img src="/gif/map/heat_map.jpg" height="500px"/>| <img src="/gif/map/kml.jpg" height="500px"/>

| GeoJSON | ScaleBar | Snapshot |
| -- | -- | -- |
| <img src="/gif/map/geo_json.jpg" height="500px"/> | <img src="/gif/map/scale_bar.gif" height="500px"/>| <img src="/gif/map/snapshot.jpg" height="500px"/>

| Map in scrollable view | Compose Map In XML |
| -- | -- |
| <img src="/gif/map/map_in_scrollable_view.jpg" height="500px"/> | <img src="/gif/map/compose_maps_in_xml.jpg" height="500px"/> |

## ViewPager

| Horizontal Pager | Horizontal Pager with Tabs | Horizontal Pager with indicator |
| -- | -- | -- |
| <img src="/gif/ViewPager/horizontal_pager.gif" height="500px"/> | <img src="/gif/ViewPager/horizontal_page_with_tab.gif" height="500px"/> | <img src="/gif/ViewPager/horizontal_pager_with_indicator.gif" height="500px"/> |

| Vertical Pager with indicator | Pager with Zoom-in</br>transformation | Pager with Fling Behavior |
| -- | -- | -- |
| <img src="/gif/ViewPager/vertical_pager_with_indicator.gif" height="500px"/> | <img src="/gif/ViewPager/pager_with_zoom_in_transformation.gif" height="500px"/> | <img src="/gif/ViewPager/pager_with_fling_behavior.gif" height="500px"/> |

| Add/Remove pager |
| -- |
| <img src="/gif/ViewPager/add_remove_pager.gif" height="500px"/> | 

## Coming Up
- Navigation
- Theme enhancement

## Our Libraries in JetPackCompose
- [SSJetPackComposeProgressButton](https://github.com/SimformSolutionsPvtLtd/SSJetPackComposeProgressButton) : SSJetPackComposeProgressButton is an elegant button with a different loading animations which makes your app attractive.
- [SSJetpackComposeSwipeableView](https://github.com/SimformSolutionsPvtLtd/SSJetpackComposeSwipeableView) : SSJetpackComposeSwipeableView is a small library which provides support for the swipeable views. You can use this in your lazyColumns or can add a simple view which contains swipe to edit/delete functionality.
- [SSComposeOTPPinView](https://github.com/SimformSolutionsPvtLtd/SSComposeOTPPinView) : A custom OTP view to enter a code usually used in authentication. It includes different types of OTPViews which is easy to use and configure your own view and character of OTP using all the attributes.

## Official Documentations
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Jetpack Compose Pathways](https://developer.android.com/courses/pathways/compose)
- [Jetpack Compose Samples](https://github.com/android/compose-samples)

## Find this samples useful? ❤️
Support it by joining __[stargazers]__ for this repository.⭐

## How to Contribute🤝

Whether you're helping us fix bugs, improve the docs, or a feature request, we'd love to have you! 💪
Check out our __[Contributing Guide]__ for ideas on contributing.

## Bugs and Feedback
For bugs, feature requests, and discussion please use __[GitHub Issues]__.

## Awesome Mobile Libraries
- Check out our other available [awesome mobile libraries](https://github.com/SimformSolutionsPvtLtd/Awesome-Mobile-Libraries)

## Main Contributors

<table>
  <tr>
    <td align="center"><a href="https://github.com/nikunj-b-simform"><img src="https://avatars.githubusercontent.com/u/86602550?s=100" width="100px;" alt=""/><br /><sub><b>Nikunj Buddhadev</b></sub></a></td>
    <td align="center"><a href="https://github.com/mdhanif-simformsolutions"><img src="https://avatars.githubusercontent.com/u/63775307?s=100" width="100px;" alt=""/><br /><sub><b>Mohammed Hanif</b></sub></a></td>
    <td align="center"><a href="https://github.com/ShwetaChauhan18"><img src="https://avatars.githubusercontent.com/u/34509457?s=100" width="100px;" alt=""/><br /><sub><b>Shweta Chauhan</b></sub></a></td>
    <td align="center"><a href="https://github.com/MehulKK"><img src="https://avatars.githubusercontent.com/u/60209725?s=100" width="100px;" alt=""/><br /><sub><b>Mehul Kabaria</b></sub></a></td>
    <td align="center"><a href="https://github.com/ronak-u-simformsolutions"><img src="https://avatars.githubusercontent.com/u/76208433?s=100" width="100px;" alt=""/><br /><sub><b>Ronak Ukani</b></sub></a></td>
    <td align="center"><a href="https://github.com/PayalRajput-Simform"><img src="https://avatars.githubusercontent.com/u/80446376?s=100" width="100px;" alt=""/><br /><sub><b>Payal Rajput</b></sub></a></td>
  </tr>
  <tr>
    <td align="center"><a href="https://github.com/krupa-p-simformsolutions"><img src="https://avatars.githubusercontent.com/u/76939101?s=100" width="100px;" alt=""/><br /><sub><b>Krupa Parekh</b></sub></a></td>
    <td align="center"><a href="https://github.com/priyal-p-simformsolutions"><img src="https://avatars.githubusercontent.com/u/75968888?s=100" width="100px;" alt=""/><br /><sub><b>Priyal Parmar</b></sub></a></td>
    <td align="center"><a href="https://github.com/yashwantGowla"><img src="https://avatars.githubusercontent.com/u/66367742?s=100" width="100px;" alt=""/><br /><sub><b>Yashwant Gowla</b></sub></a></td>
    <td align="center"><a href="https://github.com/Priyankkjain"><img src="https://avatars.githubusercontent.com/u/20212314?s=100" width="100px;" alt=""/><br /><sub><b>Priyank Jain</b></sub></a></td>
  </tr>
</table>
<br/>

## License
```
MIT License

Copyright (c) 2022 Simform Solutions

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)
   [git-repo-url]: <https://github.com/SimformSolutionsPvtLtd/SSComposeCookBook.git>
   [stargazers]: <https://github.com/SimformSolutionsPvtLtd/SSComposeCookBook/stargazers>
   [Contributing Guide]: <https://github.com/SimformSolutionsPvtLtd/SSComposeCookBook/blob/main/CONTRIBUTING.md>
   [GitHub Issues]: <https://github.com/SimformSolutionsPvtLtd/SSComposeCookBook/issues>
