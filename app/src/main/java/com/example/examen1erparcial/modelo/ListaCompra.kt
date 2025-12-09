package com.example.examen1erparcial.modelo

import java.util.Date

data class ListaCompra(val fecha: Date,val productos: List<Producto_Cesta> =emptyList<Producto_Cesta>())



