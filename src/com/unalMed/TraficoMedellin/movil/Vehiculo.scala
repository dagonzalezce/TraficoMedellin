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
    val interseccionesAleatorias = Random.shuffle(Simulacion.intersecciones)
    val inO= interseccionesAleatorias(0)
    val inD= interseccionesAleatorias(1)
    
    // Por cambiar
    var v: Velocidad = new Velocidad( 20, new Angulo(Random.nextInt(360)) ) // Valor mientras mayra implement ael grafo y pensamos velocidad
    
    _generarRandomProporciones( Simulacion.proporcionCarros,
        Simulacion.proporcionCamiones,
        Simulacion.proporcionBuses,
        Simulacion.proporcionMotos,
        Simulacion.proporcionMotoTaxis) match {
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
  
  //Devuelve un entero de 1 a 5 dependiendo de las proporciones
  private def _generarRandomProporciones( pCarro : Double, pCamion: Double, pBus: Double, pMoto: Double, pMotoTaxi: Double  ): Int = {
    val r : Double = Random.nextDouble()
    if( r <= pCarro ){ //Caso carro
      return 0
    }else if(r<=pCarro + pCamion ){ //Caso Camion
      return 1
    }else if(r<= pCarro + pCamion + pBus){ //Caso Bus
      return 2
    }else if(r<= pCarro + pCamion+ pBus + pMoto){ //Caso Moto
      return 3
    }else {  //Caso MotoTaxi
      return 4
    }      
  }
}


  
  
