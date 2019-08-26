package com.unalMed.TraficoMedellin.geometria

case class Aceleracion( private var _magnitud : Double) {
  this.magnitud_=(_magnitud)
  
  def magnitud = _magnitud
  def magnitud_= (magnitud: Double): Unit = {if(magnitud < 0) _magnitud = 0 else _magnitud = magnitud}
}

