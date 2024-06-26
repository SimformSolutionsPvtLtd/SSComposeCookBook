# Constraint Layout

| Barrier | Guideline | Chain |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/constraintlayout/BarrierActivity.kt#L49" target="_blank"><img src="/gif/Constraint/Barrier.png" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/constraintlayout/GuidelineActivity.kt#L48" target="_blank"><img src="/gif/Constraint/Guideline.png" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/constraintlayout/ChainActivity.kt#L52" target="_blank"><img src="/gif/Constraint/Chain.png" height="500px"/></a> |

## Guideline
A Guideline is an invisible, positionable line that can be used to position and align views within a ConstraintLayout. Guidelines can be oriented either horizontally or vertically, and their position can be specified as a percentage of the parent layout's width or height, or as a fixed distance from the start, end, top, or bottom.

## Barrier
A Barrier is a dynamic line that automatically positions itself based on the size of one or more views. Unlike a Guideline, a Barrier's position can change based on the size and content of the views it references.

## Chain
In ConstraintLayout, a chain is a group of views that are linked together to create flexible and dynamic layouts. By creating a chain, you can control the distribution of space and alignment of multiple views along a single axis (horizontal or vertical). Chains can be configured to distribute space equally, spread out, or to align views based on their size, helping to create responsive layouts that adapt to different screen sizes and orientations.