package com.example.practice2.presenter.github_users

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.practice2.presenter.github_users.component.GithubUserListItem

@Composable
fun GithubUsersScreen(
    navController: NavController,
    githubUsersViewModel: GithubUsersViewModel = hiltViewModel()
) {

    val githubUsersState = githubUsersViewModel.state.value

    var inputSearchState by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(7f),
                    text = "Github 유저 검색",
                    style = MaterialTheme.typography.h6,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
            }
            Divider(color = Color.LightGray, thickness = 0.5.dp)

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                value = inputSearchState,
                onValueChange = {
                    inputSearchState = it
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        githubUsersViewModel.searchUser(inputSearchState)
                    }
                ),
                trailingIcon = {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = "clear text",
                        modifier = Modifier
                            .clickable {
                                inputSearchState = ""
                            }
                    )
                }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(githubUsersState.users) { user ->
                    GithubUserListItem(user) {
                        Log.d("결과", it.repoUrl)
                    }
                }
            }
        }

        if (githubUsersState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .semantics { testTag = "progress" })
        }
    }

}