package com.unalMed.TraficoMedellin.simulacion

import com.unalMed.TraficoMedellin.movil.MotoTaxi
import com.unalMed.TraficoMedellin.movil.Carro
import com.unalMed.TraficoMedellin.movil.Camion
import com.unalMed.TraficoMedellin.movil.Bus
import com.unalMed.TraficoMedellin.movil.Moto
import scala.collection.mutable.ArrayBuffer
import com.unalMed.TraficoMedellin.geometria.Velocidad

case class Resultados (vehiculos: RVehiculos, mallaVial: RMallaVial,tiempos: RTiempos, velocidades: RVelocidades, distancias: RDistancias)
case class RVehiculos (total: Int , carros: Int, motos: Int, buses: Int, camiones: Int, motoTaxis:Int)
case class RMallaVial (vias: Int, intersecciones:Int, viasUnSentido: Int, viasDobleSentido: Int, velocidadMinima: Double, velocidadMaxima: Double, longitudPromedio: Double, vehiculosEnInterseccion: RVehiculosEnInterseccion)
case class RVehiculosEnInterseccion (promedioOrigen: Double, promedioDestino: Double, sinOrigen: Int, sinDestino: Int)
case class RTiempos (simulacion: Double, realidad: Double)
case class RVelocidades (minima: Double, maxima: Double, promedio: Double)
case class RDistancias (minima: Double, maxima: Double, promedio: Double)

case class ResultadosSimulacion(resultadosSimulacion: Resultados) 

object ResultadosSimulacion{
  def apply() :ResultadosSimulacion={
    var dis= RDistancias(distanciaMinima,
                          distanciaMaxima,
                          distanciaPromedio)
    
    var vel= RVelocidades(velocidadMinimaVehiculos,
                          velocidadMaximaVehiculos,
                          velocidadPromedioVehiculos)
    var tiempos= RTiempos(Simulacion.t,
                          tiempoReal)
    var vehEnInt= RVehiculosEnInterseccion(promedioOrigenVI,
                                            promedioDestinoVI,
                                            sinOrigenVI,
                                            sinDestinoVI)
    var mallaV= RMallaVial(totalVias,
                           totalIntersecciones,
                           totalViasUnSentido,
                           totalViasDobleSentido,
                           velocidadMinima,
                           velocidadMaxima,
                           longitudPromedioVias,
                           vehEnInt
                           )
    var veh= RVehiculos(totalVehiculos,
                        totalCarros,
                        totalMotos,
                        totalBuses,
                        totalCamiones,
                        totalMotoTaxis)
    var res= Resultados(veh, mallaV, tiempos, vel, dis)
    ResultadosSimulacion(res)
  }
  
  def distanciaMinima():Double={
    var distancias: ArrayBuffer[Double]= ArrayBuffer()
    Simulacion.vehiculos.foreach(x=> distancias+= x.distanciaARecorrer)
    distancias.reduce(Math.min(_,_))
    }
  def distanciaMaxima():Double={
    var distancias: ArrayBuffer[Double]= ArrayBuffer()
    Simulacion.vehiculos.foreach(x=> distancias+= x.distanciaARecorrer)
    distancias.reduce(Math.max(_,_))
    }
  def distanciaPromedio():Double={
    var distancias: ArrayBuffer[Double]= ArrayBuffer()
    Simulacion.vehiculos.foreach(x=> distancias+= x.distanciaARecorrer)
    distancias.reduce(_+_)/Simulacion.vehiculos.size
    }
  
  def velocidadMinimaVehiculos():Double={
    var velocidades: ArrayBuffer[Double]= ArrayBuffer()
    Simulacion.vehiculos.foreach(x=> velocidades+= Velocidad.aKilometrosPorHora(x.velocidad.magnitud))
    velocidades.reduce(Math.min(_,_))
  }
  def velocidadMaximaVehiculos():Double={
    var velocidades: ArrayBuffer[Double]= ArrayBuffer()
    Simulacion.vehiculos.foreach(x=> velocidades+= Velocidad.aKilometrosPorHora(x.velocidad.magnitud))
    velocidades.reduce(Math.max(_,_))
  }
  def velocidadPromedioVehiculos():Double={
    var velocidades: ArrayBuffer[Double]= ArrayBuffer()
    Simulacion.vehiculos.foreach(x=> velocidades+= Velocidad.aKilometrosPorHora(x.velocidad.magnitud))
    velocidades.reduce(_+_)/Simulacion.vehiculos.size
  }
  
  def tiempoReal():Double={
    (System.currentTimeMillis()-Simulacion.tiempoInicio)/1000
  }
  
  def promedioOrigenVI(): Double={
    var numVeh:Double=0
    Simulacion.intersecciones.foreach(x =>Simulacion.vehiculos.foreach(y => if(y.interseccionOrigen==x) numVeh+=1))
    numVeh/Simulacion.intersecciones.size
    }
  def promedioDestinoVI(): Double={
    var numVeh:Double=0
    Simulacion.intersecciones.foreach(x =>Simulacion.vehiculos.foreach(y => if(y.interseccionDestino==x) numVeh+=1))
    numVeh/Simulacion.intersecciones.size
  }
  def sinOrigenVI(): Int={
    var numInter=0
    Simulacion.intersecciones.foreach(x=> {
      var sinOr=true
      Simulacion.vehiculos.foreach(y => if(y.interseccionOrigen==x) sinOr=false)
      if (sinOr) numInter+=1
    })
    numInter
  }
  def sinDestinoVI(): Int={
    var numInter=0
    Simulacion.intersecciones.foreach(x=> {
      var sinDe=true
      Simulacion.vehiculos.foreach(y => if(y.interseccionDestino==x) sinDe=false)
      if (sinDe) numInter+=1
    })
    numInter
  }
  
  def totalVias(): Int= Simulacion.vias.size
  def totalIntersecciones(): Int= Simulacion.intersecciones.size
  def totalViasUnSentido(): Int= Simulacion.vias.filter(_.sentido.nombre == "unaVia").size
  def totalViasDobleSentido(): Int= Simulacion.vias.filter(_.sentido.nombre == "dobleVia").size
  def velocidadMinima(): Double= {
    var velocidades: ArrayBuffer[Double] = ArrayBuffer()
    Simulacion.vias.foreach(x=> velocidades+=x.velMax)
    velocidades.reduce(Math.min(_,_))
  }
  def velocidadMaxima(): Double= {
    var velocidades: ArrayBuffer[Double] = ArrayBuffer()
    Simulacion.vias.foreach(x=> velocidades+=x.velMax)
    velocidades.reduce(Math.max(_,_))
  }
  def longitudPromedioVias(): Double={
    var longitudes: ArrayBuffer[Double] = ArrayBuffer()
    Simulacion.vias.foreach(x=> longitudes+= x.longitud)
    longitudes.reduce(_+_)/Simulacion.vias.size
  }
  
  def totalVehiculos(): Int= Simulacion.vehiculos.size
  def totalCarros(): Int= Simulacion.vehiculos.filter(_.isInstanceOf[Carro]).size
  def totalMotos(): Int= Simulacion.vehiculos.filter(_.isInstanceOf[Moto]).size
  def totalBuses(): Int= Simulacion.vehiculos.filter(_.isInstanceOf[Bus]).size
  def totalCamiones(): Int= Simulacion.vehiculos.filter(_.isInstanceOf[Camion]).size
  def totalMotoTaxis(): Int= Simulacion.vehiculos.filter(_.isInstanceOf[MotoTaxi]).size
  
}
