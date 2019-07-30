package com.unalMed.TraficoMedellin.vias
import com.unalMed.TraficoMedellin.geometria.Recta

class Via (val puntoInicio: Interseccion, val puntoFinal: Interseccion, val velmax: Double, val tipo: TipoVia, val sentido: Sentido,
    val numero: String, val nombre: String) extends Recta {
  
    type t = Interseccion
    val origen = puntoInicio
    val fin = puntoFinal
    
    def longitud = Math.sqrt(Math.pow((fin.x - origen.x),2) + Math.pow((fin.y - origen.y),2))
    
    override def toString = {s"${tipo.nombre}: $nombre"}
  
  
}