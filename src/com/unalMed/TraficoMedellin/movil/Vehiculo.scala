package com.unalMed.TraficoMedellin.movil

import scala.util.Random
import com.unalMed.TraficoMedellin.vias._
import com.unalMed.TraficoMedellin.simulacion._

abstract class Vehiculo (val interseccionOrigen: Interseccion, val interseccionDestino: Interseccion) 
                extends Movil with MovimientoUniforme{
  val placa: String
}

object Vehiculo{
  def apply(): Vehiculo = {
    val r = Random.nextDouble()
    val i1= Simulacion.proporcionCarros
    val i2= i1+Simulacion.proporcionMotos
    val i3= i2+Simulacion.proporcionBuses
    val i4= i3+Simulacion.proporcionCamiones
    val i5= i4+Simulacion.proporcionMotoTaxis
    
    val interseccionesAleatorias = Random.shuffle(Simulacion.intersecciones)
    val inO= interseccionesAleatorias(0)
    val inD= interseccionesAleatorias(1)
    
    var veh: Vehiculo= null
    if(r<=i1){
      veh = Carro(inO, inD)
    }else if(r<=i2){
      veh = Moto(inO, inD)
    }else if(r<=i3){
      veh = Bus(inO, inD)
    }else if(r<=i4){
      veh = Camion(inO, inD)
    }else {
      veh = MotoTaxi(inO, inD)
    }      
    veh 
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
  
  
