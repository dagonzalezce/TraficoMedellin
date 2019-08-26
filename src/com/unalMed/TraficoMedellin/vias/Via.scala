package com.unalMed.TraficoMedellin.vias
import com.unalMed.TraficoMedellin.geometria.Recta

class Via (val puntoInicio: Interseccion, val puntoFinal: Interseccion, val velmax: Double, val tipo: TipoVia, val sentido: Sentido,
    val numero: String, private val _nombre: String) extends Recta {
  
    type t = Interseccion
    val origen = puntoInicio
    val fin = puntoFinal
    
    
   
    
    def nombre= _nombre
    
    def longitud = Math.sqrt(Math.pow((fin.x - origen.x),2) + Math.pow((fin.y - origen.y),2))
    
    override def toString = {s"${tipo.nombre}: ${_nombre}"}
  
  
}