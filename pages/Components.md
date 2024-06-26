# Components

Explore the various UI components available in Jetpack Compose and how to use them in your applications.

| App Bar | Bottom Navigation | Button |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/appbar/TopAppBarActivity.kt#L32" target="_blank"><img src="/gif/AppBar.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/bottomnav/BottomNavigationActivity.kt#L49" target="_blank"><img src="/gif/BottomNav.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/button/ButtonActivity.kt#L43" target="_blank"><img src="/gif/Button.jpg" height="500px"/></a> |

## App Bar

App bar provides a consistent layout for the top of the screen, typically used for branding, navigation, and actions. Find examples of how app bar can be implemented with different styles.

- [Simple App Bar with no actions]
- [App Bar with Left icon action]
- [App Bar with Right icon action]
- [App Bar with Right amd Left icon action]
- [App Bar with multiple Right icon action]
- [Custom App Bar with Left icon action and Title text in Center]

## Bottom Navigation

A navigation component placed at the bottom of the screen, allowing users to quickly switch between primary destinations in an app.

- [Bottom navigation bar]

## Button

- [Various button styles]

| Checkbox | Dialog | Dropdown |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/checkbox/CheckBoxActivity.kt#L50" target="_blank"><img src="/gif/Checkbox.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/dialog/DialogActivity.kt#L51" target="_blank"><img src="/gif/Dialog.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/dropdownmenu/DropDownMenuActivity.kt#L62" target="_blank"><img src="/gif/Dropdown.gif" height="500px"/></a> |

## Checkbox

A component that allows users to make a binary choice, typically used in forms and lists.

- [Check box with different styles]

## Dialog

This example demonstrates implementing two types of dialogs in Jetpack Compose: a standard AlertDialog and a customizable dialog. The DialogActivity manages dialog states, such as whether the dialog can be dismissed with a back press or outside click. The UI provides options to open these dialogs and showcases how to customize them, including setting titles, content, and buttons.

- [Check dialog activity]

## Dropdown

This code demonstrates various dropdown menu implementations in Jetpack Compose. It includes simple dropdowns, dropdowns with custom background colors, dropdowns with icons, and disabled dropdowns. The DropDownMenuActivity manages the state of these dropdowns and displays them with different configurations, offering examples of how to customize their appearance and behavior.

- [Simple dropdown]

- [Custom background color dropdown]

- [Custom dropdown with icons in dropdown item]:

- [Disable selection dropdown]:

| FAB | Modal Drawer | Bottom Drawer |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/floatingactionbutton/FloatingActionButtonActivity.kt#L50" target="_blank"><img src="/gif/FAB.png" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/navigationdrawer/ModalDrawerActivity.kt#L69" target="_blank"><img src="/gif/ModalDrawer.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/navigationdrawer/BottomDrawerActivity.kt#L50" target="_blank"><img src="/gif/BottomDrawer.gif" height="500px"/></a> |

## FAB

The code showcases different styles of Floating Action Buttons (FABs) in Jetpack Compose, including simple, custom-colored, square-shaped, and extended FABs with or without icons. It uses a `Scaffold` layout in `FloatingActionButtonActivity` to manage these FABs and demonstrates customization and user interaction handling in the `FabButtons` composable.

- [Simple FAB]
- [FAB custom color]
- [Square FAB]
- [Simple FAB with custom content (similar of Exntended FAB)]
- [Extentded FAB]
- [Extentded FAB wihout icon]
- [Extentded FAB wihout square shape]

## Modal Drawer

A drawer that slides in from the side and overlays the content, typically used for navigation.

- [Model drawer example]

## Bottom Drawer

A drawer that slides up from the bottom of the screen, providing additional content or actions.

- [Bottom drawer example]

| Radio Button | Slider | Text |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/radiobutton/RadioButtonActivity.kt#L43" target="_blank"><img src="/gif/Radio.png" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/slider/SliderActivity.kt#L51" target="_blank"><img src="/gif/Sliders.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/textstyle/SimpleTextActivity.kt#L79" target="_blank"><img src="/gif/Text.png" height="500px"/></a> |

## Radio Button

- [Simple Radio Button with Label]
- [Disabled Radio Button]
- [Disabled selected Radio Button]
- [Custom selected color Radio Button]
- [Custom unselected color Radio Button]

