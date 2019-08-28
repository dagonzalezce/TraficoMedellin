package com.unalMed.TraficoMedellin.movil
import com.unalMed.TraficoMedellin.movil._
import com.unalMed.TraficoMedellin.vias.Via

class Comparendo(val vehiculo: Vehiculo, val velVehiculo: Double, val maxVelocidad: Double, val via:Via) {

  
  def excesoPorcentaje :Double = {
    ((velVehiculo-maxVelocidad)/maxVelocidad)*100
    
    }
  
}