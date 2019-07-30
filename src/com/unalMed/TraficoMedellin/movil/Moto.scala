package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.vias._

case class Moto(inO: Interseccion, inD: Interseccion) extends Vehiculo(inO, inD){
  val placa: String= generarPlacaAleatoria()
   
  def generarPlacaAleatoria() : String ={
       Vehiculo.letrasAleatorias(3)+
                    Vehiculo.numerosAleatorios(2)+
                    Vehiculo.letrasAleatorias(1)
  }
}