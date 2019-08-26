package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.geometria._

abstract class Movil ( private val _pos: Punto, private val _vel: Velocidad, private val _ace: Aceleracion){
  //Constructor
  var _posicion = new Punto( _pos.x, _pos.y )
  var _velocidad = new Velocidad( _vel.magnitud, new Angulo( _vel.angulo.angulo ) )
  var _acelereacion = new Aceleracion( _ace.magnitud)
  
  //Getter y setters
  def posicion = _posicion
  def posicion_= (posicion: Punto): Unit = { _posicion.x = posicion.x
                                             _posicion.y = posicion.y} 
  
  def velocidad = _velocidad
  
  def velocidad_= (velocidad: Velocidad): Unit = { _velocidad.angulo = velocidad.angulo
                                                   _velocidad.magnitud = velocidad.magnitud }
  
  def aceleracion = _acelereacion
  
  def aceleracion_= (aceleracion: Aceleracion): Unit = { _acelereacion.magnitud = aceleracion.magnitud }
  
  //Metodo declarado para mover el objeto movil en una franja dt
  def mover (dt : Int): Unit 
  
  //Devuelve la direccion del vehiculo
  def anguloDireccion : Angulo = _velocidad.angulo
  
} 
