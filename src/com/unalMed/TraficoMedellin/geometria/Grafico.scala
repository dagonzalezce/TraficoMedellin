package com.unalMed.TraficoMedellin.geometria
import com.unalMed.TraficoMedellin.vias.Via
import com.unalMed.TraficoMedellin.movil._
import org.jfree.data.xy.XYSeriesCollection
import org.jfree.data.xy.XYSeries
import org.jfree.chart._
import java.awt.event._
import org.jfree.chart.renderer._
import java.awt.Color._
import org.jfree.chart.renderer.xy._
import java.awt.BasicStroke
import java.awt.geom.Line2D
import org.jfree.chart.annotations.XYTextAnnotation
import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import com.unalMed.TraficoMedellin.simulacion.Simulacion


/*
 * Clase que maneja la interfaz grafica de la aplicacion provee los metodos 
 * graficasVias y graficarVehiculos
 * graficarVias: se llama antes de iniciar dt
 * graficarVehiculos: se llama antes de 
 * autor: Juan
 */
object Grafico {
  private var _numeroVias : Int = 0 //Variable para saber cuando acaban las vias y inicial los vehiculos en el dataset 
  private var _dataset: XYSeriesCollection = new XYSeriesCollection() //data set
  private var _actualKey: Int = 0
  //private var _mapColores: scala.collection.mutable.Map[com.unalMed.TraficoMedellin.vias.Interseccion, java.awt.Color] = scala.collection.mutable.Map()
  private var _mapColores: scala.collection.mutable.Map[String, java.awt.Color] = scala.collection.mutable.Map()
  /*
   * Grafica vias y label 
   */
  var sP = ChartFactory.createScatterPlot( // ScatterPlot
    "", // Titulo
    "", // Axis x
    "", // Axis y
    _dataset, //XYDataset
    org.jfree.chart.plot.PlotOrientation.VERTICAL, // Orientacion de la grafica
    false, //legends
    false, //tooltips
    false  //url
  )
  
  sP.getXYPlot.setOutlineVisible(false) // Quita el borde de la grafica  
  sP.getPlot.setBackgroundPaint( java.awt.Color.WHITE ) //Pinta el fondo de blanco
  sP.getXYPlot.getDomainAxis.setVisible(false) // Quita el axis x
  sP.getXYPlot.getRangeAxis.setVisible(false) // Quita el axis y 

  //Renderer, el casteo es necesario!
  var r = sP.getXYPlot.getRenderer.asInstanceOf[XYLineAndShapeRenderer] 
  
  
  val frame = new ChartFrame(
    "Transito Medellín", //Titulo
    sP,
    false // Scroll panel
  )
  
  /*
  Simulacion.intersecciones.distinct.foreach( inter => _listaDestinos.+=( inter ) )
  _listaDestinos.foreach( inter => listaColores.append(java.awt.Color.getHSBColor(Random.nextFloat()*255,
          Random.nextFloat()*255,
          Random.nextFloat()*255) ) )
  */
  
  val s = Simulacion
  frame.addKeyListener( new KeyListener{
                           override def keyPressed( e : KeyEvent ): Unit = {
                             if( e.getKeyCode ==  KeyEvent.VK_F5 ){
                            	 s.reiniciar
                            	 _actualKey = _numeroVias
                             }
                             if( e.getKeyCode ==  KeyEvent.VK_F6 ){
                               println("Pausar simulacion")
                               if ( s.estado == true ){
                                 s.pausar
                               }
                               else{
                                 s.continuar
                               }
                             }
                           }
                           override def keyTyped( e : KeyEvent ): Unit = {
                             
                           }
                           override def keyReleased( e : KeyEvent ): Unit = {
                             
                           }
                          }
                        )
  
  frame.setAlwaysOnTop(true) // la ventana siempre arriba de aplicaciones
  frame.pack() // Acomoda la ventana a la grafica
  frame.setVisible(true) // Siempre visible
  frame.setResizable(true) // Que no se le pueda cambiar el tamano
  frame.getBounds //Rectangle
  frame.setSize(1400, 800) // ponerle tamaño a la ventana 
  

                        
  
