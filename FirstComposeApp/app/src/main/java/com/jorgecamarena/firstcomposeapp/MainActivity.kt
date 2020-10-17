package com.jorgecamarena.firstcomposeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.jorgecamarena.firstcomposeapp.ui.FirstComposeAppTheme
import com.jorgecamarena.firstcomposeapp.ui.typography

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeApp()
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
fun Greeting() {
    val image = imageResource(id = R.drawable.header)

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        val imageModifier = Modifier
            .preferredHeight(180.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(4.dp))

        Image(image, modifier = imageModifier,
            contentScale = ContentScale.Crop)

        Spacer(Modifier.preferredHeight(16.dp))

        Text(text = "A day wandering through the sand hills " +
                "in Shark Fin Cove, and a few of the sights I saw",
            style = typography.h6,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = "This is a row", style = typography.body2)
        Text(text = "This is a new row", style = typography.body2)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FirstComposeAppTheme {
        Greeting()
    }
}