package com.unalMed.TraficoMedellin.geometria

case class Aceleracion( private var _magnitud : Double) {
  this.magnitud_=(_magnitud)
  
  def magnitud = _magnitud
  def magnitud_= (magnitud: Double): Unit = {if(magnitud < 0) _magnitud = 0 else _magnitud = magnitud}
}

object Aceleracion{
  def aMetrosPorSegundo(magnitudEnKmPorHora : Double): Double={
      magnitudEnKmPorHora/3.6
  }
  
  def aKilometrosPorHora(magnitudEnMetrosPorSegundo : Double): Double={
      magnitudEnMetrosPorSegundo*3.6
  }
}  