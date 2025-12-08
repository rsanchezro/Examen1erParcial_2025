package com.example.examen1erparcial.modelo

import com.example.examen1erparcial.modelo.Calculable
import java.util.Date

class Lista_Compra(var fecha: Date): Calculable {
    //Inicializo el arrayList
    private val productos_cesta=mutableListOf<Producto_Cesta>()

    //fun calcularTotal_bis():Double=productos_cesta.sumOf { it.precio }

    /**
     * Calcula el total de la cesta
     */
    override fun calcularTotal(): Double {
       var importe_total=0.0

        productos_cesta.forEach { producto->importe_total+=producto.precio }
        /* Otra forma de hacerlo
        for(i in productos_cesta)
        {
            importe_total+=i.precio

        }*/
        return importe_total
    }

    /** Funcion agregar producto, agrega un producto a la
     * cesta
     */
    fun agregar_producto(p: Producto_Cesta){
        productos_cesta.add(p)
    }

    /** Funcion para obtener la cesta de la compra
     * Retorna una Lista no modificable, para proteger la lista
     */
    fun obtener_productos_cesta()=productos_cesta.toList()

    /** Funcion para filtrar productos con su expresion
     * mas reducida
     */
    fun filtrar_productosbis(filtro:(p: Producto_Cesta)-> Boolean)= productos_cesta.filter { filtro(it) }


    /** Funcion que sirve para filtrar productos
     *
     */
    fun filtrar_productos(filtro:(p: Producto_Cesta)-> Boolean):List<Producto_Cesta>
    {
        val lista_resultado=mutableListOf<Producto_Cesta>()


        //Lo mismo pero con foreach
        productos_cesta.forEach {
            //Lo que hay que hacer con cada elemento
            //de la lista
            if(filtro(it)==true)
                lista_resultado.add(it)

        }


        //Recorro la lista de productos de la cesta
        //y compruebo por la función filtro si ese producto
        //se va a añadir a la lista resultado
     /*   for(producto in productos_cesta)
        {
            if(filtro(producto)==true)
            {//El producto cumple el criterio del filtro
                //lo añado a una lista de resultados
                lista_resultado.add(producto)
            }
        }*/
        return lista_resultado.toList()
    }


}