## Slider

The SliderActivity demonstrates various types of sliders in Jetpack Compose, including continuous, discrete, and range sliders with custom styling. It uses remember and mutableStateOf to manage slider states within a themed UI.

- [Continuous Slider]
- [Discrete Slider with custom color]
- [Range Continuous Slider]
- [Range Discrete Slider]

## Text

The Text Styles showcases different text styles in Jetpack Compose, featuring various font sizes, weights, and colors. Dive into expressive typography with headers, captions, and body text, all styled dynamically to enhance readability and visual appeal.

- [Different text styles]

| Textfield | Theme | ZoomView |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/textfield/TextFieldActivity.kt#L82" target="_blank"><img src="/gif/TextFields.png" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/theme/ThemeActivity.kt#L58" target="_blank"><img src="/gif/Theme.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/zoomview/ZoomViewActivity.kt#L51" target="_blank"><img src="/gif/Zoomview.gif" height="500px"/></a> |

## Textfield

A text field that enables users to input and edit text, offering a versatile component for various forms and data entry tasks.

- [Basic Textfield which does not contain placeholder or any decorations]
- [Simple textfield With label and placeholder]
- [Textfield with different coloured text input]
- [Simple Outlined textfield without placeholder]
- [Phone number textfield with max length as 10]
- [Outlined Password textfield Without label]

## Theme

ThemeActivity allows users to select a color theme for the screen. It features a grid of color options that update the app's theme on selection, providing a visual preview with a checkmark to indicate the current selection.

- [Theme Activity]

## ZoomView

ZoomViewActivity provides an interactive zoomable image view. Users can pinch to zoom and drag to pan across the image, with changes in scale and position reflected in real-time.

- [Zoom View Activity]

| MagnifierView | TabBar | DatePicker |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/magnifierview/MagnifierViewActivity.kt#L90" target="_blank"><img src="/gif/magnifierView.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/tabarlayout/TabBarLayoutActivity.kt#L54" target="_blank"><img src="/gif/TabBar.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/datepicker/DatePickerActivity.kt#L63" target="_blank"><img src="/gif/DatePicker.gif" height="500px"/></a> |

## MagnifierView

The MagnifierViewActivity demonstrates an image with a magnifier effect that adjusts based on user touch. It uses pointerInteropFilter to update the magnifier's size and position, with a zoom factor of 2x. The magnifier follows the user's touch, enlarging the image in the touched area and disappearing when the touch is lifted.

- [Magnifier View]

## TabBar

This code demonstrates various implementations of tab bars using Jetpack Compose, including simple text tabs, icon tabs with indicators, and more complex layouts with text, icons, indicators, and dividers. It includes both fixed and scrollable tab bars with customizable appearance and behavior.

- [Simple text tabBar]

- [Simple Icon tabBar with indicator]

- [TabBar with text,icon,indicator and divider]

- [Scrollable icon tabBar]

- [Scrollable tabBar with text,icon and divider]

## DatePicker

A component that lets users select a date from a calendar view.

- [Date Picker View]

| TimePicker | ImagePicker | ParallaxEffect |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/timepicker/TimePickerActivity.kt#L66" target="_blank"><img src="/gif/TimePicker.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/imagepicker/ImagePickerActivity.kt#L77" target="_blank"><img src="/gif/ImagePicker.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/parallaxeffect/ParallaxEffectActivity.kt#L83" target="_blank"><img src="/gif/ParallaxEffect.gif" height="500px"/></a> |

## TimePicker

A component that allows users to select a specific time.

- [Time Picker View]

## ImagePicker

A component for selecting images from the device's gallery or camera.

- [Image Picker View]

## ParallaxEffect

A visual effect where background content moves at a different speed than foreground content, creating a sense of depth.

- [ParallaxEffect View]

| Compose views in Xml | XML views in Compose |
| -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/xmls/ComposeInXmlViews.kt" target="_blank"><img src="/gif/ComposeViewsInXml.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/xmls/ComposeInXmlViews.kt#L48" target="_blank"><img src="/gif/XmlViewInCompose.png" height="500px"/></a> |

## Compose views in Xml

