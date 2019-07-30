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
/*
 * Clase que maneja la interfaz grafica de la aplicacion provee los metodos 
 * graficasVias y graficarVehiculos
 * graficarVias: se llama antes de iniciar dt
 * graficarVehiculos: se llama antes de 
 * autor: Juan
 */
object Grafico {
  private var _vias : XYSeries = new XYSeries(0) // 0 es la key identificadora de las serie
  private var _vehiculos : XYSeries = new XYSeries(1) // 1 "" 
  private var _dataset: XYSeriesCollection = new XYSeriesCollection()
  /*
   * Grafica vias y label 
   */
  var scatterPlot = ChartFactory.createScatterPlot( // ScatterPlot
    "", // Titulo
    "", // Axis x
    "", // Axis y
    _dataset, //XYDataset
    org.jfree.chart.plot.PlotOrientation.HORIZONTAL,
    false, //legends
    false, //tooltips
    false  //url
  )
  
  scatterPlot.getXYPlot.setOutlineVisible(false) // Quita el borde de la grafica  
  scatterPlot.getPlot.setBackgroundPaint( java.awt.Color.WHITE ) //Pinta el fondo de blanco
  scatterPlot.getXYPlot.getDomainAxis.setVisible(false) // Quita el axis x
  scatterPlot.getXYPlot.getRangeAxis.setVisible(false) // Quita el axis y 
  
  val frame = new ChartFrame(
    "Transito Medellín", //Titulo
    scatterPlot,
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
  
  frame.setAlwaysOnTop(true) // la ventana siempre visible
  frame.pack() // Acomoda la ventana a la grafica
  frame.setVisible(true) // Siempre visible
  frame.setResizable(false) // Que no se le pueda cambiar el tamano
  frame.getBounds //Rectangle
  
  
  var renderer =  scatterPlot.getXYPlot.getRenderer.asInstanceOf[XYLineAndShapeRenderer] //Casteo a renderer necesario!
  
  
  
  def graficarVias( vias : Array[Via] ): Unit = {
    vias.foreach( via => this._vias.add( via.origen.x.toDouble, via.origen.y.toDouble ) )
    vias.foreach( via => this._vias.add( via.fin.x.toDouble, via.fin.y.toDouble ))
    _dataset.addSeries(_vias)
    return
  }
  
  /*
  def graficarVehiculos( vehiculos: Array[com.unalMed.TraficoMedellin.movil.Vehiculo] ): Unit = {
    vehiculos.foreach( vehiculo => this._vehiculos.add( vehiculo. , y) )
  }
  */
}