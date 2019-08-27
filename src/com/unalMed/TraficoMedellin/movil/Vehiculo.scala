package com.unalMed.TraficoMedellin.movil

import scala.util.Random
import com.unalMed.TraficoMedellin.vias._
import com.unalMed.TraficoMedellin.simulacion._
import com.unalMed.TraficoMedellin.geometria._
import scalax.collection.mutable.{AdjacencyListGraph$EdgeImpl => Edge}
import scala.collection.mutable.Queue


abstract case class Vehiculo (val ve: Velocidad, val acel: Aceleracion) 
                extends Movil(ve,acel) with MovimientoUniforme{
  
  var placa: String
  
  def mover(dt: Int){
    moverUniformemente(dt)
  } 
}


object Vehiculo{
  def apply(): Vehiculo = {
       
    val magnVel: Int= Simulacion.minVelocidad+Random.nextInt(Simulacion.maxVelocidad-Simulacion.minVelocidad)
    val magnAcel: Int= Simulacion.minAceleracion+Random.nextInt(Simulacion.maxAceleracion-Simulacion.minAceleracion)
    val v= new Velocidad(Velocidad.aMetrosPorSegundo(magnVel), new Angulo(0)) 
    val a = new Aceleracion(magnAcel)
    val r = Random.nextDouble()	   
    val i1= Simulacion.proporcionCarros	    
    val i2= i1+Simulacion.proporcionMotos	    
    val i3= i2+Simulacion.proporcionBuses	    
    val i4= i3+Simulacion.proporcionCamiones	    
    val i5= i4+Simulacion.proporcionMotoTaxis
    
    if(r<=i1){ new Carro(v,a)	     
    }else if(r<=i2){  new Moto(v,a)	     
    }else if(r<=i3){  new Bus(v,a)	     
    }else if(r<=i4){  new Camion(v,a)	      
    }else { new MotoTaxi(v,a)	     
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


  
  
