package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.geometria._

abstract class Movil ( private val _velMax: Velocidad, private val _ace: Aceleracion){
  //Constructor
  var _velAct: Velocidad = Velocidad(0,Angulo(0))
  var _posicion = new Punto( 0, 0 )
  var _velocidadMax = new Velocidad( _velMax.magnitud, new Angulo( _velMax.angulo.angulo ))
  var _velocidadAct = new Velocidad( _velAct.magnitud, new Angulo( _velAct.angulo.angulo ))
  var _aceleracion = new Aceleracion( _ace.magnitud)
  
  //Getter y setters
  def posicion = _posicion
  def posicion_= (posicion: Punto): Unit = { _posicion.x = posicion.x
                                             _posicion.y = posicion.y} 
  
  def velocidadMax = _velocidadMax
  
  def velocidadMax_= (velocidadMax: Velocidad): Unit = { _velocidadMax.angulo = velocidadMax.angulo
                                                   _velocidadMax.magnitud = velocidadMax.magnitud }
  
  def velocidadAct = _velocidadAct
  
  def velocidadAct_= (velocidadAct: Velocidad): Unit = { _velocidadAct.angulo = velocidadAct.angulo
                                                   _velocidadAct.magnitud = velocidadAct.magnitud }
  
  def aceleracion = _aceleracion
  
  def aceleracion_= (aceleracion: Aceleracion): Unit = { _aceleracion.magnitud = aceleracion.magnitud }
  
  //Metodo declarado para mover el objeto movil en una franja dt
  def mover (dt : Int): Unit 
  
  //Devuelve la direccion del vehiculo
  def anguloDireccion : Angulo = _velocidadMax.angulo
  
} 