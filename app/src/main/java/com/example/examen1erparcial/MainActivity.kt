package com.example.examen1erparcial

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.examen1erparcial.databinding.ActivityMainBinding
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var mibinding: ActivityMainBinding
    //Defino un mutableList de Lista_Compra, para
    //poder almacenar todas las listas de la compra
    val mis_listas_compra=mutableListOf<Lista_Compra>()
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

               mibinding.editTextDate.setText("$dia-${mes+1}-$anno")

               //Aqui voy a gestionar si existe una lista de la compra
               //con la fecha seleccionada
              var fecha_compra= SimpleDateFormat("dd-MM-yyyy").parse("$dia-${mes+1}-$anno")
               mi_lista_compra_actual=mis_listas_compra.find{it.fecha==fecha_compra}
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