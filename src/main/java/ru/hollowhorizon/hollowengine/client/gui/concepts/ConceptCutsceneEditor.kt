package ru.hollowhorizon.hollowengine.client.gui.concepts

import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.util.Color
import ru.hollowhorizon.hc.client.kool.KoolScreen

val rows = listOf(
  "Position",
  "Rotation",
  "Scale"
)
val maxTicks = 1000*20
var interval = 8

object CutsceneEditor: KoolScreen({
  setupUiScene()

  addPanelSurface {
    modifier
      .size(Grow.Std, Grow.Std)
      .backgroundColor(Color("000000aa"))

    Column(width = Grow.Std, height = Grow.Std) {
      val scrollState = rememberScrollState()

      // Временная шкала (горизонтальный скролл)
      Row(width = Grow.Std, height = 30.dp) {
        Box(width = 120.dp) {} // Пустое место под заголовки
        ScrollArea(width = Grow.Std, height = Grow.Std, state = scrollState, scrollbarColor = Color(1f, 1f, 1f, 0f)) {
          modifier.allowOverScroll(true, false)

          Row(width = Grow.Std) {
            for (t in 0..maxTicks) { // Длинная шкала
              val value =
                if(t % 5 == 0 || t == 0)
                  "$t" to 2.dp
                else
                  " " to 1.dp
              Box {
                Text(value.first) { modifier.margin(start = interval.dp) }

                Box(width = value.second, height = 10.dp) { // Верхние белые палочки
                  modifier
                    .alignY(AlignmentY.Bottom)
                    .backgroundColor(Color.WHITE)
                }
              }
            }
          }
        }
      }

      Box(height = 2.dp, width = Grow.Std) {
        modifier.backgroundColor(Color.BLACK)
      } // Горизонтальная линия

      // Контент с параметрами
      Row(width = Grow.Std, height = Grow.Std) {
        Column(width = 120.dp) {
          for (row in rows) {
            Box(width = Grow.Std, height = 30.dp) {
              modifier.backgroundColor(Color.BLACK)

              Text(row) {
                modifier
                  .background(
                    RectGradientBackground(
                      Color("FF0000"), Color("00FF00"),
                      0.dp, 30.dp,
                      (30*64).dp, 30.dp
                    )
                  )
                  .size(120.dp, FitContent)
                  .align(AlignmentX.Start)
                  .padding(4.dp)
              }
            }
          }
        }

        ScrollArea(width = Grow.Std, height = Grow.Std, state = scrollState) {
          modifier.allowOverScroll(true, true)

          Column(width = Grow.Std) {
            for (row in rows) {
              Row(width = Grow.Std, height = 30.dp) {
                for (t in 0..maxTicks) { // Бесконечный таймлайн
                  val value =
                    if(t % 5 == 0 || t == 0)
                      2.dp
                    else
                      1.dp

                  Box(width = interval.dp, height = Grow.Std) {
                    modifier.backgroundColor(Color.DARK_GRAY)

                    Box(width = value, height = Grow.Std) { // Нижние красные палочки
                      modifier.backgroundColor(Color.LIGHT_GRAY)
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
})