package com.unalMed.TraficoMedellin.simulacion

import net.liftweb.json._
import java.io._
import scala.io.Source
import scala.reflect.ManifestFactory.classType

object ArchivosJson {
  val ruta = "C:\\Users\\admin\\eclipse-workspace\\TraficoMedellin\\src\\"
  val archivo = "parametros.json"
  implicit val formats = DefaultFormats
  
  val parametros : Parametro = cargarDatos(ruta+archivo)
    
  case class Parametro(dt: Int, tRefresh: Int, vehiculos: PVehiculo, velocidad: PVelocidad, proporciones: PProporciones)
  case class PVehiculo(minimo: Int, maximo: Int)
  case class PVelocidad(minimo: Int, maximo: Int)
  case class PProporciones(carros: Double, motos: Double, buses: Double, camiones: Double, motoTaxis: Double)
   
  def cargarDatos(archivo: String) : Parametro = {
    val cadena = Source.fromFile(archivo).getLines.mkString
    val json = parse(cadena)
    
    val parametrosSimulacion = (json \\ "pametrosSimulacion" ).children
    
    parametrosSimulacion (0).extract[Parametro]
    }
}
