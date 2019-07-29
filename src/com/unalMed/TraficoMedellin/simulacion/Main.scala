package com.unalMed.TraficoMedellin.simulacion

object Main {
  def main(args: Array[String]): Unit = {
     new Thread(Simulacion).start
  }
}