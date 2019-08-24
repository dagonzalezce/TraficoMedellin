
package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.vias._
import com.unalMed.TraficoMedellin.geometria._

class Camion(inO: Interseccion, inD: Interseccion, v: Velocidad ) extends Vehiculo(inO, inD, v){
  val placa: String= generarPlacaAleatoria()
   
  def generarPlacaAleatoria() : String ={
       "R"+Vehiculo.numerosAleatorios(5)
  }
}
