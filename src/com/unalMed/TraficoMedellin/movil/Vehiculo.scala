package com.unalMed.TraficoMedellin.movil

import scala.util.Random
import com.unalMed.TraficoMedellin.simulacion._


abstract class Vehiculo {
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
    
    var veh: Vehiculo= null
    if(r<=i1){
      veh = Carro()
    }else if(r<=i2){
      veh = Moto()
    }else if(r<=i3){
      veh = Bus()
    }else if(r<=i4){
      veh = Camion()
    }else {
      veh = MotoTaxi()
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
  
  
