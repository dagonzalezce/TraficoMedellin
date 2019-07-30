package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.geometria._

abstract class Movil ( var posicion: Punto, var velocidad: Velocidad){
  
  def aumentaPosicion(p: Punto,veloc: Velocidad, dt: Int): Punto 
  
  def anguloDireccion = this.velocidad.angulo
  
} 
