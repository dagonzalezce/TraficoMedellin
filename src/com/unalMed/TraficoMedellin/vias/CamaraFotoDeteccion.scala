package com.unalMed.TraficoMedellin.vias

import com.unalMed.TraficoMedellin.movil._
import com.unalMed.TraficoMedellin.simulacion.Simulacion
import com.unalMed.TraficoMedellin.geometria.Punto

class CamaraFotoDeteccion (val via:Via, distanciaOrigen: Double){
  
    
  def medirVelocidad (dt:Int) {
    
    val vehiculos = Simulacion.viajes.filter(_.viaActual==via).map(_.vehiculo)
    
    vehiculos.foreach(x=>if(vehiculoCercaDeCamara(x, dt)){
         if (x.velocidadAct.magnitud > via.velMax){
        val comparendo = new Comparendo(x, x.velocidadAct.magnitud, via.velMax, via)
        if(Simulacion.listaComparendos.filter(_.vehiculo.placa==x.placa).filter(_.via==via)
            .isEmpty){
        Simulacion.listaComparendos += comparendo    }
        }
      } 
    )
    
        
  } 
  
   def calcularUbicacion(): Punto = { 

    val angulo = via.calcularAnguloRecta().toRadians

    val y = via.origen.y+(distanciaOrigen*Math.sin(angulo)).toInt
    val x = via.origen.x+(distanciaOrigen*Math.cos(angulo)).toInt
    Punto(x, y)
     
  }
  
   def vehiculoCercaDeCamara(vehiculo:Vehiculo, dt:Int): Boolean={
    var distancia= Math.sqrt(Math.pow((vehiculo.posicion.x - calcularUbicacion.x),2) + Math.pow((vehiculo.posicion.y - calcularUbicacion.y),2))
    distancia = Math.abs(distancia)
    (distancia <= vehiculo.velocidadMax.magnitud*dt)
  }
}
