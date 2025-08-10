package com.sarmad.moody.ui.screen.insights

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarmad.moody.R

@Composable
fun InsightsScreen(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    uiState: InsightsUiState = InsightsUiState(),
    onFetchInsights: () -> Unit,
    onUserMsgShown: () -> Unit,
) {

    LaunchedEffect(Unit) {
        onFetchInsights()
    }

    LaunchedEffect(uiState.userMsg != null) {
        val messageResId = uiState.userMsg
        messageResId?.let { messageResId ->
            Toast.makeText(
                context,
                context.getString(messageResId), Toast.LENGTH_SHORT
            ).show()
            onUserMsgShown()
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.insights),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = stringResource(R.string.insights_detail),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp, start = 16.dp, end = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            items(
                items = uiState.insights,
                key = { entry -> entry.hashCode() }) { entry ->
                InsightItem(insight = entry)
            }
        }
    }
}

@Composable
fun InsightItem(
    modifier: Modifier = Modifier,
    insight: String,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        RoundedInsightText(
            text = insight
        )
    }
}

@Composable
fun RoundedInsightText(text: String) {
    Surface(
        shape = RoundedCornerShape(size = 16.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(all = 16.dp)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun InsightItemPreview() {
    InsightItem(insight = "You are usually happy on rainy days")
}

@Preview(
    showBackground = true
)
@Composable
fun InsightsScreenPreview() {
    InsightsScreen(
        uiState = InsightsUiState(
            insights = listOf(
                "You are usually happy on rainy days",
                "You are usually sad on sunny days",
                "You are usually neutral on cloudy days",
            )
        ),
        onFetchInsights = {},
        onUserMsgShown = {}
    )
}
