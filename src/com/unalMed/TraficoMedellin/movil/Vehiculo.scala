package com.unalMed.TraficoMedellin.movil

import scala.util.Random
import com.unalMed.TraficoMedellin.vias._
import com.unalMed.TraficoMedellin.simulacion._
import com.unalMed.TraficoMedellin.geometria._
import scalax.collection.mutable.{AdjacencyListGraph$EdgeImpl => Edge}
import scala.collection.mutable.Queue


abstract class Vehiculo ( val interseccionOrigen: Interseccion, val interseccionDestino: Interseccion, val ve: Velocidad ) 
                extends Movil(new Punto( interseccionOrigen.x, interseccionOrigen.y ),ve) with MovimientoUniforme{
  
  val placa: String  
  val trayectoVias = GrafoVia.caminoCorto(interseccionOrigen, interseccionDestino)
  
  private var siguienteInterseccion : Interseccion = interseccionOrigen
  private var viaActual : Via=trayectoVias.dequeue
  posicion= interseccionOrigen
  
  
  def mover(dt: Int){
    if (posicion == interseccionDestino) return
    
    if(posicion == viaActual.origen){
      siguienteInterseccion= viaActual.fin
      
    }else if(posicion == viaActual.fin){
      siguienteInterseccion= viaActual.origen
      }
    velocidad.angulo= new Angulo(posicion.calcularAnguloA(siguienteInterseccion))
    moverUniformemente(dt)
    if(cercaDeInterseccion(siguienteInterseccion, dt)){
      posicion= siguienteInterseccion.asInstanceOf[Punto]
      if(!trayectoVias.isEmpty) viaActual= trayectoVias.dequeue
      }    
  }
 
  
  def cercaDeInterseccion(interseccion: Interseccion, dt:Int): Boolean={
    var distancia= Math.sqrt(Math.pow((posicion.x - interseccion.x),2) + Math.pow((posicion.y - interseccion.y),2))
    distancia = Math.abs(distancia)
    (distancia <= velocidad.magnitud*dt)
  }

}


object Vehiculo{
  def apply(): Vehiculo = {
    val interseccionesAleatorias = Random.shuffle(Simulacion.intersecciones)
    val inO= interseccionesAleatorias(0)
    val inD= interseccionesAleatorias(1)
    
    val magnVel: Int= Simulacion.minVelocidad+Random.nextInt(Simulacion.maxVelocidad-Simulacion.minVelocidad)    
    val v: Velocidad = new Velocidad(Velocidad.aMetrosPorSegundo(magnVel), new Angulo(0)) 
    val r = Random.nextDouble()	   
    val i1= Simulacion.proporcionCarros	    
    val i2= i1+Simulacion.proporcionMotos	    
    val i3= i2+Simulacion.proporcionBuses	    
    val i4= i3+Simulacion.proporcionCamiones	    
    val i5= i4+Simulacion.proporcionMotoTaxis
    
    if(r<=i1){ new Carro(inO, inD, v)	     
    }else if(r<=i2){  new Moto(inO, inD, v)	     
    }else if(r<=i3){  new Bus(inO, inD, v)	     
    }else if(r<=i4){  new Camion(inO, inD, v)	      
    }else { new MotoTaxi(inO, inD, v)	     
    }      	         
  }
  
  
  def letrasAleatorias(n: Int)={
    var letras="ABCDEFGHIJKLMNOPQRSTUVWXYZ" 
    var la=""
    1 to n foreach {x => la+= letras.charAt(Random.nextInt(letras.length())).toString}
    la
  }
  
  def numerosAleatorios(n: Int)={
    var na=""
    1 to n foreach {x => na+= Random.nextInt(10).toString}
    na
  }
  
}


  
  
