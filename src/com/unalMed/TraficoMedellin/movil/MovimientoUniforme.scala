package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.geometria._
import Math.cos
import Math.sin
import scala.math.cos
import scala.math.sin

trait MovimientoUniforme {
  var _posicion : Punto
  var _velocidad : Velocidad
  
  def moverUniforme : Unit = {
    _posicion.x = ( _posicion.x.toDouble + _velocidad.magnitud * math.cos( _velocidad.angulo.angulo ) ).toInt
    _posicion.y = ( _posicion.y.toDouble + _velocidad.magnitud + math.sin( _velocidad.angulo.angulo ) ).toInt
  }
    
  
  
}
