# Canvas

The Canvas API in Jetpack Compose allows for custom drawing within your app. It provides a powerful and flexible way to create complex graphics, animations, and visual effects.

| Overview | Shapes | Text and Image |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/canvas/CanvasActivity.kt" target="_blank"><img src="/gif/canvas/canvas.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/canvas/CanvasShapesActivity.kt#L75" target="_blank"><img src="/gif/canvas/shapes.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/canvas/CanvasTextImageActivity.kt#L89" target="_blank"><img src="/gif/canvas/text_and_image.jpg" height="500px"/></a> |

## Shapes

Learn how to draw basic shapes such as circles, rectangles, and lines. The Canvas API provides a variety of functions to create and customize these shapes.

- [Line Shape]
- [Circle Shape]
- [Arc Shape]
- [Rectangle Shape]
- [Outline Shape]

## Text And Image

Explore how to render text and images on the Canvas. This includes positioning, styling, and scaling to fit your design needs.

- [Text]
- [Image 1]
- [Image 2]

| Path | Path Operations | DrawScope Helpers |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/canvas/CanvasPathsActivity.kt#L75" target="_blank"><img src="/gif/canvas/path.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/canvas/CanvasPathOperationActivity.kt#L79" target="_blank"><img src="/gif/canvas/path_operation.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/canvas/CanvasDrawScopeOperationActivity.kt#L86" target="_blank"><img src="/gif/canvas/drawscope_helpers.jpg" height="500px"/></a> |

## Paths

Paths allow you to create complex shapes by defining a series of points and lines. Learn how to create and manipulate paths to draw intricate designs.

- [Basic Path]
- [With Shapes]
- [Any Polygon]
- [Any Polygon With Progress]

## Path Operations

Explore various Canvas path operations. This section covers all operations related to paths with different shapes, demonstrating how to combine, intersect, and subtract paths to create complex graphical effects.

- [Path Operations]

## Draw Scope Helper

Discover examples of various Canvas draw scope operations. This section includes examples related to insets, translate, rotate, scale, clip path, and clip rect, demonstrating how to enhance your drawing capabilities.

- [Insets]
- [Translate]
- [Rotate]
- [Clip Path]
- [Clip Rect]

| Canvas + Touch | Blend Modes | Basic Example |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/canvas/CanvasTouchOperationActivity.kt#L110" target="_blank"><img src="/gif/canvas/canvas_and_touch.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/canvas/CanvasBlendModesActivity.kt#L106" target="_blank"><img src="/gif/canvas/blendmode.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/canvas/BasicCanvasExampleActivity.kt#L112" target="_blank"><img src="/gif/canvas/basic_example.jpg" height="500px"/></a> |

## Canvas + Touch

Implement touch interactions with the Canvas to create a free draw board. Users can draw anything with different stroke properties, making the drawings dynamic and interactive.

- [With Drag]
- [With Pointer Loop]
- [On Image]

## Blend Modes

Discover how to use blend modes on the Canvas with shapes and images. This section provides examples of combining colors and shapes in different ways to create various visual effects.

- [With Shapes]
- [With Shape and Image]
- [With Shape and Image 2]
- [With Images]

## Basic Example

- [Basic Example]
- [Progress Indicator]
- [Gauge Meter]

| Android 11 Easter Egg |
| -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/canvas/CanvasAndroidEasterEggActivity.kt#L95" target="_blank"><img src="/gif/canvas/android_easter_egg.gif" height="500px"/></a> |

## Android 11 Easter Egg

Draw ester egg animation.

- [Easter Egg]

<!-- Code Links -->

[Line Shape]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasShapesActivity.kt#L99

[Circle Shape]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasShapesActivity.kt#L219

[Arc Shape]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasShapesActivity.kt#L319

[Rectangle Shape]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasShapesActivity.kt#L438

[Outline Shape]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasShapesActivity.kt#L544

[Text]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasTextImageActivity.kt#

[Image 1]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasTextImageActivity.kt#L207

[Image 2]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasTextImageActivity.kt#L291

[Basic Path]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasPathsActivity.kt#L95

[With Shapes]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasPathsActivity.kt#L202

[Any Polygon]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasPathsActivity.kt#L315

[Any Polygon With Progress]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasPathsActivity.kt#L432

[Path Operations]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasPathOperationActivity.kt#L90

[Insets]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasDrawScopeOperationActivity.kt#L117

[Translate]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasDrawScopeOperationActivity.kt#L158

[Rotate]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasDrawScopeOperationActivity.kt#L188


[Clip Path]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasDrawScopeOperationActivity.kt#L225

[Clip Rect]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasDrawScopeOperationActivity.kt#L298

[With Drag]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasTouchOperationActivity.kt#L130

[With Pointer Loop]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasTouchOperationActivity.kt#L222

[On Image]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasTouchOperationActivity.kt#:312

[With Shapes]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasBlendModesActivity.kt#L126

[With Shape and Image]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasBlendModesActivity.kt#L160

[With Shape and Image 2]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasBlendModesActivity.kt#L209

[With Images]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasBlendModesActivity.kt#L286

[Basic Example]: /app/src/main/java/com/jetpack/compose/learning/canvas/BasicCanvasExampleActivity.kt#L131

[Progress Indicator]: /app/src/main/java/com/jetpack/compose/learning/canvas/BasicCanvasExampleActivity.kt#L178

[Gauge Meter]: /app/src/main/java/com/jetpack/compose/learning/canvas/BasicCanvasExampleActivity.kt#L231

[Easter Egg]: /app/src/main/java/com/jetpack/compose/learning/canvas/CanvasAndroidEasterEggActivity.kt#L95
