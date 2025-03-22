// See README.md for license details.

package blinky

import chisel3._
// _root_ disambiguates from package chisel3.util.circt if user imports chisel3.util._
import _root_.circt.stage.ChiselStage

/**
  * Blink LED at specified interval
  */
class blinky extends Module {
  val io = IO(new Bundle {
    val led = Output(UInt(1.W))
  })

  val CNT_MAX = (50000000/2 - 1).U;
  val ledReg  = RegInit(0.U(1.W))
  val cntReg  = RegInit(0.U(32.W))

  cntReg := cntReg + 1.U
  when(cntReg === CNT_MAX) {
    cntReg := 0.U
    ledReg := ~ledReg
  }

  io.led := ledReg
}

/**
 * Generate Verilog sources and save it in file blinky.v
 */
object blinky extends App {
  ChiselStage.emitSystemVerilogFile(
    new blinky,
    firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
  )
}
