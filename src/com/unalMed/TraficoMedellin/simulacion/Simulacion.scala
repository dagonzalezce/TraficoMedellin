package com.unalMed.TraficoMedellin.simulacion


import com.unalMed.TraficoMedellin.vias._
import com.unalMed.TraficoMedellin.movil._
import com.unalMed.TraficoMedellin.geometria._
import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import com.unalMed.TraficoMedellin.movil.Comparendo
import com.unalMed.TraficoMedellin.vias.CamaraFotoDeteccion

import com.unalMed.TraficoMedellin.geometria.Grafico

object Simulacion extends Runnable{
  
  val dt: Int= ArchivosJson.parametros.dt
  val tRefresh: Int= ArchivosJson.parametros.tRefresh
  val minVehiculos: Int= ArchivosJson.parametros.vehiculos.minimo
  val maxVehiculos: Int= ArchivosJson.parametros.vehiculos.maximo
  val minVelocidad: Int= ArchivosJson.parametros.velocidad.minimo
  val maxVelocidad: Int= ArchivosJson.parametros.velocidad.maximo
  val minAceleracion: Int= ArchivosJson.parametros.aceleracion.minimo
  val maxAceleracion: Int= ArchivosJson.parametros.aceleracion.maximo
  val XSemaforoAmarilloContinuar: Int= ArchivosJson.parametros.distanciasFrenadoVehiculos.XSemaforoAmarilloContinuar
  val XSemaforoFrenar: Int= ArchivosJson.parametros.distanciasFrenadoVehiculos.XSemaforoFrenar
  
  val proporcionCarros: Double= ArchivosJson.parametros.proporciones.carros
  val proporcionMotos: Double= ArchivosJson.parametros.proporciones.motos
  val proporcionBuses: Double= ArchivosJson.parametros.proporciones.buses
  val proporcionCamiones: Double= ArchivosJson.parametros.proporciones.camiones
  val proporcionMotoTaxis: Double= ArchivosJson.parametros.proporciones.motoTaxis
  
  val minTiempoVerde: Int= ArchivosJson.parametros.semaforos.minTiempoVerde
  val maxTiempoVerde: Int= ArchivosJson.parametros.semaforos.maxTiempoVerde
  val tiempoAmarillo: Int= ArchivosJson.parametros.semaforos.tiempoAmarillo


  val intersecciones = Conexion.getIntersecciones()
  val vias = Conexion.getVias()
  val listaComparendos:ArrayBuffer[Comparendo]=ArrayBuffer[Comparendo]()  // aun no he creado fotomultas :(

  val semaforos = ArrayBuffer[Semaforo]()
  vias.foreach(x=>{
          val tiempoVerde= minTiempoVerde+Random.nextInt(maxTiempoVerde-minTiempoVerde)
          print(tiempoVerde)
          semaforos+= new Semaforo(tiempoVerde,tiempoAmarillo,x,x.puntoFinal)
          if(x.sentido.nombre == "dobleVia" ){
            val tiempoVerde2= minTiempoVerde+Random.nextInt(maxTiempoVerde-minTiempoVerde)
            semaforos+= new Semaforo(tiempoVerde2,tiempoAmarillo,x,x.puntoInicio)  
          }
      }
    )
   val nodosSemaforos= ArrayBuffer[NodoSemaforo]()
   intersecciones.foreach(i=>{
       nodosSemaforos+= new NodoSemaforo(semaforos.filter(_.nodo == i))
   })
   
  
 
  //val vehiculos = crearVehiculos()
  var t=0
  var tiempoInicio= 0L
  
  GrafoVia.construir(vias)
  Grafico.graficarVias(vias.toArray)
 
   var vehiculos: ArrayBuffer[Vehiculo] = ArrayBuffer[Vehiculo]()
   var viajes = ArrayBuffer[Viaje]()
  
    def run(){
    estado = false
    
    while (true) {
      
      if( estado ){
        viajes.foreach(_.avanzar(dt))
        nodosSemaforos.foreach(_.controlarFlujo(dt))
        
        t += dt
        Grafico.graficarVehiculos(viajes)
        Thread.sleep(tRefresh)
        
        var llegaronTodos=true
        viajes.foreach(x=> if(!x.llegoADestino()) llegaronTodos=false)
        if(llegaronTodos && !vehiculos.isEmpty) ArchivosJson.escribirArchivo(ResultadosSimulacion())
      }
      print("") //No me borres, por que rompe pausar, dios sabe por que, preguntarle pls
<<<<<<< HEAD
     }
=======
      
    }
>>>>>>> 97ef839906bbb2a0b2edd8b1f0e80273d6ef146a
    
  }

  // true es corriendo false es en pausa
  var estado : Boolean = false
  
  def reiniciar{
    t=0
    tiempoInicio= System.currentTimeMillis()
    vehiculos = ArrayBuffer[Vehiculo]()
    viajes.clear()
    vehiculos = crearVehiculos
    continuar
  }
  
  def iniciarDeNeo4J{
    pausar
    t=0
    tiempoInicio= System.currentTimeMillis()
    vehiculos = ArrayBuffer[Vehiculo]()
    viajes.clear()
    vehiculos = Conexion.getVehiculos()
    viajes = Conexion.getViajes()
    print(vehiculos)
    continuar    
  }
  
  def pausar: Unit = {
    estado = false
  }
  
  def continuar: Unit = {
    estado = true
  }
  
  
  def crearVehiculos() : ArrayBuffer[Vehiculo] = {
    
    val size= minVehiculos+Random.nextInt(maxVehiculos-minVehiculos)
    val array= new ArrayBuffer[Vehiculo]()
    for(i <- 0 to size-1) {
      var veh= Vehiculo()
      array+= veh
      viajes+= Viaje(veh)
    }
    array
  }
  
  

  
  
    
    }