package com.example.infor.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.infor.DestinationScreen
import com.example.infor.R
import com.example.infor.navigateTo


enum class BottomNavigationItem(val icon: Int, val navDestination: DestinationScreen){
    HOME(R.drawable.home, DestinationScreen.HomeScreen),
    SEARCH(R.drawable.search, DestinationScreen.Search),
    MESSAGE(R.drawable.messenger, DestinationScreen.ChatList),
    PROFIlE(R.drawable.user, DestinationScreen.Profile)

}

@Composable
fun BottomNavigationMenu (
    selectedItem: BottomNavigationItem,
    navController: NavController,

){
    Row (modifier = Modifier
        .wrapContentHeight()
        .padding(top = 10.dp)
        .background(Color.White),
        verticalAlignment = Alignment.Top){
        for (item in BottomNavigationItem.values()){

            Image(painter = painterResource(id = item.icon), contentDescription = null,
                modifier = Modifier.size(40.dp).padding(4.dp).weight(1f).clickable {
                    navigateTo(navController,item.navDestination.route)
                },
                colorFilter = if(item==selectedItem)
                    ColorFilter.tint(color = Color.Black)
                else
                    ColorFilter.tint(color= Color.Gray)
            )
        }
    }
}