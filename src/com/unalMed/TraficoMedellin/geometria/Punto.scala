package com.unalMed.TraficoMedellin.geometria

class Punto ( private var _x: Int, private var _y: Int ) {
  this.x_=( _x ) // Revisar los valores de x
  this.y_=( _y ) // Revisar los valores de y
  
  def x = _x
  def x_= (x: Int): Unit = {if(x < 0) _x = 0 else _x = x}
  
  def y = _y
  def y_= (y: Int): Unit = {if( y < 0 )_y = 0 else _y = y}
  
}