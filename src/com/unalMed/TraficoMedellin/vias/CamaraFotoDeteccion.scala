package com.unalMed.TraficoMedellin.vias

import com.unalMed.TraficoMedellin.movil._
import com.unalMed.TraficoMedellin.simulacion.Simulacion
import com.unalMed.TraficoMedellin.geometria.Punto

class CamaraFotoDeteccion (val via:Via, distanciaOrigen: Double){
  
    
  def medidorVelocidad (vehiculo:Vehiculo, velocidad: Double) {
    
    if (velocidad > via.velMax){
      val comparendo = new Comparendo(vehiculo, velocidad, via.velMax)
      Simulacion.listaComparendos += comparendo        
    }    
  } 
  
   def calcularUbicacion(): Punto = { 

    val angulo = via.calcularAnguloRecta().toRadians

    val y = via.origen.y+(distanciaOrigen*Math.sin(angulo)).toInt
    val x = via.origen.x+(distanciaOrigen*Math.cos(angulo)).toInt
    Punto(x, y)
     
  }
}
