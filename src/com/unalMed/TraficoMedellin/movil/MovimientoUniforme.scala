package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.geometria._
import Math.cos
import Math.sin

trait MovimientoUniforme {
  
  
  def aumentaPosicion(p: Punto,veloc: Velocidad, dt: Int): Punto ={
    
    val px = (p.x+(veloc.magnitud * Math.cos(veloc.angulo.angulo) * dt)).toInt
    val py = (p.y+(veloc.magnitud * Math.sin(veloc.angulo.angulo) * dt)).toInt
    val pn = new Punto(px,py)
    pn
    
  }
  
}
