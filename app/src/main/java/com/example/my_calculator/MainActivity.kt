package com.example.my_calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tv: TextView? = null
    private var lastDot = false
    private var lastNumeric = false
    private var isOperatorAdded = false
    private var lastans="0.0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lastNumeric = true
        tv = findViewById(R.id.screen)
        tv?.text = "0";
    }

    //view denotes the component which clicked on it, which button click on it.
    fun onClick(view: View) {
        //if digit buttons pressed
        if (tv?.text?.length == 1 && tv?.text!!.get(0) == '0') {
            tv?.text = (view as Button).text
        } else {
            tv?.append((view as Button).text)
        }
    }

    fun onClear(view: View) {
        tv?.text = "0"
        lastDot = false
        lastNumeric = true
        isOperatorAdded = false
    }

    fun onDel(view: View) {
        var tv_string = tv?.text
        //deleting dot make a non floating number
        var lastchar = tv?.text?.get(tv?.text?.length!! - 1)
        if (lastchar == '.')
            lastDot = false
        else if (lastchar == '÷' || lastchar == '×' || lastchar == '-' || lastchar == '+' || lastchar == '%')
            isOperatorAdded = false

        if (tv_string?.length == 1) tv?.text = "0"
        else tv?.text = tv_string?.substring(0, tv_string?.length - 1)
        lastchar = tv?.text?.get(tv?.text?.length!! - 1)
        if (lastchar == '÷' || lastchar == '×' || lastchar == '-' || lastchar == '+' || lastchar == '%')
            isOperatorAdded = true
        if (!isOperatorAdded && tv_string!!.contains("."))
            lastDot = true
    }

    fun onDot(view: View) {
        var lastchar = tv?.text?.get(tv?.text?.length!! - 1)
        if (!lastDot && lastchar != '.' && lastchar != '÷' && lastchar != '×' && lastchar != '-' && lastchar != '+' && lastchar != '%') {
            tv?.append(".")
            lastDot = true
        } else if (!isOperatorAdded && !tv?.text?.contains(".")!!) {
            tv?.append(".")
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        var lastchar = tv?.text?.get(tv?.text?.length!! - 1)
        if (!isOperatorAdded) {
            //find the equivalent answer and then append new-operator in the end
            //appending
            tv?.append((view as Button).text)
            isOperatorAdded = true
            lastDot = false
        } else if ((view as Button).text == "-" && (lastchar == '÷' || lastchar == '×' || lastchar == '+' || lastchar == '%')) {
            tv?.append("-")
        }
    }

    fun equals(view: View) {
        if (isOperatorAdded) {
            for (i in 1..(tv?.text!!.length - 1)) {
                if (!tv!!.text.get(i).isDigit() && tv!!.text.get(i) != '.') {
                    if (tv!!.text.length - 1 - i > 0 && tv!!.text.last().isDigit()) {
//                        Toast.makeText(this, "${tv?.text?.get(i)}", Toast.LENGTH_SHORT).show()
                        var left = (tv!!.text.substring(0, i).toFloat())
                        var right = (tv!!.text.substring(i + 1, tv!!.text.length).toFloat())
//                        Toast.makeText(this, "${left} $right", Toast.LENGTH_SHORT).show()
                        if (tv!!.text.get(i) == '÷') {
                            tv?.text = "${(left / right).toString()}"
                        } else if (tv!!.text.get(i) == '-') {
                            tv?.text = "${(left - right).toString()}"
                        } else if (tv!!.text.get(i) == '+') {
                            tv?.text = "${(left + right).toString()}"
                        } else if (tv!!.text.get(i) == '%') {
                            tv?.text = "${(left % right).toString()}"
                        } else {
                            tv?.text = "${(left * right).toString()}"
                        }
                        isOperatorAdded = false
//                        Toast.makeText(this, "${tv?.text.toString()=="0.0"}", Toast.LENGTH_SHORT).show()
                        if(tv!!.text.toString()=="0.0") {
                            onClear(view)
                        }
                        lastans= tv!!.text.toString()
                        lastDot=true
                        break
                    }
                }
            }
        }
    }

    fun ans(view:View){
        onClear(view)
        tv!!.text=lastans
    }
}


