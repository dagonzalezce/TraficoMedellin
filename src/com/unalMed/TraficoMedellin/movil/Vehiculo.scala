package com.unalMed.TraficoMedellin.movil

import scala.util.Random
import com.unalMed.TraficoMedellin.vias._
import com.unalMed.TraficoMedellin.simulacion._
import com.unalMed.TraficoMedellin.geometria._

abstract class Vehiculo ( val interseccionOrigen: Interseccion, val interseccionDestino: Interseccion, val ve: Velocidad ) 
                extends Movil(new Punto( interseccionOrigen.x, interseccionOrigen.y ),ve) with MovimientoUniforme{
  
  val placa: String  
}


object Vehiculo{
  def apply(): Vehiculo = {
    val i1= Simulacion.proporcionCarros
    val i2= i1+Simulacion.proporcionMotos
    val i3= i2+Simulacion.proporcionBuses
    val i4= i3+Simulacion.proporcionCamiones
    val i5= i4+Simulacion.proporcionMotoTaxis
    
    val interseccionesAleatorias = Random.shuffle(Simulacion.intersecciones)
    val inO= interseccionesAleatorias(0)
    val inD= interseccionesAleatorias(1)
    
    // Por cambiar
    var v: Velocidad = new Velocidad( 1, new Angulo(90) ) // Valor mientras mayra implement ael grafo y pensamos velocidad
    
    
    
    var veh: Vehiculo= null
    Random.nextInt(5) match {
      case 0 => new Carro( inO, inD, v )
      case 1 => new Camion( inO, inD, v )
      case 2 => new Bus( inO, inD, v )
      case 3 => new Moto( inO, inD, v )
      case 4 => new MotoTaxi( inO, inD, v ) 
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


  
  
