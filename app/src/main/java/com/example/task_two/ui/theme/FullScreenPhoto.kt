import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun FullScreenPhoto(photoUrl: String, onClose: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        val scale = remember { androidx.compose.runtime.mutableStateOf(1f) }

        Image(
            painter = rememberAsyncImagePainter(photoUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value
                )
                .pointerInput(Unit) {
                    detectTransformGestures { _, _, zoom, _ ->
                        scale.value *= zoom
                    }
                }
        )

        androidx.compose.material3.IconButton(
            onClick = onClose,
            modifier = Modifier
                .padding(16.dp)
                .align(androidx.compose.ui.Alignment.TopEnd)
        ) {
            androidx.compose.material3.Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.Close,
                contentDescription = "Close"
            )
        }
    }
}