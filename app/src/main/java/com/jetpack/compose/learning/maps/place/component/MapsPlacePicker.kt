package com.jetpack.compose.learning.maps.place.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetpack.compose.learning.maps.MapVerticalSpace
import com.jetpack.compose.learning.maps.animateBound
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.maps.place.model.AutoCompleteItem
import com.jetpack.compose.learning.maps.place.model.PhotosItem
import com.jetpack.compose.learning.maps.place.model.PlaceDetail
import com.jetpack.compose.learning.maps.place.model.PlaceResult
import com.jetpack.compose.learning.maps.place.model.Resource
import com.jetpack.compose.learning.maps.place.model.Reviews
import com.jetpack.compose.learning.maps.place.viewmodel.PlacePickerViewModel

/**
 * Place Picker example.
 * It will search the place from API and display its details and you can select the place.
 */
@Composable
fun PlacePicker(
    viewModel: PlacePickerViewModel = viewModel(),
    onPlaceSelect: (PlaceResult) -> Unit
) {
    val searchText by viewModel.searchString.collectAsState()
    val searchState by viewModel.searchResultState.collectAsState()
    val placeDetailState by viewModel.placeResultState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentMarkerLatLong, 14f)
    }
    val focusManager = LocalFocusManager.current
    val searchRequester by remember { mutableStateOf(FocusRequester()) }

    LaunchedEffect(placeDetailState) {
        if (placeDetailState is Resource.Success) {
            cameraPositionState.animateBound((placeDetailState as Resource.Success<PlaceDetail>).data.getLatLngBound())
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(contentPadding = PaddingValues(8.dp)) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = viewModel::searchText,
                    placeholder = {
                        Text("Search")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    maxLines = 1,
                    modifier = Modifier
                        .focusRequester(searchRequester)
                        .fillMaxWidth(),
                    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.background,
                        unfocusedBorderColor = MaterialTheme.colors.background,
                        backgroundColor = MaterialTheme.colors.background,
                        textColor = MaterialTheme.colors.onBackground
                    ),
                    trailingIcon = {
                        AnimatedVisibility(visible = searchText.isNotEmpty()) {
                            Icon(
                                Icons.Filled.Clear,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(26.dp)
                                    .clickable { viewModel.searchText("") }
                            )
                        }
                    }
                )
            }
        },
    ) {
        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.animateContentSize()) {
                MapView(modifier = Modifier.weight(1f), cameraPositionState, placeDetailState)
                AnimatedVisibility(visible = uiState.showPlaceDetail) {
                    PlaceDetailStateView(placeDetailState, onPlaceSelect)
                }
            }
            AnimatedVisibility(visible = uiState.showSearchResult) {
                SearchResultStateView(searchState) { place ->
                    focusManager.clearFocus()
                    viewModel.selectPlace(place.place_id)
                }
            }
        }
    }
}

/**
 * Displays entire search view according to API result.
 */