We might need to migrate from XML to Jetpack Compose, but doing so all at once may not be feasible. We can achieve this gradually by embedding Compose components within XML layouts and XML views within Compose, allowing for a seamless transition.

- [Compose In Xml]

## XML views in Compose

Similar to Compose in XML we can use XML views in Compose.

- [Xml in Compose]

## Other References

Checkout some other cool stuff for components of Jetpack Compose.

- [Building an Exploding FAB Transition With Jetpack Compose]

<!-- Code Links -->

[Simple App Bar with no actions]: /app/src/main/java/com/jetpack/compose/learning/appbar/TopAppBarActivity.kt#L56

[App Bar with Left icon action]: /app/src/main/java/com/jetpack/compose/learning/appbar/TopAppBarActivity.kt#L63

[App Bar with Right icon action]: /app/src/main/java/com/jetpack/compose/learning/appbar/TopAppBarActivity.kt#L81

[App Bar with Right amd Left icon action]: /app/src/main/java/com/jetpack/compose/learning/appbar/TopAppBarActivity.kt#L99

[App Bar with multiple Right icon action]: /app/src/main/java/com/jetpack/compose/learning/appbar/TopAppBarActivity.kt#L128

[Custom App Bar with Left icon action and Title text in Center]: /app/src/main/java/com/jetpack/compose/learning/appbar/TopAppBarActivity.kt#L158

[Bottom navigation bar]: /app/src/main/java/com/jetpack/compose/learning/bottomnav/BottomNavigationActivity.kt#L69

[Various button styles]: /app/src/main/java/com/jetpack/compose/learning/button/ButtonActivity.kt#L43

[Check box with different styles]: /app/src/main/java/com/jetpack/compose/learning/checkbox/CheckBoxActivity.kt#L50

[Check dialog activity]: /app/src/main/java/com/jetpack/compose/learning/dialog/DialogActivity.kt#L51

[Simple dropdown]: /app/src/main/java/com/jetpack/compose/learning/dropdownmenu/DropDownMenuActivity.kt#L119C16-L119C31

[Custom background color dropdown]: /app/src/main/java/com/jetpack/compose/learning/dropdownmenu/DropDownMenuActivity.kt#L152C16-L152C48

[Custom dropdown with icons in dropdown item]: /app/src/main/java/com/jetpack/compose/learning/dropdownmenu/DropDownMenuActivity.kt#L187

[Disable selection dropdown]: /app/src/main/java/com/jetpack/compose/learning/dropdownmenu/DropDownMenuActivity.kt#L187

[Simple FAB]: /app/src/main/java/com/jetpack/compose/learning/floatingactionbutton/FloatingActionButtonActivity.kt#L86C15-L86C25

[FAB custom color]: /app/src/main/java/com/jetpack/compose/learning/floatingactionbutton/FloatingActionButtonActivity.kt#L93C15-L93C31

[Square FAB]: /app/src/main/java/com/jetpack/compose/learning/floatingactionbutton/FloatingActionButtonActivity.kt#L104

[Simple FAB with custom content (similar of Exntended FAB)]: /app/src/main/java/com/jetpack/compose/learning/floatingactionbutton/FloatingActionButtonActivity.kt#L114C15-L114C72

[Extentded FAB]: /app/src/main/java/com/jetpack/compose/learning/floatingactionbutton/FloatingActionButtonActivity.kt#L131

[Extentded FAB wihout icon]: /app/src/main/java/com/jetpack/compose/learning/floatingactionbutton/FloatingActionButtonActivity.kt#L140

[Extentded FAB wihout square shape]: /app/src/main/java/com/jetpack/compose/learning/floatingactionbutton/FloatingActionButtonActivity.kt#L140

[Model drawer example]: /app/src/main/java/com/jetpack/compose/learning/navigationdrawer/ModalDrawerActivity.kt#L69

[Bottom drawer example]: /app/src/main/java/com/jetpack/compose/learning/navigationdrawer/BottomDrawerActivity.kt#L50

[Simple Radio Button with Label]: /app/src/main/java/com/jetpack/compose/learning/radiobutton/RadioButtonActivity.kt#L91C20-L91C50

[Disabled Radio Button]: /app/src/main/java/com/jetpack/compose/learning/radiobutton/RadioButtonActivity.kt#L107C20-L107C41

