package com.unalMed.TraficoMedellin.simulacion

import org.neo4j.driver.v1._
import scala.collection.mutable.ArrayBuffer
import com.unalMed.TraficoMedellin.vias.Via
import com.unalMed.TraficoMedellin.vias.Interseccion
import com.unalMed.TraficoMedellin.movil.Vehiculo
import com.unalMed.TraficoMedellin.movil.Bus
import com.unalMed.TraficoMedellin.movil.Camion
import com.unalMed.TraficoMedellin.movil.Moto
import com.unalMed.TraficoMedellin.movil.Carro
import com.unalMed.TraficoMedellin.movil.MotoTaxi
import com.unalMed.TraficoMedellin.geometria.Angulo
import com.unalMed.TraficoMedellin.geometria.Velocidad
import com.unalMed.TraficoMedellin.geometria.Aceleracion
import com.unalMed.TraficoMedellin.geometria.Punto
import com.unalMed.TraficoMedellin.movil.Viaje
import com.unalMed.TraficoMedellin.geometria.GrafoVia
import com.unalMed.TraficoMedellin.vias.Sentido
import com.unalMed.TraficoMedellin.vias.TipoVia
import com.unalMed.TraficoMedellin.vias.CamaraFotoDeteccion
import com.unalMed.TraficoMedellin.movil.Comparendo


object Conexion {
  val url = "bolt://localhost/7687"
  val user = "neo4j" //Usuario por defecto
  val pass = "graph" //Pass de la bd activa

  def getSession(): (Driver, Session) = {
    val driver = GraphDatabase.driver(url, AuthTokens.basic(user, pass))
    val session = driver.session
    (driver, session)
  }

  def guardarDatos(){
    borrarDatos()
    insertarVehiculos()
    insertarViajes()
    insertarComparendos()
    println("Datos guardados")
  }
  
  def borrarDatos(){
    val (driver, session) = getSession()
    var script ="""match (v:Vehiculo)-[r]-()
                match (v2:Vehiculo)
                match (vi:Viaje)-[r2]-()
                match (vi2:Viaje)
                match (co:Comparendo)-[r3]-()
                match (co2:Comparendo)
                delete r,r2,r3,v2,vi2, co2""" 
    var result = session.run(script)
    session.close()
    driver.close()
    
  }
  
  def getIntersecciones(): ArrayBuffer[Interseccion] ={
    val (driver, session) = getSession()
    val script = s"MATCH (i:Interseccion) RETURN i"
    val result = session.run(script)
    val intersecciones = ArrayBuffer.empty[Interseccion]
    while (result.hasNext()) {
      val values = result.next().values()
      val nodo = values.get(0) //Nodo 0 del return
      intersecciones += new Interseccion(nodo.get("cx").asInt(), nodo.get("cy").asInt(),Some(nodo.get("nombre").asString()))
    }
    session.close()
    driver.close()
    intersecciones    
  }
   
  
  def getVias(): ArrayBuffer[Via]={
    val (driver, session) = getSession()
	
    val script = s"""MATCH (v:Via),
                     (v)-[:TIENE_SENTIDO]->(s:Sentido),
                     (v)-[:ES_TIPO_VIA]->(tv:TipoVia),
	                   (v)-[:INICIA_EN]->(io:Interseccion), 
	                   (v)-[:TERMINA_EN]->(id:Interseccion)
                     
                       RETURN v,s, tv, io, id"""
    val result = session.run(script)
    val vias = ArrayBuffer.empty[Via]
    while (result.hasNext()) {
      val values = result.next().values()
      val nodo = values.get(0) //Nodo 0 del return
      val nodo1= values.get(1)
      val nodo2= values.get(2)
      val nodo3= values.get(3)
      val nodo4= values.get(4)
      val sentido:Sentido= if(nodo1.get("nombre").asString=="unaVia"){
        Sentido.unaVia     }else{Sentido.dobleVia}
      val tipoVia: TipoVia= TipoVia(nodo2.get("nombre").asString)
      val io:Interseccion=Simulacion.intersecciones.filter(x=> x==Punto(nodo3.get("cx").asInt, nodo3.get("cy").asInt))(0)
      val id:Interseccion=Simulacion.intersecciones.filter(x=> x==Punto(nodo4.get("cx").asInt, nodo4.get("cy").asInt))(0)
      val velMax= nodo.get("velMax").asDouble()
      val numero= nodo.get("numero").asString()
      val nombre= Some(nodo.get("nombre").asString())
      vias += new Via(io, id, velMax, tipoVia, sentido, numero, nombre)
    }
    session.close()
    driver.close()
    vias   
  }
  def getVehiculos(): ArrayBuffer[Vehiculo]={
    val (driver, session) = getSession()
    val vehiculos = ArrayBuffer.empty[Vehiculo]
    var script = s"MATCH (v:Vehiculo) RETURN v"
    var result = session.run(script)
    while (result.hasNext()) {
      val values = result.next().values()
      val nodo = values.get(0) //Nodo 0 del return
      val vel= Velocidad(nodo.get("velocidad").asDouble(),Angulo(0))
      val ace= Aceleracion(nodo.get("aceleracion").asDouble())
      var veh: Vehiculo= null
      (nodo.get("tipo").asString()) match{
        case "Bus" => veh= new Bus(vel, ace)
        case "Camion" => veh= new Camion(vel, ace)
        case "Carro" => veh= new Carro(vel, ace)
        case "Moto" => veh= new Moto(vel, ace)
        case "MotoTaxi" => veh= new MotoTaxi(vel, ace)
      }
      veh.placa= nodo.get("placa").asString()
      vehiculos += veh
      veh.posicion= new Punto(nodo.get("x").asInt(), nodo.get("y").asInt())
    }
    
    session.close()
    driver.close()
    vehiculos  
  }

