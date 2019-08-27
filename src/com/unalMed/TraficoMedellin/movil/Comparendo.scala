package com.unalMed.TraficoMedellin.movil
import com.unalMed.TraficoMedellin.movil._

class Comparendo(vehiculo: Vehiculo, velVehiculo: Double, maxVelocidad: Double) {
  
  def excesoPorcentaje :Double = {
    (velVehiculo/maxVelocidad)*100
    
    }
  
}