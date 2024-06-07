package com.example.infor.Screen


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.infor.DestinationScreen
import com.example.infor.ImageStory
import com.example.infor.LCViewModel
import com.example.infor.navigateTo


@Composable
fun StorySection(vm: LCViewModel, navController : NavController) {
    val storyes = vm.storys.value
    val userData = vm.userData.value
    val myStory = storyes.filter {
        it.user.userId == userData?.userId
    }
    val otherStory = storyes.filter {
        it.user.userId != userData?.userId
    }
        Row(modifier = Modifier) {
            if (myStory.isNotEmpty()) {
                val userId = myStory[0].user.userId ?: ""
                ImageStory(
                    imageUrl = myStory[0].user.imageUrl,
                    name = myStory[0].user.name
                ) {
                    navigateTo(
                        navController = navController,
                        DestinationScreen.SingleStory.createRoute(userId = userId)
                    )
                }
            }
            if (otherStory.isNotEmpty()) {
                val uniqueUser = otherStory.map { it.user }.toSet().toList()
                LazyRow(modifier = Modifier.weight(1f)) {
                    items(uniqueUser) { user ->
                        val userId = user.userId ?: ""
                        ImageStory(imageUrl = user.imageUrl, name = user.name) {
                                navigateTo(
                                    navController = navController,
                                    DestinationScreen.SingleStory.createRoute(userId=userId)
                                )
                        }
                    }
                }
            }
        }
    }