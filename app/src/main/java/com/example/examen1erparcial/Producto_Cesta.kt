package com.example.examen1erparcial

data class Producto_Cesta(var nombre:String,
                          var tipo:TipoProducto,
                          var precio:Double)
enum class TipoProducto{COMIDA,BEBIDA,LIMPIEZA_HIGIENE,OTROS}