@Composable
private fun SearchResultStateView(
    result: Resource<List<AutoCompleteItem>>,
    onPlaceClick: (AutoCompleteItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .heightIn(0.dp, 250.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
    ) {
        when (result) {
            is Resource.Error -> {
                item {
                    Text(
                        text = result.message,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Resource.Loading -> {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator(modifier = Modifier.padding(vertical = 16.dp))
                    }
                }
            }
            is Resource.Success -> {
                val autoCompleteItems = result.data
                if (autoCompleteItems.isEmpty()) {
                    item {
                        Text(
                            text = "No Data Found",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    items(
                        items = autoCompleteItems,
                        key = {
                            it.place_id
                        },
                    ) {
                        SearchItem(it, onPlaceClick)
                    }
                }
            }
        }
    }
}

/**
 * Single search item. The searched text will be annotated in bold.
 */
@Composable
private fun SearchItem(item: AutoCompleteItem, onPlaceClick: (AutoCompleteItem) -> Unit) {
    Column(modifier = Modifier.clickable { onPlaceClick(item) }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp, 8.dp)
        ) {
            Icon(Icons.Outlined.NearMe, contentDescription = "Location Icon")
            Spacer(modifier = Modifier.requiredWidth(10.dp))
            Text(item.annotatedPlaceName())
        }
        Divider()
    }
}

@Composable
private fun MapView(
    modifier: Modifier,
    cameraPositionState: CameraPositionState,
    placeDetail: Resource<PlaceDetail>
) {
    GoogleMap(modifier = modifier, cameraPositionState = cameraPositionState) {
        if (placeDetail is Resource.Success) {
            Marker(MarkerState(placeDetail.data.getLocation()))
        }
    }
}

/**
 * Displays place detail state view according to api state.
 */
@Composable
private fun PlaceDetailStateView(
    result: Resource<PlaceDetail>,
    onPlaceSelect: (PlaceResult) -> Unit
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.heightIn(0.dp, 300.dp)) {
        when (result) {
            is Resource.Error -> {
                Text(
                    text = result.message,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
            Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .wrapContentSize()
                )
            }
            is Resource.Success -> {
                PlaceDetail(result.data, onPlaceSelect)
            }
        }
    }
}

/**
 * Displays place detail
 * If photos, rating and reviews are present in result then it will be displayed.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PlaceDetail(data: PlaceDetail, onPlaceSelect: (PlaceResult) -> Unit) {
    val verticalScrollState = rememberScrollState()
    val photos = data.photos
    val reviews = data.reviews
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(verticalScrollState)
    ) {
        MapVerticalSpace()
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 3.dp,
                onClick = {
                    onPlaceSelect(data.getPlaceResult())
                }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(60.dp, 70.dp)
                ) {
                    Icon(
                        Icons.Outlined.CheckCircle,
                        contentDescription = "Select",
                        tint = Color(0xFF26A65B),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.requiredWidth(8.dp))
            Text(
                data.getAddress(),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            if (data.rating != null) {
                Spacer(modifier = Modifier.requiredWidth(8.dp))
                Card(shape = RoundedCornerShape(8.dp), elevation = 3.dp) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.size(60.dp, 70.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Star,
                            contentDescription = "Star",
                            tint = Color(0xFFFDCC0D),
                            modifier = Modifier.size(30.dp)
                        )
                        MapVerticalSpace(5.dp)
                        Text(
                            data.rating.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
        if (!photos.isNullOrEmpty()) {
            MapVerticalSpace()
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(
                    items = photos,
                    key = {
                        it.photo_reference
                    },
                ) {
                    PlacePhotoItem(it)
                }
            }
        }
        if (!reviews.isNullOrEmpty()) {
            MapVerticalSpace()
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(
                    items = reviews,
                    key = {
                        it.time.toString() + it.author_name
                    },
                ) {
                    PlaceReviewItem(it)
                }
            }
        }
        MapVerticalSpace()
    }
}

/**
 * Single review item. It display user profile photo, name and actual review.
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
private fun PlaceReviewItem(it: Reviews) {
    val image = rememberImagePainter(
        data = it.profile_photo_url,
        builder = {
            crossfade(true)
        }
    )
    Card(
        modifier = Modifier.padding(end = 16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 3.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp, 8.dp)
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .requiredSize(48.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.requiredWidth(8.dp))
            Column {
                Text(it.author_name, fontWeight = FontWeight.Bold)
                MapVerticalSpace(5.dp)
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        it.text,
                        modifier = Modifier.widthIn(
                            0.dp,
                            LocalConfiguration.current.screenWidthDp.dp / 1.5f
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

/**
 * Single photo item. It display photo of the place.
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
private fun PlacePhotoItem(it: PhotosItem) {
    val image = rememberImagePainter(
        data = it.getPhotoURL(),
        builder = {
            crossfade(true)
        }
    )
    Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier
            .padding(end = 16.dp)
            .requiredWidth(150.dp)
            .requiredHeight(100.dp)
            .clip(shape = RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}

/**
 * Utility function to convert search item into annotated item.
 * This will make searched text bold in result.
 */
private fun AutoCompleteItem.annotatedPlaceName(): AnnotatedString {
    return buildAnnotatedString {
        append(description)
        matched_substrings.forEach {
            addStyle(
                style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp),
                start = it.offset,
                end = it.length + it.offset
            )
        }
    }
}