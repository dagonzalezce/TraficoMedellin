package com.unalMed.TraficoMedellin.geometria

case class Velocidad( private var _magnitud : Double, private var _angulo : Angulo  ) {
  this.magnitud_=(_magnitud)
  this.angulo_=( _angulo )
    
  def magnitud = _magnitud
  def magnitud_= (magnitud: Double): Unit = {if(magnitud < 0) _magnitud = 0 else _magnitud = magnitud}
  
  def angulo = _angulo
  def angulo_= (angulo: Angulo): Unit = { _angulo.angulo_=(angulo.angulo) }
}

object Velocidad{
  def aMetrosPorSegundo(magnitudEnKmPorHora : Double): Double={
      magnitudEnKmPorHora/3.6
  }
  
  def aKilometrosPorHora(magnitudEnMetrosPorSegundo : Double): Double={
      magnitudEnMetrosPorSegundo*3.6
  }
}