package com.jorgecamarena.firstcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorgecamarena.firstcomposeapp.ui.*
import com.jorgecamarena.firstcomposeapp.ui.themeLayouts.LayoutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            MyComposeApp()
            LayoutsTheme {
                Layouts()
            }
        }
    }
}

@Composable
fun MyComposeApp() {
    FirstComposeAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            Greeting()
        }
    }
}

@Composable
fun Greeting(names: List<String> = listOf("Android", "there")) {
    val image = ImageBitmap.imageResource(id = R.drawable.header)
    val counterState = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
    ) {

        Column(modifier = Modifier.weight(1f)) {

            val imageModifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(4.dp))

            Image(
                bitmap = image,
                contentDescription = "Header",
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(16.dp))

            Text(text = "A day wandering through the sand hills " +
                    "in Shark Fin Cove, and a few of the sights I saw",
                style = typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = "This is a row", style = typography.body2)
            Text(text = "This is a new row", style = typography.body2)

            NameList(names = List(1000) {"Hello Android #$it"})
        }

        Spacer(
            modifier =
            Modifier
                .height(16.dp)
        )

        ButtonCounter(
            count = counterState.value,
            updateCount = {
                newCount ->
                counterState.value = newCount
            }
        )

    }
}

@Composable
fun ButtonCounter(count: Int, updateCount: (Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Button(
                onClick = { updateCount(count + 1) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (count > 5) Color.Green else Color.White
                )
            ) {
                Text(text = "I've been clicked $count times")
            }
        }
    }
}

@Composable
fun ListItem(name: String) {
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(if (isSelected) Color.Red else Color.Transparent)

    Text(
        text = "Hello $name!",
        modifier = Modifier
            .padding(24.dp)
            .background(color = backgroundColor)
            .clickable( onClick = { isSelected = !isSelected }),
        style = MaterialTheme.typography.body1
    )
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    FirstComposeAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            Greeting()
        }
    }
}

@Composable
fun NameList(names: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(names) { name ->
            ListItem(name = name)
            Divider(color = Color.Black)
        }
    }
}
