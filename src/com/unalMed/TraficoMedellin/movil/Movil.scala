package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.geometria._

abstract class Movil ( private val _pos: Punto, private val _vel: Velocidad){
  private var _posicion = new Punto( _pos.x, _pos.y )
  private var _velocidad = new Velocidad( _vel.magnitud, new Angulo( _vel.angulo.angulo ) )
  
  def posicion = _posicion
  def posicion_= (posicion: Punto): Unit = { _posicion.x = posicion.x
                                             _posicion.y = posicion.y} 
  
  def velocidad = _velocidad
  
  def velocidad_= (velocidad: Velocidad): Unit = { _velocidad.angulo = velocidad.angulo
                                                   _velocidad.magnitud = velocidad.magnitud }
  
  def mover: Punto 
  
  def anguloDireccion : Angulo = _velocidad.angulo
  
} 
