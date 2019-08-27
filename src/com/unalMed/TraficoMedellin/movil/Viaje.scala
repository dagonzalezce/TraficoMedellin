package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.vias.Interseccion
import com.unalMed.TraficoMedellin.geometria.GrafoVia
import com.unalMed.TraficoMedellin.geometria.Punto
import com.unalMed.TraficoMedellin.vias.Via
import com.unalMed.TraficoMedellin.simulacion.Simulacion
import com.unalMed.TraficoMedellin.geometria.Angulo
import scala.util.Random
import com.unalMed.TraficoMedellin.simulacion.ArchivosJson


class Viaje (val interseccionOrigen: Interseccion, val interseccionDestino: Interseccion, val vehiculo: Vehiculo){
  
  if(vehiculo.posicion==Punto(0,0)) vehiculo.posicion= interseccionOrigen
  var trayectoVias = GrafoVia.caminoCorto(interseccionOrigen, interseccionDestino)
  var distanciaARecorrer= calcularDistanciaARecorrer
  
  var siguienteInterseccion : Interseccion = interseccionOrigen
  var viaActual : Via=trayectoVias.dequeue
  if(vehiculo.posicion == viaActual.origen){
          siguienteInterseccion= viaActual.fin
        }else if(vehiculo.posicion == viaActual.fin){
          siguienteInterseccion= viaActual.origen
        }
 
  
  
  def avanzar(dt: Int){
    
      var estaEnVerde=false
      var estaEnRojo=false
      var estaEnAmarillo=false
    if(cercaDeInterseccion(siguienteInterseccion, dt)){
      vehiculo.posicion= siguienteInterseccion.asInstanceOf[Punto]
      if(llegoADestino) return
      Simulacion.semaforos.filter(_.nodo==siguienteInterseccion).filter(_.via.nombre == viaActual.nombre)
        .foreach(x=> if(x.estado== "Verde") {estaEnVerde=true})
      if(estaEnVerde){
        if(!trayectoVias.isEmpty) viaActual= trayectoVias.dequeue
        if(vehiculo.posicion == viaActual.origen){
          siguienteInterseccion= viaActual.fin
        }else if(vehiculo.posicion == viaActual.fin){  
          siguienteInterseccion= viaActual.origen
        }
      }else{
        vehiculo.velocidadAct.magnitud=0
        return
      }
    }
    Simulacion.semaforos.filter(_.nodo==siguienteInterseccion).filter(_.via.nombre == viaActual.nombre)
      .foreach(x=> if (x.estado== "Rojo") {estaEnRojo=true} else if (x.estado== "Amarillo") {estaEnAmarillo=true})
    var distancia= Math.sqrt(Math.pow((vehiculo.posicion.x - siguienteInterseccion.x),2) + Math.pow((vehiculo.posicion.y - siguienteInterseccion.y),2))
    distancia = Math.abs(distancia)  
    vehiculo.velocidadAct.angulo= new Angulo(vehiculo.posicion.calcularAnguloA(siguienteInterseccion))
    if ((distancia<Simulacion.XSemaforoFrenar) && (estaEnRojo || estaEnAmarillo)){
      if ((distancia<Simulacion.XSemaforoAmarilloContinuar) && estaEnAmarillo) {
        vehiculo.mover(dt)
        return
      }
      vehiculo.frenar(dt)
      
    }else{
       vehiculo.mover(dt) 
    }
    
  }
  
  def llegoADestino():Boolean={
    vehiculo.posicion==interseccionDestino
  }
  
  def cercaDeInterseccion(interseccion: Interseccion, dt:Int): Boolean={
    var distancia= Math.sqrt(Math.pow((vehiculo.posicion.x - interseccion.x),2) + Math.pow((vehiculo.posicion.y - interseccion.y),2))
    distancia = Math.abs(distancia)
    (distancia <= vehiculo.velocidadMax.magnitud*dt)
  }
  
  def calcularDistanciaARecorrer(): Double={
     var dis:Double =0
     trayectoVias.foreach(x=> dis = dis + x.longitud)
     dis
  }
}
object Viaje{
  def apply(veh: Vehiculo): Viaje={
    val interseccionesAleatorias = Random.shuffle(Simulacion.intersecciones)
    val inO= interseccionesAleatorias(0)
    val inD= interseccionesAleatorias(1)
    
    new Viaje(inO, inD, veh)
    }
}