  def getViajes(): ArrayBuffer[Viaje]={
    val (driver, session) = getSession()
    val script = s"""MATCH (v:Viaje), 
                      (v)-[:DE_VEHICULO]->(ve:Vehiculo),
                      (v)-[:INICIA_EN]->(io:Interseccion),
                      (v)-[:TERMINA_EN]->(id:Interseccion),
                      (v)-[:CON_SIGUIENTE_INTERSECCION]->(si:Interseccion),
                      (v)-[:EN_VIA_ACTUAL]->(via: Via)-[:INICIA_EN]->(ioVia:Interseccion),
                      (via)-[:TERMINA_EN]->(idVia:Interseccion)
                      RETURN v,ve,io,id,si,ioVia, idVia"""
    val result = session.run(script)
    val viajes = ArrayBuffer.empty[Viaje]
    while (result.hasNext()) {
      val values = result.next().values()
      val nodo = values.get(0) //Nodo 0 del return
      val nodo1 = values.get(1)
      val nodo2= values.get(2)
      val nodo3= values.get(3)
      val nodo4= values.get(4)
      val nodo5= values.get(5)      
      val nodo6= values.get(6)
      val veh:Vehiculo=Simulacion.vehiculos.filter(_.placa==nodo1.get("placa").asString())(0)
      val io:Interseccion=Simulacion.intersecciones.filter(x=> x==Punto(nodo2.get("cx").asInt, nodo2.get("cy").asInt))(0)
      val id:Interseccion=Simulacion.intersecciones.filter(x=> x==Punto(nodo3.get("cx").asInt, nodo3.get("cy").asInt))(0)
      val si:Interseccion=Simulacion.intersecciones.filter(x=> x==Punto(nodo4.get("cx").asInt, nodo4.get("cy").asInt))(0)
      var viaje= new Viaje(io, id,veh)
      viaje.trayectoVias= GrafoVia.caminoCorto(si, id)
      viaje.distanciaARecorrer= nodo.get("distanciaARecorrer").asDouble()
      viaje.siguienteInterseccion=si
      viaje.viaActual= getVia(Punto(nodo5.get("cx").asInt, nodo5.get("cy").asInt),Punto(nodo6.get("cx").asInt, nodo6.get("cy").asInt))
      viajes += viaje
      
    }
    session.close()
    driver.close() 
    viajes
  }
  def getComparendos(): ArrayBuffer[Comparendo]={
    val (driver, session) = getSession()
    val script = s"""MATCH (c:Comparendo), 
                      (c)-[:A_VEHICULO]->(ve:Vehiculo),
                      (c)-[:EN_VIA]->(via: Via)-[:INICIA_EN]->(ioVia:Interseccion),
                      (via)-[:TERMINA_EN]->(idVia:Interseccion)
                      RETURN c,ve,ioVia, idVia"""
    val result = session.run(script)
    val comparendos = ArrayBuffer.empty[Comparendo]
    while (result.hasNext()) {
      val values = result.next().values()
      val nodo = values.get(0) //Nodo 0 del return
      val nodo1 = values.get(1)
      val nodo2= values.get(2)
      val nodo3= values.get(3)
      val veh:Vehiculo=Simulacion.vehiculos.filter(_.placa==nodo1.get("placa").asString())(0)
      val io:Interseccion=Simulacion.intersecciones.filter(x=> x==Punto(nodo2.get("cx").asInt, nodo2.get("cy").asInt))(0)
      val id:Interseccion=Simulacion.intersecciones.filter(x=> x==Punto(nodo3.get("cx").asInt, nodo3.get("cy").asInt))(0)
      var via= getVia(io,id)
      comparendos+=new Comparendo(veh, nodo.get("velVehiculo").asDouble, nodo.get("maxVelocidad").asDouble, via)
       }
    session.close()
    driver.close() 
    comparendos
  }
  def getVia(pOrigen: Punto, pDestino: Punto):Via={
    Simulacion.vias.filter(x=> x.origen==pOrigen && x.fin==pDestino)(0)
  }
  
