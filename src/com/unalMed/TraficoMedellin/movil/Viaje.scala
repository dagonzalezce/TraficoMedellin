package com.unalMed.TraficoMedellin.movil

import com.unalMed.TraficoMedellin.vias.Interseccion
import com.unalMed.TraficoMedellin.geometria.GrafoVia
import com.unalMed.TraficoMedellin.geometria.Punto
import com.unalMed.TraficoMedellin.vias.Via
import com.unalMed.TraficoMedellin.simulacion.Simulacion
import com.unalMed.TraficoMedellin.geometria.Angulo
import scala.util.Random

class Viaje (val interseccionOrigen: Interseccion, val interseccionDestino: Interseccion, val vehiculo: Vehiculo){
  
  vehiculo.posicion= interseccionOrigen
  val trayectoVias = GrafoVia.caminoCorto(interseccionOrigen, interseccionDestino)
  val distanciaARecorrer= calcularDistanciaARecorrer
  
  private var siguienteInterseccion : Interseccion = interseccionOrigen
  private var viaActual : Via=trayectoVias.dequeue
  if(vehiculo.posicion == viaActual.origen){
          siguienteInterseccion= viaActual.fin
        }else if(vehiculo.posicion == viaActual.fin){
          siguienteInterseccion= viaActual.origen
        }
 
  
  
  def avanzar(dt: Int){
    if(cercaDeInterseccion(siguienteInterseccion, dt)){
      vehiculo.posicion= siguienteInterseccion.asInstanceOf[Punto]
      if(llegoADestino) return
      var estaEnVerde=false
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
        return
      }
    }
    vehiculo.velocidadMax.angulo= new Angulo(vehiculo.posicion.calcularAnguloA(siguienteInterseccion))
    vehiculo.mover(dt)
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