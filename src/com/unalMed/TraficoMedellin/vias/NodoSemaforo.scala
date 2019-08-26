package com.unalMed.TraficoMedellin.vias

import scala.collection.mutable.ArrayBuffer
import com.unalMed.TraficoMedellin.simulacion.Simulacion

class NodoSemaforo (val semaforos: ArrayBuffer[Semaforo]) {
   private var indiceSemaforoActual: Int=0
   semaforos(0).estado= "Verde"   
   private var _tiempoDesdeUltimoCambio:Int =0
   private var enVerde=true //El semáforo actual está en verde o en amarillo (false)
   
   def controlarFlujo(dt: Int):Unit={
     val semaforoActual= semaforos(indiceSemaforoActual)
     _tiempoDesdeUltimoCambio+= dt
     
     if(enVerde){
       if(semaforoActual.tiempoVerde <= _tiempoDesdeUltimoCambio){
         semaforoActual.estado= "Amarillo"
         _tiempoDesdeUltimoCambio=0
         enVerde=false
       }
     }else{
          if(semaforoActual.tiempoAmarillo <= _tiempoDesdeUltimoCambio){
            semaforoActual.estado= "Rojo"
            _tiempoDesdeUltimoCambio=0
            indiceSemaforoActual+=1
            if(indiceSemaforoActual >= semaforos.size) indiceSemaforoActual=0
            semaforos(indiceSemaforoActual).estado= "Verde"
            enVerde=true
          }
       }
   }
   
   def tiempoDesdeUltimoCambio: Int= _tiempoDesdeUltimoCambio
   
 
}