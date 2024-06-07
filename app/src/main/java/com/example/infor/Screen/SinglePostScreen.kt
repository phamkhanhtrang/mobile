package com.example.infor.Screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.infor.DestinationScreen
import com.example.infor.LCViewModel
import com.example.infor.navigateTo
import com.example.infor.ui.theme.Bule1

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListPost (vm: LCViewModel,navController: NavController, userID: String) {
    val post = vm.Post.value.filter {
        it.user.userId == userID
    }

    Scaffold(
        topBar={
            Box(modifier = Modifier.height(24.dp)) {
                Image(Icons.Default.ArrowBack, contentDescription = null,
                    modifier = Modifier.clickable {
                        navigateTo(navController, DestinationScreen.Profile.route)
                    }.size(30.dp))
            }
        },
        content = {
            LazyColumn {
                items(post) { post ->
                    itemp(
                        postID = post.postID,
                        imageProfile = post.user.imageUrl,
                        name = post.user.name,
                        content = post.content,
                        vm = vm,
                        imagePostUrl = post.imageUrl,
                        deletePost = {
                            post.postID?.let {
                                vm.deletePost(postID = post.postID)
                            }
                        }
                    )
                }
            }
        }
    )
}
@OptIn( ExperimentalFoundationApi::class)
@Composable
fun itemp (modifier: Modifier = Modifier,
             imagePostUrl:List<String>?,
             imageProfile: String?,
             name:String?,
             postID: String?,
             content: String?,
           deletePost:()->Unit,
             vm:LCViewModel,
          ){
    val painterProfile = rememberImagePainter(data = imageProfile)
    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 10.dp)){
        val pagerState = rememberPagerState(pageCount = { imagePostUrl?.size ?: 0 })
        Spacer(modifier = Modifier.height(3.dp))
        ConstraintLayout (modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)){
            val (imgAvatar, username, textDelete )= createRefs()
            Image(painter = painterProfile, contentDescription = "profile",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)

                    .constrainAs(imgAvatar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                contentScale = ContentScale.Crop)

            Text(text =name ?:"", fontSize = 12.sp, maxLines = 1,modifier = Modifier
                .width(100.dp)
                .constrainAs(username) {
                    start.linkTo(imgAvatar.end, margin = 6.dp)
                    top.linkTo(parent.top, margin = 10.dp)
                    width = Dimension.fillToConstraints
                },
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(text = "Xóa", fontSize = 12.sp, modifier = Modifier
                .clickable {
                    deletePost.invoke()
                }
                .constrainAs(textDelete) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 10.dp)
                })
        }
        Spacer(modifier = Modifier.height(7.dp))
         Carousel( data = imagePostUrl, pagerState = pagerState)
        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(Color.Black, fontWeight = FontWeight.Bold)){
                append("${name} ")
            }
            append(content)
        }
        Text(
            text = annotatedString,
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}
@OptIn( ExperimentalFoundationApi::class)
@Composable
fun  Carousel(data: List<String>?,pagerState: PagerState) {
    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
            Image(
                painter = rememberAsyncImagePainter(model = data?.getOrNull(page)),
                contentDescription = "post image",
                modifier = Modifier.aspectRatio(16f / 16f),
                contentScale = ContentScale.Crop
            )
        }
    }

    Row(
        Modifier
            .fillMaxWidth()
            .offset(y = 21.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Bule1 else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp)
            )
        }
    }
}