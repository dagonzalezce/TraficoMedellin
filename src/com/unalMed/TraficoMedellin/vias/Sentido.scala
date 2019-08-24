package com.unalMed.TraficoMedellin.vias

case class Sentido private (val nombre:String)


object Sentido {
  
  def unaVia = {
    
    new Sentido("unaVia")
  }
  
  def dobleVia = {
    
    new Sentido("dobleVia")
    
  }
} 
  
