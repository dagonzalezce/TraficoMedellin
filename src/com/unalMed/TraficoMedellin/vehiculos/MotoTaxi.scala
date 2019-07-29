package com.unalMed.TraficoMedellin.vehiculos

case class MotoTaxi() extends Vehiculo{
  val placa: String= generarPlacaAleatoria()
   
  def generarPlacaAleatoria() : String ={
       Vehiculo.numerosAleatorios(3)+
                    Vehiculo.letrasAleatorias(3)
  }
}