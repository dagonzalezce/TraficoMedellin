
package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.vias._
import com.unalMed.TraficoMedellin.geometria._

class Carro(inO: Interseccion, inD: Interseccion, vel: Velocidad, acel: Aceleracion) extends Vehiculo(inO, inD, vel,acel){
  val placa: String= generarPlacaAleatoria()
   
  def generarPlacaAleatoria() : String ={
       Vehiculo.letrasAleatorias(3)+
                    Vehiculo.numerosAleatorios(3)
  }
}
