package com.unalMed.TraficoMedellin.simulacion

case class Resultados (veliculos: RVehiculos, mallaVial: RMallaVial,tiempos: RTiempos, velocidades: RVelocidades, distancias: RDistancias)
case class RVehiculos (total: Int , carros: Int, motos: Int, buses: Int, camiones: Int, motoTaxis:Int)
case class RMallaVial (vias: Int, intersecciones:Int, viasUnSentido: Int, viasDobleSentido: Int, velocidadMinima: Double, velocidadMaxima: Double, longitudPromedio: Double, vehiculosEnInterseccion: RVehiculosEnInterseccion)
case class RVehiculosEnInterseccion (promedioOrigen: Double, promedioDestino: Double, sinOrigen: Double, sinDestino: Double)
case class RTiempos (simulacion: Double, realidad: Double)
case class RVelocidades (minima: Double, maxima: Double, prodemio: Double)
case class RDistancias (minima: Double, maxima: Double, prodemio: Double)

class ResultadosSimulacion(val resultados: Resultados) {
  
}