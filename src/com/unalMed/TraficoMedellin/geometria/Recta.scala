package com.unalMed.TraficoMedellin.geometria

trait Recta {
  type t <: com.unalMed.TraficoMedellin.geometria.Punto 
  val origen : t
  val fin  : t
  
   def calcularAnguloRecta(): Double={
    
    var a = Math.atan2(fin.y-origen.y, fin.x-origen.x)*180.0/Math.PI
    
    if(a<0) a+= 360
    
    a
  }
}