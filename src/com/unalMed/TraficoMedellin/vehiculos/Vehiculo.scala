package com.unalMed.TraficoMedellin.vehiculos

import scala.util.Random

abstract class Vehiculo {
  val placa: String
}

object Vehiculo{
  def apply(): Vehiculo = {
    val veh = Camion()
    veh
  }
  
  def letrasAleatorias(n: Int)={
    var letras="ABCDEFGHIJKLMNOPQRSTUVWXYZ" 
    var la=""
    1 to n foreach {x => la+= letras.charAt(Random.nextInt(letras.length())).toString}
    la
  }
  
  def numerosAleatorios(n: Int)={
    var na=""
    1 to n foreach {x => na+= Random.nextInt(10).toString}
    na
  }
}
  
  
