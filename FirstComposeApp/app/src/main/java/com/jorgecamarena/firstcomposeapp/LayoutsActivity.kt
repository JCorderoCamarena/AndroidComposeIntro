package com.jorgecamarena.firstcomposeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorgecamarena.firstcomposeapp.ui.themeLayouts.LayoutsTheme
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt


class LayoutsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsTheme {
                Layouts()
            }
        }
    }
}

@Composable
fun Layouts() {

    val scaffoldState = rememberScaffoldState()

    // Consider negative values to mean 'cut corner' and positive values to mean 'round corner'
    val sharpEdgePercent = -50f
    val roundEdgePercent = 45f
    // Start with sharp edges
    val animatedProgress = remember { Animatable(sharpEdgePercent) }
    // Create a coroutineScope for the animation
    val coroutineScope = rememberCoroutineScope()

    // animation value to animate shape
    val progress = animatedProgress.value.roundToInt()

    // When progress is 0, there is no modification to the edges so we are just drawing a rectangle.
    // This allows for a smooth transition between cut corners and round corners.
    val fabShape = if (progress < 0) {
        CutCornerShape(abs(progress))
    } else if (progress == roundEdgePercent.toInt()) {
        CircleShape
    } else {
        RoundedCornerShape(progress)
    }
    // lambda to call to trigger shape animation
    val changeShape: () -> Unit = {
        val target = animatedProgress.targetValue
        val nextTarget = if (target == roundEdgePercent) sharpEdgePercent else roundEdgePercent
        coroutineScope.launch {
            animatedProgress.animateTo(
                targetValue = nextTarget,
                animationSpec = TweenSpec(durationMillis = 600)
            )
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CustomTopBar()
        },
        drawerContent = { Text("Drawer content") },
        bottomBar = {
            CustomBottomBar(fabShape, coroutineScope, scaffoldState)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Change shape") },
                onClick = changeShape,
                shape = fabShape
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) { innerPadding ->
        BodyContent(
            Modifier
                .padding(innerPadding)
                .padding(8.dp))
    }
}

@Composable
fun CustomBottomBar(fabShape: Shape, coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    BottomAppBar(cutoutShape = fabShape) {
        IconButton(
            onClick = {
                coroutineScope.launch { scaffoldState.drawerState.open() }
            }
        ) {
            Icon(Icons.Filled.Menu, contentDescription = "Localized description")
        }
    }
}

@Composable
fun CustomTopBar() {
    TopAppBar(
        title = {
            Text(text = "Layouts Sample")
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Facebook, contentDescription = null)
            }
        }
    )
}

@Composable
fun BodyContent(modifier: Modifier) {
    Column(modifier = modifier) {
        LazyList()
    }
}

@Composable
fun LazyList() {
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Row {
        Button(onClick = {
            coroutineScope.launch {
                scrollState.animateScrollToItem(0)
            }
        }) { Text(text = "Scroll to the top") }

        Button(onClick = {
            coroutineScope.launch {
                scrollState.animateScrollToItem(listSize -1)
            }
        }) { Text(text = "Scroll to the end") }
    }

    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp, start = 15.dp, end = 15.dp)
    ) {
        items(100) {
            ImageListItem(it)
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CoilImage(
            data =  "https://developer.android.com/images/brand/Android_Robot.png",
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}


@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row (modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colors.surface)
        .clickable { /* Do nothing */ }
        .padding(16.dp)
    ) {
        Row() {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
            ) {
                // TODO: Image pending
            }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotographerCardPreview() {
    LayoutsTheme {
        Layouts()
//        PhotographerCard()
    }
}
