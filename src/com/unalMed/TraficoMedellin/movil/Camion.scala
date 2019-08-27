
package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.vias._
import com.unalMed.TraficoMedellin.geometria._

class Camion(v: Velocidad, acel: Aceleracion ) extends Vehiculo(v,acel){
  var placa: String= generarPlacaAleatoria()
   
  def generarPlacaAleatoria() : String ={
       "R"+Vehiculo.numerosAleatorios(5)
  }
}
