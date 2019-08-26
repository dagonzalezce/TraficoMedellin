package com.unalMed.TraficoMedellin.vias

import com.unalMed.TraficoMedellin.movil._
import com.unalMed.TraficoMedellin.simulacion.Simulacion
import com.unalMed.TraficoMedellin.geometria.Punto

class CamaraFotoDeteccion (val via:Via, distanciaOrigen: Double, val ubicacion: Punto){
  
  
  def medidorVelocidad (vehiculo:Vehiculo, velocidad: Double) {
    
    if (velocidad > via.velMax){
      val comparendo = new Comparendo(vehiculo, velocidad, via.velMax)
      Simulacion.listaComparendos += comparendo        
    }    
  }  
     
}
