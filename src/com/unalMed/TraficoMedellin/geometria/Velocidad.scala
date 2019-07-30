package com.unalMed.TraficoMedellin.geometria

class Velocidad( private var _magnitud : Int, private var _angulo : Angulo  ) {
  this.magnitud_=(_magnitud)
  this.angulo_=( _angulo )
    
  def magnitud = _magnitud
  def magnitud_= (magnitud: Int): Unit = {if(magnitud < 0) _magnitud = 0 else _magnitud = magnitud}
  
  def angulo = _angulo
  def angulo_= (angulo: Angulo): Unit = { _angulo.angulo_=(angulo.angulo) }
}