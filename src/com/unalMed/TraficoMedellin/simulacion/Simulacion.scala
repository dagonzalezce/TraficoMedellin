package com.unalMed.TraficoMedellin.simulacion


object Simulacion extends Runnable{
      
  def run(){
    println("hola")
    val dt: Int= ArchivosJson.parametros.dt
    println(dt)
  }
}