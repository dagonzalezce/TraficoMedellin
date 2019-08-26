
package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.vias._
import com.unalMed.TraficoMedellin.geometria._

class MotoTaxi(inO: Interseccion, inD: Interseccion, v : Velocidad, acel: Aceleracion ) extends Vehiculo(inO, inD, v,acel){
  val placa: String= generarPlacaAleatoria()
   
  def generarPlacaAleatoria() : String ={
       Vehiculo.numerosAleatorios(3)+
                    Vehiculo.letrasAleatorias(3)
  }
}
