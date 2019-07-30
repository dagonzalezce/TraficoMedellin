// Idea para el recorrido aleatorio inicial de los vehiculos
// Esta malo, pero no sé como organizarlo
// si alguno tiene idea, puede modificar la clase.


package com.unalMed.TraficoMedellin.geometria


import com.unalMed.TraficoMedellin.vias.Interseccion

import com.unalMed.TraficoMedellin.simulacion.Simulacion

import scalax.collection.edge.WLUnDiEdge
import scalax.collection.mutable.Graph

class Trayecto (val origen: Interseccion, val destino: Interseccion, val camino: Option[GrafoVia.grafo.Path]) {
  
}

object Trayecto {
  
  def apply(): Trayecto = {
    
    val a = scala.util.Random.nextInt(Simulacion.listaInterseccion.length) // tendría que crear una lista de intersecciones en Simulacion
    var b = scala.util.Random.nextInt(Simulacion.listaInterseccion.length)  // // tendría que crear una lista de intersecciones en Simulacion
    
    while ( a == b) {b = scala.util.Random.nextInt(Simulacion.listaInterseccion.length)}  // tendría que crear una lista de intersecciones en Simulacion
    val origen = Simulacion.listaInterseccion(a)
    val destino = Simulacion.listaInterseccion(b)
    val camino = GrafoVia.caminoCorto(origen, destino)
        new Trayecto(origen, destino, camino)
    
    
    
  }
  
}
