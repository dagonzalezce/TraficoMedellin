package com.unalMed.TraficoMedellin.vias
import com.unalMed.TraficoMedellin.geometria.Recta

class Via (val puntoInicio: Interseccion, val puntoFinal: Interseccion,val velMax: Double, val tipo: TipoVia, val sentido: Sentido, val numero: String, 
    val nombre: Option[String]=Some("")) extends Recta {
  
  private var _fotoDeteccion: Option[CamaraFotoDeteccion] = None
  
  def this(puntoInicio: Interseccion, puntoFinal: Interseccion, velMax: Double,  tipo: TipoVia,  sentido: Sentido,  numero: String, 
     nombre: String){
    this(puntoInicio, puntoFinal, velMax, tipo, sentido, numero, Some(nombre))
  }
  
  type t = Interseccion 
  val origen = puntoInicio
  val fin = puntoFinal
  
  def longitud = Math.sqrt(Math.pow((fin.x - origen.x),2) + Math.pow((fin.y - origen.y),2))

  override def toString = {s"${tipo.nombre}: ${nombre}"}
  
  def fotoDeteccion = _fotoDeteccion.get
  
  def fotoDeteccion_= (fotoMulta: Option[CamaraFotoDeteccion]) = _fotoDeteccion = fotoMulta
  
} 

