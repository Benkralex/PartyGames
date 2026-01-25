package de.benkralex.partygames.lanParty.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.qrose.options.QrBrush
import io.github.alexzhirkevich.qrose.options.solid
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun QRCode(
    modifier: Modifier = Modifier,
    qrModifier: Modifier = Modifier,
    data: String,
    backgroundColor: Color = Color(255, 255, 255),
    foregroundColor: Color = Color(0, 0, 0),
    subtitle: Boolean = true,
    qrSize: Dp = 275.dp,
) {
    val qrCodePainter = rememberQrCodePainter(
        data = data,
    ) {
        colors {
            dark = QrBrush.solid(foregroundColor)
            light = QrBrush.solid(backgroundColor)
        }
    }

    Column(
        modifier = modifier
            .width(qrSize),
    ) {
        Image(
            painter = qrCodePainter,
            contentDescription = "QR Code: $data",
            modifier = qrModifier
                .background(backgroundColor)
                .size(qrSize)
                .padding(8.dp),
        )
        if (subtitle) {
            Spacer(Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f),
                text = data,
                textAlign = TextAlign.Center,
            )
        }
    }
}