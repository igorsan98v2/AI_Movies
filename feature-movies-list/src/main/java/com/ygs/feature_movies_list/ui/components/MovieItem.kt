package com.ygs.feature_movies_list.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ygs.feature_movies_list.models.UIMovieSummary
import com.ygs.utils.formatCurrency

@Composable
fun MovieItem(
    movie: UIMovieSummary,
    onMovieClicked: () -> Unit,
    showAlternativeBackground: Boolean
) {
    val background = if (showAlternativeBackground) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.primary
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .clickable { onMovieClicked() }
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = movie.name,
                style = TextStyle(color = MaterialTheme.colorScheme.onPrimary, fontSize = 18.sp)
            )

            Text(
                text = formatCurrency(movie.price.toDouble()), // Use the formatCurrency utility function to format the price
                style = TextStyle(color = MaterialTheme.colorScheme.secondary, fontSize = 14.sp)
            )
        }
    }
}
