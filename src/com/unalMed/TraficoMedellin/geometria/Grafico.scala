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
  
  
  frame.addKeyListener( new KeyListener{
                           override def keyPressed( e : KeyEvent ): Unit = {
                             if( e.getKeyCode ==  KeyEvent.VK_F5 )
                               println("Presionado F5")
                            if( e.getKeyCode ==  KeyEvent.VK_F6 )
                               println("Presionado F6")
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
  frame.setResizable(false) // Que no se le pueda cambiar el tamano
  frame.getBounds //Rectangle
  

  
  
  def graficarVias( vias : Array[Via] ): Unit = {
    var _viaActual = 0
    
    _dataset.removeAllSeries()
    _numeroVias = vias.length
    
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
    //Agregar el nombre de las intersecciones
    (vias.map(_.puntoInicio)++vias.map(_.puntoFinal)).distinct.foreach( inter =>
        sP.getXYPlot.addAnnotation( new XYTextAnnotation( inter.nombre.getOrElse(""), inter.x, inter.y  )))
    return
  }
  
  /*
  def graficarVehiculos( vehiculos: Array[com.unalMed.TraficoMedellin.movil.Vehiculo] ): Unit = {
    vehiculos.foreach( vehiculo => this._vehiculos.add( vehiculo. , y) )
  }
  */
}