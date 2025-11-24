package com.example.examen1erparcial

import android.R
import android.content.res.Configuration
import android.os.Bundle
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar


import androidx.compose.material3.Switch
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examen1erparcial.ui.theme.Examen1erParcialTheme
import com.example.examen1erparcial.ui.theme.Purple40
import com.example.examen1erparcial.ui.theme.Purple80

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
fun PantallaListaCompra() {

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "FECHA_COMPRA:",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        enabled = false,
                        placeholder = { Text("Introduce Fecha") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(12.dp))

                // TIPO PRODUCTO + SPINNER + SWITCH
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        "TIPO_PRODUCTO:",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(2f)
                    )

                    // Spinner equivalente → DropdownMenu
                    var expanded by remember { mutableStateOf(false) }
                    var selectedOption by remember { mutableStateOf("Seleccione") }
                    val opciones = listOf("Opción 1", "Opción 2")

                    Box(modifier = Modifier.weight(2f)) {
                        OutlinedButton(onClick = { expanded = true }) {
                            Text(selectedOption)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            opciones.forEach {
                                DropdownMenuItem(text={ Text(it)},onClick = {
                                    selectedOption = it
                                    expanded = false
                                })
                            }
                        }
                    }

                    Switch(
                        checked = false,
                        onCheckedChange = {},
                        modifier = Modifier.weight(2f)
                    )
                }

                Spacer(Modifier.height(12.dp))


                // NOMBRE PRODUCTO
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
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Nombre Producto") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(12.dp))

                // IMPORTE + Switch
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
                        value = "",
                        onValueChange = {},
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

            // BOTÓN AÑADIR
            Spacer(Modifier.height(16.dp))
            Button(onClick = {}, modifier = Modifier.padding(top = 16.dp)) {
                Text( text = "AÑADIR PRODUCTO")
            }

            // IMPORTE TOTAL
            Spacer(Modifier.height(16.dp))
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

            Spacer(Modifier.height(16.dp))

            Text(
                "DATOS LISTA COMPRA",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
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
fun DebugButtonColors() {
    val defaults = ButtonDefaults.buttonColors() // colores por defecto para Button
    val containerColorState = defaults.containerColor
    val containerColor = containerColorState

    val primary = MaterialTheme.colorScheme.primary
    val onPrimary = MaterialTheme.colorScheme.onPrimary

    Column(modifier = Modifier.padding(16.dp)) {
        Text("MaterialTheme.primary = $primary")
        Text("Button containerColor (enabled) = $containerColor")

        Button(onClick = {}, modifier = Modifier.padding(top = 8.dp)) {
            Text("Botón de prueba")
        }
    }
}