[Disabled selected Radio Button]: /app/src/main/java/com/jetpack/compose/learning/radiobutton/RadioButtonActivity.kt#L121C20-L121C50

[Custom selected color Radio Button]: /app/src/main/java/com/jetpack/compose/learning/radiobutton/RadioButtonActivity.kt#L131

[Custom unselected color Radio Button]: /app/src/main/java/com/jetpack/compose/learning/radiobutton/RadioButtonActivity.kt#L149

[Continuous Slider]: /app/src/main/java/com/jetpack/compose/learning/slider/SliderActivity.kt#L62

[Discrete Slider with custom color]: /app/src/main/java/com/jetpack/compose/learning/slider/SliderActivity.kt#L84

[Range Continuous Slider]: /app/src/main/java/com/jetpack/compose/learning/slider/SliderActivity.kt#L104

[Range Discrete Slider]: /app/src/main/java/com/jetpack/compose/learning/slider/SliderActivity.kt#L122

[Different text styles]: /app/src/main/java/com/jetpack/compose/learning/textstyle/SimpleTextActivity.kt#L79x  

[Basic Textfield which does not contain placeholder or any decorations]: /app/src/main/java/com/jetpack/compose/learning/textfield/TextFieldActivity.kt#L103

[Simple textfield With label and placeholder]: /app/src/main/java/com/jetpack/compose/learning/textfield/TextFieldActivity.kt#L124

[Textfield with different coloured text input]: /app/src/main/java/com/jetpack/compose/learning/textfield/TextFieldActivity.kt#L139

[Simple Outlined textfield without placeholder]: /app/src/main/java/com/jetpack/compose/learning/textfield/TextFieldActivity.kt#L158

[Phone number textfield with max length as 10]: /app/src/main/java/com/jetpack/compose/learning/textfield/TextFieldActivity.kt#L173

[Outlined Password textfield Without label]: /app/src/main/java/com/jetpack/compose/learning/textfield/TextFieldActivity.kt#L192

[Theme Activity]: /app/src/main/java/com/jetpack/compose/learning/theme/ThemeActivity.kt#L58

[Zoom View Activity]: /app/src/main/java/com/jetpack/compose/learning/zoomview/ZoomViewActivity.kt#L70

[Magnifier View]: /app/src/main/java/com/jetpack/compose/learning/magnifierview/MagnifierViewActivity.kt#L90

[Simple text tabBar]: /app/src/main/java/com/jetpack/compose/learning/tabarlayout/TabBarLayoutActivity.kt#L84

[Simple Icon tabBar with indicator]: /app/src/main/java/com/jetpack/compose/learning/tabarlayout/TabBarLayoutActivity.kt#L06

[TabBar with text,icon,indicator and divider]: /app/src/main/java/com/jetpack/compose/learning/tabarlayout/TabBarLayoutActivity.kt#L136

[Scrollable icon tabBar]: /app/src/main/java/com/jetpack/compose/learning/tabarlayout/TabBarLayoutActivity.kt#L173

[Scrollable tabBar with text,icon and divider]: /app/src/main/java/com/jetpack/compose/learning/tabarlayout/TabBarLayoutActivity.kt#L199

[Date Picker View]: /app/src/main/java/com/jetpack/compose/learning/datepicker/DatePickerActivity.kt#L63

[Time Picker View]: /app/src/main/java/com/jetpack/compose/learning/timepicker/TimePickerActivity.kt#L66

[Image Picker View]: /app/src/main/java/com/jetpack/compose/learning/imagepicker/ImagePickerActivity.kt#L77

[ParallaxEffect View]: /app/src/main/java/com/jetpack/compose/learning/parallaxeffect/ParallaxEffectActivity.kt#L83

[Compose In Xml]: /app/src/main/java/com/jetpack/compose/learning/xmls/ComposeInXmlViews.kt

[Xml in Compose]: /app/src/main/java/com/jetpack/compose/learning/androidviews/AndroidViews.kt

<!-- Reference Links -->

[Building an Exploding FAB Transition With Jetpack Compose]: https://joebirch.co/android/building-an-exploding-fab-transition-in-jetpack-compose/
