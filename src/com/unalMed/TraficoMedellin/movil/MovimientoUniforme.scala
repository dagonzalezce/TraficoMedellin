package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.geometria._
import Math.cos
import Math.sin
import scala.math.cos
import scala.math.sin

trait MovimientoUniforme {
  var _posicion : Punto
  var _velocidad : Velocidad
  
  // https://es.wikipedia.org/wiki/Movimiento_rectil%C3%ADneo_uniforme
  def mover (dt: Int): Unit = {
    _posicion.x = ( _posicion.x.toDouble + _velocidad.magnitud * math.cos( _velocidad.angulo.angulo ) * dt ).toInt
    _posicion.y = ( _posicion.y.toDouble + _velocidad.magnitud + math.sin( _velocidad.angulo.angulo ) * dt ).toInt
  }
    
  
  
}
