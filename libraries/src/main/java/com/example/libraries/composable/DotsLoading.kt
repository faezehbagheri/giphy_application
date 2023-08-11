import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraries.R

val dotSize = 24.dp // made it bigger for demo
val delayUnit = 300 // you can change delay to change animation speed

@Composable
fun DotsPulsing() {

    @Composable
    fun Dot(
        scale: Float,
        colors: List<Color>
    ) = Spacer(
        Modifier
            .size(dotSize)
            .scale(scale)
            .background(
                brush = Brush.horizontalGradient(
                    colors = colors
                ),
                shape = CircleShape
            )
    )

    val infiniteTransition = rememberInfiniteTransition()

    @Composable
    fun animateScaleWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delayUnit * 4
                0f at delay with LinearEasing
                1f at delay + delayUnit with LinearEasing
                0f at delay + delayUnit * 2
            }
        )
    )

    val scale1 by animateScaleWithDelay(0)
    val scale2 by animateScaleWithDelay(delayUnit)
    val scale3 by animateScaleWithDelay(delayUnit * 2)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val spaceSize = 2.dp

        Dot(
            scale1, colors =
            listOf(
                colorResource(id = R.color.red),
                colorResource(id = R.color.yellow),
                colorResource(id = R.color.green),
            )
        )
        Spacer(Modifier.width(spaceSize))
        Dot(
            scale2, colors =
            listOf(
                colorResource(id = R.color.yellow),
                colorResource(id = R.color.green),
                colorResource(id = R.color.blue),
            )
        )
        Spacer(Modifier.width(spaceSize))
        Dot(
            scale3,
            colors =
            listOf(
                colorResource(id = R.color.green),
                colorResource(id = R.color.blue),
                colorResource(id = R.color.purple),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DotsPreview() = MaterialTheme {
    Column(modifier = Modifier.padding(4.dp)) {
        DotsPulsing()
    }
}