  def getCamara(): ArrayBuffer[CamaraFotoDeteccion] ={
    val (driver, session) = getSession()
    val script = s"""MATCH (c:CamaraFotoMulta), 
                      (c)-[:ESTA_UBICADA_EN]->(via:Via),
                       (via)-[:INICIA_EN]->(io:Interseccion),
                      (via)-[:TERMINA_EN]->(id:Interseccion)
                    RETURN c, io, id """
    
    val result = session.run(script)
    val camaras = ArrayBuffer.empty[CamaraFotoDeteccion]
    while (result.hasNext()) {
      val values = result.next().values()
      val nodo = values.get(0) //Nodo 0 del return
      val nodo1 = values.get(1)
      val nodo2 = values.get(2)
      val io:Interseccion=Simulacion.intersecciones.filter(x=> x==Punto(nodo1.get("cx").asInt, nodo1.get("cy").asInt))(0)
      val id:Interseccion=Simulacion.intersecciones.filter(x=> x==Punto(nodo2.get("cx").asInt, nodo2.get("cy").asInt))(0)
      val via= getVia(io, id)
      val camara=new CamaraFotoDeteccion(via, nodo.get("distanciaOrigen").asInt)
      camaras+=camara
      via.fotoDeteccion= Some(camara)
      }
    session.close()
    driver.close()
    camaras   
  }
  def insertarViajes()={
    val (driver, session) = getSession()
    Simulacion.viajes.foreach(viaje=>{
    var script = s"""MATCH (ve: Vehiculo{placa: '${viaje.vehiculo.placa}'})
                     MATCH (io: Interseccion{cx:${viaje.interseccionOrigen.x}, cy:${viaje.interseccionOrigen.y}})  
                     MATCH (id: Interseccion{cx:${viaje.interseccionDestino.x}, cy:${viaje.interseccionDestino.y}})  
                     MATCH (siguienteInterseccion: Interseccion{cx:${viaje.siguienteInterseccion.x}, cy:${viaje.siguienteInterseccion.y}})  
                     MATCH (viaActual: Via)-[:INICIA_EN]->(:Interseccion{cx:${viaje.viaActual.origen.x}, cy:${viaje.viaActual.origen.y}}) 
                     MATCH (viaActual)-[:TERMINA_EN]->(:Interseccion{cx:${viaje.viaActual.fin.x}, cy:${viaje.viaActual.fin.y}})                        
                     CREATE (vi:Viaje{distanciaARecorrer:${viaje.distanciaARecorrer}}),
                     (vi)-[:DE_VEHICULO]->(ve),
                     (vi)-[:INICIA_EN]->(io),
                     (vi)-[:TERMINA_EN]->(id),
                     (vi)-[:CON_SIGUIENTE_INTERSECCION]->(siguienteInterseccion),
                     (vi)-[:EN_VIA_ACTUAL]->(viaActual)
                     """
                     
    var result = session.run(script)})
    session.close()
    driver.close()
  }
  
  def insertarVehiculos()={
    val (driver, session) = getSession()
    var script1 = ""
    var script2 = ""
    Simulacion.vehiculos.foreach(vehiculo=>{
    script1= vehiculo match{
      case vehiculo: Carro => "tipo: 'Carro'})"
  	    case vehiculo: Camion => "tipo: 'Camion'})"
  	    case vehiculo: Bus => "tipo: 'Bus'})"
  	    case vehiculo: Moto => "tipo: 'Moto'})"
  	    case vehiculo: MotoTaxi => "tipo: 'MotoTaxi'})"
  	  
    }
    script2 = s"CREATE (:Vehiculo{x:${vehiculo.posicion.x},y:${vehiculo.posicion.y},velocidad:${vehiculo.velocidadMax.magnitud}, aceleracion:${vehiculo.aceleracion.magnitud}, placa:'${vehiculo.placa}', "+script1
     var result = session.run(script2)})
    session.close()
    driver.close()
  }
  
  def insertarComparendos()={
    val (driver, session) = getSession()
    Simulacion.listaComparendos.foreach(c=>{
    var script = s"""MATCH (ve: Vehiculo{placa: '${c.vehiculo.placa}'})
                     MATCH (via: Via)-[:INICIA_EN]->(:Interseccion{cx:${c.via.origen.x}, cy:${c.via.origen.y}}) 
                     MATCH (via)-[:TERMINA_EN]->(:Interseccion{cx:${c.via.fin.x}, cy:${c.via.fin.y}})                        
                     CREATE (comp:Comparendo{velVehiculo:${c.velVehiculo}, maxVelocidad:${c.maxVelocidad} }),
                     (comp)-[:A_VEHICULO]->(ve),
                     (comp)-[:EN_VIA]->(via)
                     """                     
    var result = session.run(script)})
    session.close()
    driver.close()
  }
    
  
}