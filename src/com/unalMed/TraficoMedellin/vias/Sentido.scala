package com.unalMed.TraficoMedellin.vias

class Sentido private (val nombre:String)


object Sentido {
  
  def unaVia = {
    
    new Sentido("unaVia")
  }
  
  def dobleVia = {
    
    new Sentido("dobleVia")
    
  }
} 
  
