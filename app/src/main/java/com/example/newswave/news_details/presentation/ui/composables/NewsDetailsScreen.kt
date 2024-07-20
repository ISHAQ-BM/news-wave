package com.example.newswave.news_details.presentation.ui.composables

import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material.icons.sharp.MailOutline
import androidx.compose.material.icons.sharp.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.newswave.R
import com.example.newswave.core.presentation.ui.theme.NewsWaveTheme


@Composable
fun NewsDetailsScreen(
    link: String
) {
    @OptIn(ExperimentalMaterial3Api::class)
    (Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {},
                actions = {
                    Row {
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.ic_outline_bookmark
                                ),
                                contentDescription = ""
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.ic_share
                                ),
                                contentDescription = ""
                            )
                        }
                    }
                }
            )
        },

        ) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding())
        ) {
            WebView(mUrl = link)
        }

    })
}


@Composable
fun WebView(
    mUrl: String,
) {

    AndroidView(factory = {
        android.webkit.WebView(it).apply {
            this.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            this.webViewClient = WebViewClient()
        }
    }, update = {
        it.loadUrl(mUrl)
    })
}

@Composable
fun Details(
    modifier: Modifier=Modifier
){
    Scaffold {
        Column (
            modifier = Modifier.padding(it.calculateTopPadding())
        ){
            AsyncImage(
                model = "https://media.gettyimages.com/id/1311148884/vector/abstract-globe-background.jpg?s=612x612&w=0&k=20&c=9rVQfrUGNtR5Q0ygmuQ9jviVUfrnYHUHcfiwaH5-WFE=",
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
                    .height(230.dp)
            )
            Text(text = "title")
            PublishInfo("","","")
            Reactions(numberOfReaction = 3)
        }
    }
}

@Composable
fun Reactions(
    numberOfReaction: Int,
    modifier: Modifier=Modifier
){
    Row (
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween

    ){
        reactions.forEach {it ->
            Reaction(
                icon = it.second,
                textId = it.first,
                numberOfReaction = numberOfReaction)
        }
    }
}

@Composable
fun PublishInfo(
    creatorName:String,
    creatorImageUrl:String,
    publishedAt:String,
    modifier: Modifier = Modifier

){
    Row (
        modifier=modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ){
        AsyncImage(
            model = creatorImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(52.dp)
                .clip(CircleShape)
        )
        Text(
            text = "by ishaq",
            modifier=modifier.weight(1f)
        )
        Text(text = "1hr ago")

    }
}

@Composable
fun Reaction(
    icon:ImageVector,
    @StringRes textId:Int,
    numberOfReaction:Int
){
    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Text(text = stringResource(id = textId,numberOfReaction))
    }
}

@Preview(showBackground = true)
@Composable
fun PubPrev(){
    NewsWaveTheme {
       PublishInfo(
           creatorName = "ishaq",
           creatorImageUrl = "https://media.gettyimages.com/id/1311148884/vector/abstract-globe-background.jpg?s=612x612&w=0&k=20&c=9rVQfrUGNtR5Q0ygmuQ9jviVUfrnYHUHcfiwaH5-WFE=",
           publishedAt ="1hr ago" )
    }

}


val reactions= listOf<Pair<Int,ImageVector>>(
    Pair(R.string.comments,Icons.Sharp.MailOutline),
    Pair(R.string.likes,Icons.Sharp.FavoriteBorder),
    Pair(R.string.share,Icons.Sharp.Share)

    )


@Preview(showBackground = true)
@Composable
fun ReactionP(){
    NewsWaveTheme {
        Reactions(numberOfReaction = 3) }
}

@Preview(showBackground = true)
@Composable
fun DetailsPrev(){
    NewsWaveTheme {
        Details()
    }

}