  private def _limpiarVehiculos: Unit = {
     val empiezanCarros = _numeroVias 
     while( _dataset.getSeriesCount > empiezanCarros  ){
    	 _dataset.removeSeries( empiezanCarros )
      }
  }
  
  def graficarVias( vias : Array[Via] ): Unit = {
    var _viaActual = 0
    _dataset.removeAllSeries()
    _numeroVias = vias.length
    //Grafica las vias
    vias.foreach( via =>{ var s = new XYSeries(s"Via${_viaActual}")
                          s.add(via.origen.x, via.origen.y)
                          s.add(via.fin.x, via.fin.y)
                          _dataset.addSeries(s)
                          r.setSeriesPaint(_viaActual, java.awt.Color.GRAY ) //Color de la via 
                          r.setSeriesStroke(_viaActual , new BasicStroke( 4.0f )) //Grosor de la via
                          r.getSeriesStroke(_viaActual).createStrokedShape( new Line2D.Float()) // Forma de linea
                          r.setSeriesShapesVisible(_viaActual, false) //Quita los cuadrados en las esquinas
                          r.setSeriesLinesVisible(_viaActual, true) // Mostrar lineas
                          
                          _viaActual=_viaActual + 1 // Aumentar el numero de la via
                          } )
    //Agrega mapa y color por interseccion
    (vias.map(_.fin)++(vias.map(_.origen))).distinct.foreach( interseccion => { 
      _mapColores.+=( interseccion.nombre.get -> java.awt.Color.getHSBColor(Random.nextFloat()*100f+100f,
                                                                 Random.nextFloat()*100f+100f,
                                                                 Random.nextFloat()*100f+100f) )
                                                                 
    		  println("ward")
      //Agrega anotaciones a la via
      var _annotation = new XYTextAnnotation( interseccion.nombre.getOrElse(""), interseccion.x, interseccion.y  ) 
      _annotation.setPaint( _mapColores.get(interseccion.nombre.get).getOrElse(java.awt.Color.BLACK) )
      sP.getXYPlot.addAnnotation( _annotation )
      //sP.getXYPlot.getAnnotations.forEach( annotation => annotation.asInstanceOf[XYTextAnnotation].setPaint( java.awt.Color.getHSBColor(Random.nextFloat()*255,
      //                                                           Random.nextFloat()*255,
      //                                                           Random.nextFloat()*255) ) )
      //sP.getXYPlot.getAnnotations.get(_viaActual).asInstanceOf[XYTextAnnotation].setPaint(_mapColores.get(interseccion).getOrElse(java.awt.Color.BLACK)) 
     })
    _actualKey = _numeroVias 
    println( _mapColores.mkString(",") )
    return
  }
  
  
  def graficarVehiculos( vehiculos: Array[com.unalMed.TraficoMedellin.movil.Vehiculo] ): Unit = {
    _limpiarVehiculos
    //var listaDestinos = vehiculos.map(_.interseccionDestino)
    
    vehiculos.foreach( vehiculo => {
  	  var s = new XYSeries(_actualKey)
  	  s.add(vehiculo.posicion.x, vehiculo.posicion.y)
  	  _dataset.addSeries(s)
  	  r.setSeriesPaint( _actualKey, _mapColores.get(vehiculo.interseccionDestino.nombre.get).getOrElse(java.awt.Color.BLACK) )
  	  vehiculo match { 
  	    case vehiculo: Carro => r.setSeriesShape(_actualKey, org.jfree.util.ShapeUtilities.createDiagonalCross(7, 1f)) // primer arg es tamano 2 es grosor
  	    case vehiculo: Camion => r.setSeriesShape(_actualKey, org.jfree.util.ShapeUtilities.createDiamond(7)) // primer argumento es el tamano
  	    case vehiculo: Bus => r.setSeriesShape(_actualKey, org.jfree.util.ShapeUtilities.createDownTriangle(7)) // primer argumento es el tamano
  	    case vehiculo: Moto => r.setSeriesShape(_actualKey, org.jfree.util.ShapeUtilities.createUpTriangle(7))
  	    case vehiculo: MotoTaxi => r.setSeriesShape(_actualKey, org.jfree.util.ShapeUtilities.createUpTriangle(7))
  	  }
  	  _actualKey = _actualKey + 1
    })
    //println(_actualKey)
  }
  
}
