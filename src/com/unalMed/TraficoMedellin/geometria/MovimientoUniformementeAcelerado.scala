package com.unalMed.TraficoMedellin.geometria

import com.unalMed.TraficoMedellin.geometria._

trait MovimientoUniformeAcelerado {
  var _posicion : Punto
  var _velocidadMax : Velocidad
  var _velocidadAct: Velocidad
  var _aceleracion: Aceleracion
  
  def moverUniformemente( dt:Int = 1 ): Unit = {   
    _posicion.x = ( _posicion.x.toDouble + _velocidadMax.magnitud * math.cos( _velocidadMax.angulo.angulo.toRadians ) * dt ).toInt
    _posicion.y = ( _posicion.y.toDouble + _velocidadMax.magnitud * math.sin( _velocidadMax.angulo.angulo.toRadians ) * dt ).toInt
  }
    
  def moverUniformementeAcelerado( dt:Int = 1): Unit = {
    
    _velocidadAct.magnitud = _velocidadAct.magnitud + _aceleracion.magnitud
    
    if (_velocidadAct.magnitud>_velocidadMax.magnitud){
      _velocidadAct.magnitud=_velocidadMax.magnitud
          
    } 
    _posicion.x = ( _posicion.x.toDouble + _velocidadAct.magnitud * math.cos( _velocidadAct.angulo.angulo.toRadians ) * dt ).toInt
     _posicion.y = ( _posicion.y.toDouble + _velocidadAct.magnitud * math.sin( _velocidadAct.angulo.angulo.toRadians ) * dt ).toInt
  }
  
  def moverUniformementeDesacelerado( dt:Int = 1): Unit = {
    
    _velocidadAct.magnitud = _velocidadAct.magnitud - _aceleracion.magnitud
    
    if ((_velocidadAct.magnitud<0)) {
      _velocidadAct.magnitud = 0
    }
    
    _posicion.x = ( _posicion.x.toDouble + _velocidadAct.magnitud * math.cos( _velocidadAct.angulo.angulo.toRadians ) * dt ).toInt
     _posicion.y = ( _posicion.y.toDouble + _velocidadAct.magnitud * math.sin( _velocidadAct.angulo.angulo.toRadians ) * dt ).toInt
  }
  
}
