package com.example.examen1erparcial

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.material.icons.Icons

import androidx.compose.runtime.setValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar


import androidx.compose.material3.Switch
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examen1erparcial.modelo.Producto_Cesta
import com.example.examen1erparcial.modelo.TipoProducto
import com.example.examen1erparcial.ui.listacompra.ListaCompraViewModel
import com.example.examen1erparcial.ui.theme.Examen1erParcialTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Examen1erParcialTheme {
             PantallaListaCompra()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListaCompra(viewModelListaCompra: ListaCompraViewModel=viewModel()) {
    val fecha_compra_state=rememberDatePickerState()

    val botonhabilitado by remember { mutableStateOf(false) }

//Me suscribo, cualquier cambio en valores del uiState provocan actualizacion de la función
    //composable

    val uiState by viewModelListaCompra.state.collectAsState()

    // Creamos el DatePickerState.
    val pickerState = rememberDatePickerState(initialSelectedDateMillis = uiState.fecha)

    Scaffold(
        topBar = {
            TopAppBar(
                title ={Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) { Text("Gestor de Lista de la Compra") }},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary ,
                    titleContentColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) //Para que el contenido no quede debajo del TopBar
                .padding(8.dp), //Se añade otro paddin extra
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // -----------------------------
            //    BLOQUE PRINCIPAL (Caja)
            // -----------------------------
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {

                // FECHA
               Fecha_Compra(fecha_compra_state,uiState.fecha){
                   viewModelListaCompra.setFecha(it)
               }

                Spacer(Modifier.height(12.dp))

                // TIPO PRODUCTO + SPINNER + SWITCH
                Tipo_Producto_Spinner_Switch(uiState.tipoProducto.toString()){
                  viewModelListaCompra.setTipoProducto(TipoProducto.valueOf(it))
                }

                Spacer(Modifier.height(12.dp))


                // NOMBRE PRODUCTO
                Nombre_Producto(uiState.nombre){
                    viewModelListaCompra.setNombre(it)
                }

                Spacer(Modifier.height(12.dp))

                // IMPORTE + Switch
                Importe_Switch(uiState.importe){
                   viewModelListaCompra.setImporte(it.toDouble())
                }
            }


            Spacer(Modifier.height(16.dp))
            val contexto=LocalContext.current
            // BOTÓN AÑADIR
            Button(onClick = {

                //Aqui añadimos el producto a la lista
                viewModelListaCompra.añadir_producto()
                //Vaciar campos
                viewModelListaCompra.setNombre("")
                Toast.makeText(contexto,"PRODUCTO AÑADIDO CORRECTAMENTE A LA LISTA",Toast.LENGTH_LONG).show()


            }, modifier = Modifier.padding(top = 16.dp), enabled = uiState.isFormValid) {
                Text( text = "AÑADIR PRODUCTO")
            }


            Spacer(Modifier.height(16.dp))

            // IMPORTE TOTAL
            Importe_Total()


            Spacer(Modifier.height(16.dp))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = Color(0xFFBEBEBE),
                thickness = 1.dp
            )

            // TÍTULO NAVEGACIÓN
            Text(
                "NAVEGACIÓN PRODUCTOS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            // BOTONES ADELANTE / ATRÁS
           Botones_Avanza_Retroceso()

            Spacer(Modifier.height(16.dp))

            Text(
                "DATOS LISTA COMPRA",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun Fecha_Compra(fech_state: DatePickerState,fecha_sel:Long?,onChangeDate:(Long)->Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "FECHA_COMPRA:",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold
        )
        //Configuro el DatePicker
        MiDatePicker(Modifier.weight(1f),fech_state,fecha_sel,onChangeDate)

    }
}

@Composable
fun Tipo_Producto_Spinner_Switch(opcion: String,onOptionSelected:(String)->Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            "TIPO_PRODUCTO:",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(3f)
        )

        // Spinner equivalente → DropdownMenu
        var expanded by remember { mutableStateOf(false) }

        val opciones = TipoProducto.values()

        Box(modifier = Modifier.weight(2f)) {
            OutlinedButton(onClick = { expanded = true }) {
                Text(opcion)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opciones.forEach {
                    DropdownMenuItem(text={ Text(it.toString())},onClick = {
                        onOptionSelected(it.toString())
                        expanded = false
                    })
                }
            }
        }

        Switch(
            checked = false,
            onCheckedChange = {},
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun Nombre_Producto(nombre_producto:String,onNombreChange:(String)->Unit) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            "NOMBRE_PRODUCTO:",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        OutlinedTextField(
            value = nombre_producto,
            onValueChange = {onNombreChange(it)},
            placeholder = { Text("Nombre Producto") },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun Importe_Switch( importe: Double?,onImporteChange:(Double)->Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "IMPORTE:",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        OutlinedTextField(
            value = importe?.let { it.toString() }?:"",
            onValueChange = {it.toDoubleOrNull()?.let{onImporteChange(it)}},
            placeholder = { Text("Importe") },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        Switch(
            checked = false,
            onCheckedChange = {},
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun Importe_Total() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "IMPORTE TOTAL:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(name="Mi previsualizacion", showBackground = true)
@Composable
fun GreetingPreview() {
    Examen1erParcialTheme {
        PantallaListaCompra()
       // DebugButtonColors()
    }
}

@Composable
fun Botones_Avanza_Retroceso()
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 32.dp, end = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {},
            modifier = Modifier.weight(1f)
        ) {
            Text("<", fontSize = 20.sp)
        }

        Spacer(Modifier.width(16.dp))

        Button(
            onClick = {},
            modifier = Modifier.weight(1f)
        ) {
            Text(">", fontSize = 20.sp)
        }
    }
}



//Funcion composable para mostrar la fecha
@Composable

fun MiDatePicker(modificador: Modifier, pickerState: DatePickerState,
                 selectedDateMillis: Long?,
                 onDateChange: (Long) -> Unit) {
    //Controla si se muestra el DatePicker
    var showDatePicker by remember { mutableStateOf(false) }
    //Recuerda el valor, es un remember con un State preconfigurado
    //fecha= rememberDatePickerState()
    //Define la fecha seleccionada para convertirla en fecha formateada
    val selectedDate = selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""


    OutlinedTextField(
        value = selectedDate,
        modifier=modificador,
        onValueChange = { },
        label = { Text(text = "Selecciona", fontSize = 11.sp) },
        readOnly = true,
        trailingIcon = {
            //Cuando hacemos click en el icono aparece, pero
            //no interactua si esta visible el Popup
            IconButton(onClick = {
                showDatePicker = !showDatePicker
                Log.i("INFO", "Click en el icono fecha")
            }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Selecciona fecha"
                )
            }
        }

    )
    //Al modificarse el valor que se indica en el parametro se ejecuta el código
    LaunchedEffect(pickerState.selectedDateMillis) {
        //Cuando selecciono la fecha cierro el DatePicker
        pickerState.selectedDateMillis?.let {
            //Le paso el dato a la funcion padre cuando hay un cambio
            //en la fecha
            onDateChange(it)
            //Para que cierre el DatePicker
            showDatePicker=false
        }
    }
    if (showDatePicker) {
        Popup(
            onDismissRequest = { showDatePicker = false },
            alignment = Alignment.TopStart
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    //Desplazo el DatePicker 64dp con respecto a la parte superior
                    .offset(y = 64.dp)
                    .shadow(elevation = 4.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                DatePicker(
                    //De esta forma no hay que estar pendiente
                    //de cambios de valores, es Material3
                    state = pickerState,
                    //Permite introducir o no las fechas de forma manual
                    showModeToggle = false,

                )
            }
        }
    }
}




//Funcion para convertir milisegundos a una fecha con formato dia/mes/año
fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
