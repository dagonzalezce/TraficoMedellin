package com.unalMed.TraficoMedellin.geometria

trait Recta {
  type t <: com.unalMed.TraficoMedellin.geometria.Punto 
  val origen : t
  val fin  : t
}