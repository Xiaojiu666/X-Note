package com.gx.note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gx.note.ui.ParamsConfig
import com.gx.note.ui.RichEditor
import com.gx.note.ui.RouteConfig
import com.gx.note.ui.ScaffoldMenuSamples
import com.gx.note.ui.theme.XNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XNoteTheme {
                // A surface container using the 'background' color from the theme
                RichEditor()
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
////                    NavHostDemo()
//                    ScaffoldMenuSamples()
//                }
            }
        }
        KeyboardHandler(findViewById(android.R.id.content)).handleKeyboard()
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}



@Composable
fun NavHostDemo() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = RouteConfig.ROUTE_PAGEONE) {

        composable(RouteConfig.ROUTE_PAGEONE){
            PageOne(navController)
        }


        composable("${RouteConfig.ROUTE_PAGETWO}/{${ParamsConfig.PARAMS_NAME}}/{${ParamsConfig.PARAMS_AGE}}") {

            val argument = requireNotNull(it.arguments)
            val name = argument.getString(ParamsConfig.PARAMS_NAME)
            val age = argument.getInt(ParamsConfig.PARAMS_AGE)
            PageTwo(navController,name,age)
        }

    }
}



    @Composable
fun PageOne(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                Color.White
            )
    ) {
        Text(text = "这是页面1")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            //点击跳转到页面2
            //点击跳转到页面2
            navController.navigate("${RouteConfig.ROUTE_PAGETWO}/guoxu/26")
        }) {
            Text(
                text = "跳转页面2",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun PageTwo(navController: NavController, name: String?, age: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                Color.White
            )
    ) {
        Text(text = "这是页面2")
        Text(text = "Name is ${name}, age is $age")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {            //点击返回页面1
            navController.navigate("${RouteConfig.ROUTE_PAGETWO}/guoxu/26")
        }) {
            Text(
                text = "返回页面1",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun DefaultPreview() {
    XNoteTheme {
        Greeting("Android")
    }
}