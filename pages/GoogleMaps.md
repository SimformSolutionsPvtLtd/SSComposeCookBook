# Google Maps
Integrating Google Maps in Jetpack Compose involves using the GoogleMap composable to embed and interact with Google Maps within your app, allowing for features like map display, markers, and custom map controls.

## Setup (For Google Map Implementation)
To run the google map samples, you'll have to:

1. Get a Maps API key
2. Add an entry in `local.properties` or `local.defaults.properties` that looks like `MAPS_API_KEY=YOUR_MAPS_KEY`
3. Build and run 

## Examples

| Basic | Marker | Polyline |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/maps/basic/BasicMapActivity.kt#L78" target="_blank"><img src="/gif/map/basic_map.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/maps/marker/MapMarkerActivity.kt#L71" target="_blank"><img src="/gif/map/marker.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/maps/polyline/MapPolylineActivity.kt#L63" target="_blank"><img src="/gif/map/polyline.jpg" height="500px"/></a> |

| Polygon | Circle | Ground Overlay |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/maps/polygon/MapPolygonActivity.kt#L65" target="_blank"><img src="/gif/map/polygon.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/maps/circle/MapCircleActivity.kt#L63" target="_blank"><img src="/gif/map/circle.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/maps/overlay/MapGroundOverlayActivity.kt#L61" target="_blank"><img src="/gif/map/ground_overlay.jpg" height="500px"/></a> |

| Tile Overlay | Indoor Level | Lite Map |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/maps/overlay/MapTileOverlayActivity.kt#L61" target="_blank"><img src="/gif/map/tile_overlay.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/maps/indoorlevel/MapIndoorActivity.kt#L54" target="_blank"><img src="/gif/map/indoor_level.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/maps/basic/LiteMapInListActivity.kt#L58" target="_blank"><img src="/gif/map/lite_map.jpg" height="500px"/></a> |

| Place Picker | Navigation Viewer | Projection |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/maps/place/ui/MapsPlaceActivity.kt#L77" target="_blank"><img src="/gif/map/place_picker.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/maps/place/ui/MapsNavigationActivity.kt#L103" target="_blank"><img src="/gif/map/navigation_viewer.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/maps/projection/MapProjectionActivity.kt#L64" target="_blank"><img src="/gif/map/projection.jpg" height="500px"/></a> |

| Cluster | Heat Map | KML |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/maps/cluster/MapClusterActivity.kt#L61" target="_blank"><img src="/gif/map/cluster.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/maps/overlay/MapHeatMapOverlayActivity.kt#L73" target="_blank"><img src="/gif/map/heat_map.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/maps/overlay/MapKMLOverlayActivity.kt#L63" target="_blank"><img src="/gif/map/kml.jpg" height="500px"/></a> |

| GeoJSON | ScaleBar | Snapshot |
| -- | -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/maps/geojson/MapGeoJsonActivity.kt#L63" target="_blank"><img src="/gif/map/geo_json.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/maps/scalebar/MapScaleBarActivity.kt#L61" target="_blank"><img src="/gif/map/scale_bar.gif" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/maps/snapshot/MapSnapShotActivity.kt#L65" target="_blank"><img src="/gif/map/snapshot.jpg" height="500px"/></a> |

| Map in scrollable view | Compose Map In XML |
| -- | -- |
| <a href="/app/src/main/java/com/jetpack/compose/learning/maps/basic/MapInScrollingActivity.kt#L61" target="_blank"><img src="/gif/map/map_in_scrollable_view.jpg" height="500px"/></a> | <a href="/app/src/main/java/com/jetpack/compose/learning/maps/interoperability/MapsInXMLActivity.kt#L41" target="_blank"><img src="/gif/map/compose_maps_in_xml.jpg" height="500px"/></a> |
