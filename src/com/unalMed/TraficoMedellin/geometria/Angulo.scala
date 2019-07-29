package com.unalMed.TraficoMedellin.geometria

class Angulo( private var _angulo : Double) {
  
  def angulo = _angulo
  def angulo_= (angulo: Int): Unit = {if( angulo < 0 || angulo > 360 )_angulo = 0 else _angulo = angulo}
  
  
  
}