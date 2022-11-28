package space.pandaer.composepandaer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import space.pandaer.composepandaer.compose.input.PTextField
import space.pandaer.composepandaer.compose.input.TextFieldState
import space.pandaer.composepandaer.ui.theme.ComposePandaerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePandaerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        val state = remember {
                            TextFieldState(
                                textChecker = {
                                    it.length >= 3
                                },
                                errorMsgBlock = {
                                    "长度不够：($it)"
                                },
                                label = "密码"
                            )
                        }
                        PTextField(state = state
                            , modifier = Modifier.fillMaxWidth(), imeAction = ImeAction.Done, onImeAction = {

                            })

                        TextField(value = "", onValueChange = {})
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposePandaerTheme {
        Greeting("Android")
    }
}