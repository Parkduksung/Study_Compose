package com.example.practice2.presenter.github_users.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.example.practice2.domain.model.GithubUser
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun GithubUserListItem(
    githubUser: GithubUser,
    onItemClick: (GithubUser) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(githubUser) }
            .height(150.dp)
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {

        CoilImage(
            modifier = Modifier
                .align(CenterVertically)
                .height(80.dp)
                .width(80.dp),
            imageModel = githubUser.image,
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.background,
                highlightColor = Color.Black,
                durationMillis = 1000,
                dropOff = 0.65f,
                tilt = 20f
            ),
            contentScale = ContentScale.Crop,
            failure = {
                Text(text = "image request failed.")
            })

        Text(
            modifier = Modifier.align(CenterVertically),
            text = githubUser.userId,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = Bold,
        )

    }
}