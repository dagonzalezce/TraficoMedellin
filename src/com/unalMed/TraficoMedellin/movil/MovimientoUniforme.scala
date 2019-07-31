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
  /*
   * dv/dt = 0
   * Velocidad = constante
   * V = vector velocidad ( constante )
   * s0 = posicion inicial
   * ds/dt = V => ds = Vdt => s = s0 - V*t
   * descomponiendo en coordenadas
   *  x = x0 - |V|*cos(V angulo en radianes)* (t0 - t1) 
   *  y = y0 - |V|*sin(V angulo en radianes)* (t0 - t1)
   *  t = 1 seg Reemplazando
   *  x = x0 - |V|*cos(V angulo en radianes) * dt
   *  y = y0 - |V|*sin(V angulo en radianes) * dt
   */
  def mover( dt:Int = 1 ): Unit = {
    _posicion.x = ( _posicion.x.toDouble + _velocidad.magnitud * math.cos( _velocidad.angulo.angulo.toRadians ) * dt ).toInt
    _posicion.y = ( _posicion.y.toDouble + _velocidad.magnitud + math.sin( _velocidad.angulo.angulo.toRadians ) * dt ).toInt
  }
    
  
  
}
