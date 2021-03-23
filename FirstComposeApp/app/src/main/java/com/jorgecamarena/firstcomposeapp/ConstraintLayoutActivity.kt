package com.jorgecamarena.firstcomposeapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeast
import com.jorgecamarena.firstcomposeapp.ui.themeLayouts.LayoutsTheme

class ConstraintLayoutActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            LayoutsTheme {
                ConstraintLayoutContent()
            }
        }
    }
}

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {

        // Create references for the composables to constraint
        val (button1, button2, text) = createRefs()

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text(text = "Button 1")
        }

        Text(
            text = "Text",
            Modifier.constrainAs(text) {
                top.linkTo(button1.bottom, margin = 16.dp)
                centerAround(button1.end)
            }
        )

        val barrier = createEndBarrier(button1, text)
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text(text = "Button 2")
        }

    }
}

@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        var guideline = createGuidelineFromStart(fraction = 0.5f)

        Text(
            "This is a very very very very very very very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent.atLeast(100.dp)
            }
        )

    }
}

@Composable
fun DecoupleConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupleConstraints(margin = 16.dp)
        } else {
            decoupleConstraints(margin = 32.dp)
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.layoutId("button")
            ) {
                Text(text = "Button")
            }

            Text(
                text = "Text",
                modifier = Modifier.layoutId("text")
            )
        }
    }
}

fun decoupleConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }

        constrain(text) {
            top.linkTo(button.bottom, margin = margin)
            centerHorizontallyTo(button)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDecoupledConstraintLayout() {
    LayoutsTheme {
        DecoupleConstraintLayout()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLargeConstraintLayout() {
    LayoutsTheme {
        LargeConstraintLayout()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConstraintLayoutContent() {
    LayoutsTheme {
        ConstraintLayoutContent()
    }
}