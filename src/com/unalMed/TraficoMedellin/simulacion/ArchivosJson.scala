package com.unalMed.TraficoMedellin.simulacion

import net.liftweb.json._
import java.io._
import scala.io.Source
import scala.reflect.ManifestFactory.classType
import net.liftweb.json.Serialization.write

object ArchivosJson {
  val ruta = "C:\\Users\\SERGIO\\git\\TraficoMedellin\\src\\"
  val archivo = "parametros.json"
  val archivo2 = "resultados.json" //archivo resultados simulacion
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
  
  
  //recibe la instancia de resultados enviada por simulacion
  def escribirArchivo( resultados: ResultadosSimulacion) {
    val pw = new PrintWriter(new File(ruta+archivo2))
    pw.write(write(resultados))
    pw.close
    print("archivo escrito")
  }
  
}
