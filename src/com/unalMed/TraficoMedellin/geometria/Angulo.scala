package com.unalMed.TraficoMedellin.geometria

case class Angulo( private var _angulo : Double) {
  
  def angulo = _angulo
  def angulo_= (angulo: Double): Unit = {if( angulo < 0 || angulo > 360 )_angulo = 0 else _angulo = angulo}
  
  
  
}