package com.example.examen1erparcial.ui.listacompra

import androidx.lifecycle.ViewModel
import com.example.examen1erparcial.modelo.ListaCompra
import com.example.examen1erparcial.modelo.Producto_Cesta
import com.example.examen1erparcial.modelo.TipoProducto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

class ListaCompraViewModel: ViewModel() {
    private var _state = MutableStateFlow<ListaCompraUiState>(ListaCompraUiState())
    public val state: StateFlow<ListaCompraUiState> = _state

    // ---------- SETTERS DE FORMULARIO ----------
    fun setFecha(fecha_millis: Long) {
        _state.update { it.copy(fecha = fecha_millis) }
    }

    fun setNombre(nombre: String) {
        _state.update { it.copy(nombre = nombre) }
    }

    fun setTipoProducto(tipo: TipoProducto) {
        _state.update { it.copy(tipoProducto = tipo) }
    }

    fun setImporte(importe: Double) {
        _state.update { it.copy(importe = importe) }
    }

    fun añadir_producto() {
        val current = _state.value
        if (current.isFormValid) {
            val nuevoProducto = Producto_Cesta(
                current.nombre,
                current.tipoProducto!!,
                current.importe!!
            )

            val fechaDate = Date(current.fecha!!) // Convertimos Long a Date

            //Genero la nueva lista de las compras
            val listasActualizadas = if (current.listas_compra.any { it.fecha == fechaDate }) {
                current.listas_compra.map {
                    if (it.fecha == fechaDate) it.copy(productos = it.productos + nuevoProducto)
                    else it
                }
            } else {
                current.listas_compra + ListaCompra(fechaDate, listOf(nuevoProducto))
            }
            //Actualizo su valor
            _state.update { it.copy(listas_compra = listasActualizadas) }
        }
    }
}

//Defino ListaCompraState que contiene las listas de la compra
data class ListaCompraUiState(
    //No puedo definir mutableList porque el objeto observable solo
    //recompondrá la vista si se detecta una nueva lista
    val listas_compra: List<ListaCompra> = emptyList<ListaCompra>(),

    // --- estado del formulario ---
    val fecha: Long? = null,
    val nombre: String = "",
    val tipoProducto: TipoProducto? = null,
    val importe: Double? = null

    )
{
    val isFormValid: Boolean
        get() = fecha != null &&
                nombre.isNotBlank() &&
                tipoProducto != null &&
                importe != null &&
                importe > 0
}