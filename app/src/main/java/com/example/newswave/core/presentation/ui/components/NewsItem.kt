package com.example.newswave.core.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import coil.compose.AsyncImage
import com.example.newswave.R
import com.example.newswave.core.presentation.ui.state.NewsItemUiState

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    item: NewsItemUiState,
    onClickNews: (String) -> Unit,
    onClickBookmark: (NewsItemUiState) -> Unit,
    onShareNews :(String)-> Unit,
){
    Box (
        modifier= modifier
            .padding(vertical = 24.dp)
            .clickable { onClickNews(item.link) }
    ){
        Row (
            modifier = modifier
                .fillMaxWidth()
                .height(140.dp)
        ){
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(140.dp)
            )
            Spacer(modifier = modifier.width(8.dp))

            Column (
                modifier = modifier
                    .fillMaxHeight()
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "By ${item.author ?: "Author"}",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row (

                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = item.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = modifier.width(4.dp))
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = modifier.width(4.dp))

                    Text(
                        text = item.publishDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

            }
        }
        Box (
            modifier = modifier.align(Alignment.BottomEnd)
        ){
            var expanded by remember { mutableStateOf(false) }
            IconButton(
                onClick = { expanded=true }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_menu),
                    contentDescription = null
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false},
                properties = PopupProperties(focusable = true)
            ) {

                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.share)) },
                    onClick = { onShareNews(item.link)
                              expanded = false},
                    leadingIcon = {Icon(painter = painterResource(id = R.drawable.ic_share ), contentDescription = null) }

                )
                DropdownMenuItem(
                    text = { Text(stringResource(id = if (item.isBookmarked) R.string.unbookmark else R.string.bookmark)) },
                    onClick = {
                        onClickBookmark(item)
                              expanded = false},
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = if (!item.isBookmarked) R.drawable.ic_bookmark_outlined else R.drawable.ic_bookmark_contained),
                            contentDescription = null
                        )
                    }

                )

            }
        }
    }


}


