package com.unalMed.TraficoMedellin.vehiculos

case class Camion() extends Vehiculo{
  val placa: String= generarPlacaAleatoria()
   
  def generarPlacaAleatoria() : String ={
       "R"+Vehiculo.numerosAleatorios(5)
  }
}