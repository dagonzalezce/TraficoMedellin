package com.unalMed.TraficoMedellin.vehiculos

case class Carro() extends Vehiculo{
  val placa: String= generarPlacaAleatoria()
   
  def generarPlacaAleatoria() : String ={
       Vehiculo.letrasAleatorias(3)+
                    Vehiculo.numerosAleatorios(3)
  }
}