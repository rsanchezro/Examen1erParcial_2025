package com.example.examen1erparcial

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.examen1erparcial.databinding.ActivityMainBinding
import com.example.examen1erparcial.modelo.Lista_Compra
import com.example.examen1erparcial.modelo.Producto_Cesta
import com.example.examen1erparcial.modelo.TipoProducto
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var mibinding: ActivityMainBinding
    //Defino un mutableList de Lista_Compra, para
    //poder almacenar todas las listas de la compra
    val mis_listas_compra=mutableListOf<Lista_Compra>()
    var indice_producto=0
    var mi_lista_compra_actual: Lista_Compra?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Instancio el objeto binding
        mibinding= ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        //Vinculo layout
        setContentView(mibinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title="LISTA DE LA COMPRA"
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white, theme))
        setSupportActionBar(toolbar)
        inicializar_componentes()



    }

    private fun inicializar_componentes() {
     //DatePickerDialog
        mibinding.editTextDate.setOnClickListener {
            //Abrimos el DatePickerDialog
            //Obtener la fecha actual
            val calendario= Calendar.getInstance()

            //Instancio un objeto datePickerDialogo
           val midatepicker= DatePickerDialog(this,{vista,anno,mes,dia->

               //Aqui modifico el valor de la fecha del EditText
               mibinding.editTextDate.setText("$dia-${mes+1}-$anno")

               //Aqui voy a gestionar si existe una lista de la compra
               //con la fecha seleccionada
              var fecha_compra= SimpleDateFormat("dd-MM-yyyy").parse("$dia-${mes+1}-$anno")
               mi_lista_compra_actual=mis_listas_compra.find{lista_compra->lista_compra.fecha==fecha_compra}
               if(mi_lista_compra_actual!=null)
               {
                   //Existe una lista de la compra con esa fecha
                   val builder=AlertDialog.Builder(this)

                   builder.run {
                       setMessage("Ya hay una lista de la compra con esa fecha, se añadiran los productos a esa lista")
                       setTitle("LISTA COMPRA")
                       setPositiveButton("Aceptar"){
                           _,_->
                       }
                   create().show()
                   }
                   //Calculamos el importe y lo mostramos
                   mibinding.textViewImporteTotal.text="IMPORTE TOTAL: ${mi_lista_compra_actual!!.calcularTotal()}"
                   //Habilito el boton avanzar si hay mas de 1 producto
                   mibinding.buttonAdelante.isEnabled=(mi_lista_compra_actual!!.obtener_productos_cesta().size>1)

                   //Inicializo un variable que me permite
                   //recorrer los productos de la lista

                   indice_producto=0
                   //Si existe un producto en esa lista
                   //de la compra, muestro el producto
                   if(mi_lista_compra_actual!!.obtener_productos_cesta().size>=1)
                   {
                       val producto_cesta=mi_lista_compra_actual!!.obtener_productos_cesta().get(0)
                       //Muestro tipo de producto
                       mibinding.spinner.setSelection(producto_cesta.tipo.ordinal)
                       //Muestro el nombre
                       mibinding.editTextNombreProducto.setText(producto_cesta.nombre.toString())
                       //Muestro el importe
                       mibinding.editTextImporte.setText(producto_cesta.precio.toString())


                   }

               }
               else
               { //No existe la lista de la compra, la creo
                   //Instancio la nueva lista de la compra
                   mi_lista_compra_actual= Lista_Compra(fecha_compra)
                   //La añado a las listas
                   mis_listas_compra.add(mi_lista_compra_actual!!)
               }


           },calendario.get(Calendar.YEAR),calendario.get(
               Calendar.MONTH),calendario.get(Calendar.DAY_OF_MONTH))

            //Muestro el datepickerDialog
            midatepicker.show()

        }
        //Spinner
        //Defino un ArrayAdapter
        val miarrayAdapter= ArrayAdapter<TipoProducto>(this,android.R.layout.simple_spinner_item,
            TipoProducto.values())
        //Defino el layout cuando se despliega el spinner
        miarrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //Vinculo el arrayAdapter con el spinner
        mibinding.spinner.adapter=miarrayAdapter






        //Deshabilitar botones de recorrido
        mibinding.buttonAAdir.isEnabled=false
        mibinding.buttonAtras.isEnabled=false
        mibinding.buttonAdelante.isEnabled=false


        //Controlar cuando hago click en el nombre de producto
        //Si estan vacios los campos
        mibinding.editTextNombreProducto.addTextChangedListener{
           habilitar_boton_añadir()
        }

        //Controlo si esta vacio en el Importe
        mibinding.editTextImporte.addTextChangedListener{
            habilitar_boton_añadir()
        }

        //Controlar si esta vacio en la fecha
        mibinding.editTextDate.addTextChangedListener{
            habilitar_boton_añadir()
        }

        //Defino el codigo al pulsar el boton añadir
        mibinding.buttonAAdir.setOnClickListener {
            //Añadir el producto a la lista de la compra_actual
            //Creo un producto
            var miproducto= Producto_Cesta(
                mibinding.editTextNombreProducto.text.toString(),
                mibinding.spinner.selectedItem as TipoProducto,
                mibinding.editTextImporte.text.toString().toDouble()
            )
            //Añado el producto a la lista
            mi_lista_compra_actual?.let { it.agregar_producto(miproducto)}
            //Vacio los editText
            mibinding.editTextImporte.setText("")
            mibinding.editTextNombreProducto.setText("")
            //Recalculo el Importe Total
            mibinding.textViewImporteTotal.text="IMPORTE TOTAL: ${mi_lista_compra_actual!!.calcularTotal()}"

            //MUESTRO UN Toast informando de que el producto
            //se añadio a la cesta
            Toast.makeText(this,"PRODUCTO AÑADIDO A LA CESTA", Toast.LENGTH_LONG).show()
        }

        //Boton Avanzar
        mibinding.buttonAdelante.setOnClickListener {
            //Si no estoy en el final de la lista de productos
            //de esa lista de la compra
            if(indice_producto<mi_lista_compra_actual!!.obtener_productos_cesta().size)
            {
                //Muestro los datos de ese producto
                var producto=mi_lista_compra_actual?.let{
                    it.obtener_productos_cesta().get(indice_producto)
                }
                mibinding.editTextImporte.setText(producto?.precio.toString())
                mibinding.editTextNombreProducto.setText(producto?.nombre.toString())
                mibinding.spinner.setSelection(producto?.tipo?.ordinal ?: 0)
                //Avanzo al siguiente producto
                indice_producto++
                //Habilito el boton retroceso
                mibinding.buttonAtras.isEnabled=indice_producto>0



            }
            else
            {
                //Deshabilitar el boton >
                //He alcanzado el final de la lista
                it.isEnabled=false


            }



        }
        mibinding.buttonAtras.setOnClickListener {
            //Si no estoy al principio de la lista de producto
            //de la lista de compra actual
            if(indice_producto>0)
            {
                //Obtengo el producto
                var producto=mi_lista_compra_actual?.let {
                    it.obtener_productos_cesta().get(indice_producto)
                }
                mibinding.editTextImporte.setText(producto?.precio.toString())
                mibinding.editTextNombreProducto.setText(producto?.nombre.toString())
                mibinding.spinner.setSelection(producto?.tipo?.ordinal ?: 0)
                indice_producto--


            }
            else{
                //Deshabilito el boton retroceso
                it.isEnabled=false

            }

        }

        mibinding.switchFiltroTipoproducto.setOnClickListener {
            var lista_productos: MutableList<Producto_Cesta>?
            lista_productos=mutableListOf()
            //Solo filtrare si hay productos que filtrar
            if(mi_lista_compra_actual!!.obtener_productos_cesta().size>1)
            {
                if(mibinding.switchFiltroTipoproducto.isActivated)
                {//Filtrar los productos
                    lista_productos=mi_lista_compra_actual?.let {
                        it.filtrar_productos { mibinding.spinner.selectedItem as TipoProducto ==it.tipo } as MutableList<Producto_Cesta>?
                    }
                }
                //Actualizo el importe total de la lista de productos
                var importe=lista_productos?.let {
                    it.sumOf { it.precio }
                }
                //Actualizo el TextView que muestra el importe Total
                mibinding.textViewImporteTotal.text="IMPORTE TOTAL: $importe"

            }


        }



    }

    fun habilitar_boton_añadir()
    {
        //Compruebo si los valores de los componentes estan vacios
        if((!mibinding.editTextNombreProducto.text.isEmpty())&&
            (!mibinding.editTextDate.text.isEmpty())&&
            (!mibinding.editTextImporte.text.isEmpty()))
        {
            //Habilitar el boton añadir producto
            mibinding.buttonAAdir.isEnabled=true
        }
        else
        {
            mibinding.buttonAAdir.isEnabled=false
        }
    }
}