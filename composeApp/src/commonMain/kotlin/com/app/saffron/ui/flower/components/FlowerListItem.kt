package com.app.saffron.ui.flower.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.app.saffron.data.models.FlowerDto
import org.jetbrains.compose.resources.painterResource
import saffron.composeapp.generated.resources.Res
import saffron.composeapp.generated.resources.compose_multiplatform

@Composable
fun FlowerListItem(
    flower: FlowerDto,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    Surface(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .clickable(onClick = onClick)
            .height(IntrinsicSize.Min),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                var imageLoadResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }
                val painter = rememberAsyncImagePainter(
                    model = flower.freeImageUrls[0],
                    onSuccess = {
                        imageLoadResult =
                            if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                                Result.success(it.painter)
                            } else {
                                Result.failure(Exception("Invalid image size"))
                            }
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        imageLoadResult = Result.failure(it.result.throwable)
                    }
                )
                when (val result = imageLoadResult) {
                    null -> CircularProgressIndicator()
                    else -> {
                        Image(
                            painter = if (result.isSuccess) painter else {
                                painterResource(Res.drawable.compose_multiplatform)
                            },
                            contentDescription = flower.title,
                            contentScale = if (result.isSuccess) {
                                ContentScale.Crop
                            } else {
                                ContentScale.Fit
                            },
                            modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                                .clip(RoundedCornerShape(32.dp))
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = flower.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = flower.subtitle,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
//                Row {
//                    flower.commonColors.map{ color ->
                Text(
                    text = flower.commonColors.joinToString(" | "),
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
//                    }
//                }
            }
        }
    }
}