package com.unalMed.TraficoMedellin.vias

class Semaforo (val tiempoVerde: Int, val tiempoAmarillo: Int, val via: Via, val nodo: Interseccion){
  private var _estado = "Rojo" 
 
  
  def estado = _estado
  def estado_=(estado: String)= _estado = estado
  
  
  
}