package tw.edu.pu.csim.sj.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp // 導入用於設置字體大小的 unit

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {

//載入圖片
    //val imageBitmap = ImageBitmap.imageResource(R.drawable.horse0)
    val imageBitmaps = listOf(
        ImageBitmap.imageResource(R.drawable.horse0),
        ImageBitmap.imageResource(R.drawable.horse1),
        ImageBitmap.imageResource(R.drawable.horse2),
        ImageBitmap.imageResource(R.drawable.horse3)
    )


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)
    ){
        Canvas (modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume() // 告訴系統已經處理了這個事件
                    gameViewModel.MoveCircle( dragAmount.x, dragAmount.y)
                }
            }
        ) {
            // 繪製圓形
            drawCircle(
                color = Color.Red,
                radius = 100f,
                center = Offset(gameViewModel.circleX, gameViewModel.circleY)
            )
            for(i in 0..2){
                drawImage(
                    image = imageBitmaps[gameViewModel.horses[i].numberNo],
                    dstOffset = IntOffset(
                        gameViewModel.horses[i].horseX,
                        gameViewModel.horses[i].horseY),
                    dstSize = IntSize(200, 200)
                )
            }


        }

        // 顯示分數
        Text(
            text = "分數: ${gameViewModel.score}", // 顯示分數
            fontSize = 24.sp, // 設定字體大小
            color = Color.Black,
            modifier = Modifier.align(Alignment.TopCenter) // 置中於頂部
        )

        // 其他資訊（原始程式碼中的 Text）
        Text(text = message + gameViewModel.screenWidthPx.toString() +
                "*" + gameViewModel.screenHeightPx.toString(),
            modifier = Modifier.align(Alignment.TopStart) // 置於左上角
        )

        // 遊戲開始按鈕
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom, // 置於底部
            horizontalAlignment = Alignment.CenterHorizontally // 水平置中
        ) {
            Button(onClick = {
                gameViewModel.gameRunning = true
                gameViewModel.ResetScore() // 可選：在開始新遊戲時重設分數
                gameViewModel.StartGame()
            }){
                Text("遊戲開始")
            }
        }
    }
}