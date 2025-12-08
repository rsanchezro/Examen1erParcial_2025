package com.example.examen1erparcial.modelo

data class Producto_Cesta(var nombre:String,
                          var tipo:TipoProducto,
                          var precio:Double)
enum class TipoProducto{COMIDA,BEBIDA,LIMPIEZA_HIGIENE,OTROS}
