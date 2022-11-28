package space.pandaer.composepandaer.compose.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//带错误检查的TextField


//1.定义状态
class TextFieldState(
    private val textChecker: (String) -> Boolean,
    private val errorMsgBlock: (String) -> String,
    val label: String
) {
    var text by mutableStateOf("")//文本的状态
    var everFocus by mutableStateOf(false) //以前是否被点击过
    var curFocus by mutableStateOf(false) //当前的点击状态
    private var isAllowShowError by mutableStateOf(false) //是否能显示错误 --只要被点击过一次就能显示错误

    val isValid
        get() = textChecker(text)

    fun onFocusChange(focused: Boolean) {
        curFocus = focused
        if (focused) everFocus = true
    }

    //启用显示错误
    fun allowShowError() {
        isAllowShowError = everFocus
    }

    //判断是否该显示错误
    fun showError() = !isValid && isAllowShowError


    //获取错误信息
    fun errorMsg(): String? {
        return if (showError()) errorMsgBlock(text)
        else null
    }

}


@Composable
fun PTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    imeAction: ImeAction,
    onImeAction: ()->Unit,
) {

    Column(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = state.text,
            onValueChange = { state.text = it },
            isError = state.showError(),
            label = {
                Text(text = state.label)
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged {
                    state.curFocus = it.isFocused
                    if (it.isFocused) state.everFocus = it.isFocused
                    if (!state.curFocus) state.allowShowError()
                },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = imeAction

            ),
            keyboardActions = KeyboardActions(onDone = {
                onImeAction()
            })
        )

        state.errorMsg()
            ?.let {
                Text(
                    text = it,
                    modifier = modifier.padding(start = 4.dp),
                    color = Color.Red,
                    fontSize = 10.sp
                )
            }


    }


}