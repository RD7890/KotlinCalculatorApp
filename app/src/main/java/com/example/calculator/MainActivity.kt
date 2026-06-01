package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentNumber: StringBuilder = StringBuilder()
    private var currentOperator: String? = null
    private var firstOperand: Double = 0.0
    private var isNewOperation: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.displayTextView)
        display.text = "0"
    }

    fun onDigit(view: View) {
        if (isNewOperation) {
            currentNumber.clear()
            isNewOperation = false
        }
        val button = view as Button
        if (button.text == "." && currentNumber.contains(".")) {
            return // Prevent multiple decimal points
        }
        currentNumber.append(button.text)
        display.text = currentNumber.toString()
    }

    fun onOperator(view: View) {
        val button = view as Button
        if (currentNumber.isNotEmpty()) {
            firstOperand = currentNumber.toString().toDouble()
            currentOperator = button.text.toString()
            isNewOperation = true
        }
    }

    fun onEqual(view: View) {
        if (currentOperator != null && currentNumber.isNotEmpty()) {
            val secondOperand = currentNumber.toString().toDouble()
            var result = 0.0

            when (currentOperator) {
                "+" -> result = firstOperand + secondOperand
                "-" -> result = firstOperand - secondOperand
                "*" -> result = firstOperand * secondOperand
                "/" -> {
                    if (secondOperand == 0.0) {
                        display.text = "Error"
                        currentNumber.clear()
                        currentOperator = null
                        isNewOperation = true
                        return
                    }
                    result = firstOperand / secondOperand
                }
            }
            display.text = result.toString()
            currentNumber.clear().append(result.toString())
            currentOperator = null
            isNewOperation = true
        }
    }

    fun onClear(view: View) {
        currentNumber.clear()
        currentOperator = null
        firstOperand = 0.0
        isNewOperation = true
        display.text = "0"
    }

    fun onDelete(view: View) {
        if (currentNumber.isNotEmpty() && !isNewOperation) {
            currentNumber.deleteCharAt(currentNumber.length - 1)
            if (currentNumber.isEmpty()) {
                display.text = "0"
                isNewOperation = true
            } else {
                display.text = currentNumber.toString()
            }
        } else if (isNewOperation && display.text.isNotEmpty() && display.text != "0" && display.text != "Error") {
            // If a result is displayed, clear it and reset for new input
            onClear(view)
        }
    }
}
