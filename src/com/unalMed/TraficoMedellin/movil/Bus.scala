package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.vias._
import com.unalMed.TraficoMedellin.geometria._

case class Bus(inO: Interseccion, inD: Interseccion, v: Velocidad ) extends Vehiculo(inO, inD,v){
  val placa: String= generarPlacaAleatoria()
   
  def generarPlacaAleatoria() : String ={
       Vehiculo.letrasAleatorias(3)+
                    Vehiculo.numerosAleatorios(3)
}


}
