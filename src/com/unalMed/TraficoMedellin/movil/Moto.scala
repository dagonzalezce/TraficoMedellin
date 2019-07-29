package com.unalMed.TraficoMedellin.movil

case class Moto() extends Vehiculo{
  val placa: String= generarPlacaAleatoria()
   
  def generarPlacaAleatoria() : String ={
       Vehiculo.letrasAleatorias(3)+
                    Vehiculo.numerosAleatorios(2)+
                    Vehiculo.letrasAleatorias(1)
  }
}