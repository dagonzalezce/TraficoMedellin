package com.unalMed.TraficoMedellin.geometria

import scalax.collection.mutable.Graph
import scalax.collection.edge.WLDiEdge
import scala.collection.mutable.ArrayBuffer
import com.unalMed.TraficoMedellin.vias.{Via, Interseccion, Sentido}
import scalax.collection.mutable.{AdjacencyListGraph$EdgeImpl => Edge}
import scala.collection.mutable.Queue 

object GrafoVia {
  
  val grafo = Graph[Interseccion, WLDiEdge]()
  
  def construir ( Vias : ArrayBuffer[Via] ) : Unit = {
    Vias.foreach ( z => 
      
      if (z.sentido == Sentido.unaVia) 
        
          { grafo.add (WLDiEdge (z.origen, z.fin) (z.longitud, z)) }
      
      else { 
        
            grafo.add ( WLDiEdge (z.origen,z.fin) (z.longitud, z))
            
            grafo.add( WLDiEdge (z.fin, z.origen)(z.longitud, z))
            
            }
      )
      
    }
  
  def caminoCorto ( nodoOrigen: Interseccion, nodoDestino: Interseccion) : Queue[Via] = {
    
    val trayectoVias = Queue[Via]()
    
    val camino = grafo.get(nodoOrigen).shortestPathTo(grafo.get(nodoDestino))
    
    camino.get.filter(_.isInstanceOf[Edge]).foreach(x=>trayectoVias += (x.asInstanceOf[Edge].label.asInstanceOf[Via]))
    
    trayectoVias
  }
  
    
    
  
  
  
}
