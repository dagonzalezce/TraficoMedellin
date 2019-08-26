package com.unalMed.TraficoMedellin.vias
import com.unalMed.TraficoMedellin.geometria.Recta

class Via (val puntoInicio: Interseccion, val puntoFinal: Interseccion,val velMax: Double, val tipo: TipoVia, val sentido: Sentido, val numero: String, 
    val nombre: Option[String], var _fotoMulta: Option[CamaraFotoDeteccion] = None) extends Recta {
    
  type t = Interseccion 
  val origen = puntoInicio
  val fin = puntoFinal
  
  def longitud = Math.sqrt(Math.pow((fin.x - origen.x),2) + Math.pow((fin.y - origen.y),2))

  override def toString = {s"${tipo.nombre}: ${nombre}"}
  
  def fotoMulta = _fotoMulta.get
  
  def fotoMulta_= (fotoMulta: Option[CamaraFotoDeteccion]) = _fotoMulta = fotoMulta
  
  object Via{
    
    def apply(origen: Interseccion, fin: Interseccion, velMax:Double, tipo:TipoVia, sentido:Sentido, numero:String, nombre:Option[String]): Via= {
      val velocidad = velMax match{
        case v if (velMax < 0) => 0.0
        case v if (velMax > 120) => 120.0
        case v => velMax
      }
      
      new Via (origen, fin,velocidad, tipo, sentido, numero, nombre)
      
    }
  }
  
}

/*class Via (val puntoInicio: Interseccion, val puntoFinal: Interseccion, val velmax: Double, val tipo: TipoVia, val sentido: Sentido,
    val numero: String, private val _nombre: String) extends Recta {
  
    type t = Interseccion
    val origen = puntoInicio
    val fin = puntoFinal
    
   
    
    
    def longitud = Math.sqrt(Math.pow((fin.x - origen.x),2) + Math.pow((fin.y - origen.y),2))
    
    override def toString = {s"${tipo.nombre}: ${_nombre}"}
  
  
}*/