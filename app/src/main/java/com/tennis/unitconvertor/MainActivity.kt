package com.tennis.unitconvertor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tennis.unitconvertor.ui.theme.UnitConvertorTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConvertorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    UnitConvertor(modifier = Modifier.padding(it))
                }
            }
        }
    }
}

enum class UnitType(val displayName: String) {
    CENTIMETERS("Centimeters"),
    METERS("Meters"),
    MILLIMETERS("Millimeters"),
    FEET("Feet")
}

@Composable
fun UnitConvertor( modifier: Modifier = Modifier){
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf(UnitType.METERS) }
    var outputUnit by remember { mutableStateOf(UnitType.METERS) }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    var conversionFactor by remember { mutableDoubleStateOf(1.0) }
    var oConversionFactor by remember { mutableDoubleStateOf(1.0) }


    fun convertUnits(){
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0 // evaluates to 0.0 if null
        val valueInMeters = inputValueDouble * conversionFactor
        val result = valueInMeters / oConversionFactor
        val roundedResult = (result*100.0).roundToInt() / 100.0
        outputValue = roundedResult.toString()

    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Unit Convertor",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = inputValue, onValueChange = {
            inputValue = it
            convertUnits()
        },
            label = { Text("Input Value") }
            )
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            // Input Box
            Box{
                Button(onClick = { iExpanded = !iExpanded }) {
                    Text(inputUnit.displayName)
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Arrow Down"
                    )
                }
                DropdownMenu( expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                    DropdownMenuItem(text = {Text("Centimeters")}, onClick = {
                        iExpanded = false
                        inputUnit = UnitType.CENTIMETERS
                        conversionFactor = 0.01
                        convertUnits()
                    })
                    DropdownMenuItem(text = {Text("Meters")}, onClick = {
                        iExpanded = false
                        inputUnit = UnitType.METERS
                        conversionFactor = 1.0
                        convertUnits()
                    })
                    DropdownMenuItem(text = {Text("Millimeters")}, onClick = {
                        iExpanded = false
                        inputUnit = UnitType.MILLIMETERS
                        conversionFactor = 0.001
                        convertUnits()
                    })
                    DropdownMenuItem(text = {Text("Feet")}, onClick = {
                        iExpanded = false
                        inputUnit = UnitType.FEET
                        conversionFactor = 0.3048
                        convertUnits()
                    })
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            // Output Box
            Box{
                Button(onClick = {oExpanded = !oExpanded}) {
                    Text(outputUnit.displayName)
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Arrow Down"
                    )
                }
                DropdownMenu( expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    DropdownMenuItem(text = {Text("Centimeters")}, onClick = {
                        oExpanded = false
                        outputUnit = UnitType.CENTIMETERS
                        oConversionFactor = 0.01
                        convertUnits()
                    })
                    DropdownMenuItem(text = {Text("Meters")}, onClick = {
                        outputUnit = UnitType.METERS
                        oExpanded = false
                        oConversionFactor = 1.0
                        convertUnits()
                    })
                    DropdownMenuItem(text = {Text("Millimeters")}, onClick = {
                        oExpanded = false
                        outputUnit = UnitType.MILLIMETERS
                        oConversionFactor = 0.001
                        convertUnits()
                    })
                    DropdownMenuItem(text = {Text("Feet")}, onClick = {
                        oExpanded = false
                        outputUnit = UnitType.FEET
                        oConversionFactor = 0.3048
                        convertUnits()
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (outputValue.isNotEmpty()){
            // Result text
            Text(
                "Result: $inputValue ${inputUnit.displayName} is equal to $outputValue ${outputUnit.displayName}",
                style = MaterialTheme.typography.headlineSmall
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun UnitConvertorPreview() {
    UnitConvertor()
}