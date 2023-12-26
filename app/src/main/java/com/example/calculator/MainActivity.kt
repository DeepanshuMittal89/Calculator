package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
class MainActivity : AppCompatActivity() {

    private var tvInput:TextView? = null
    private var tvResult:TextView?=null
    var lastNumber: Boolean = false
    var lastDot:Boolean = false
    var alreadyExecuted:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResult=findViewById(R.id.tvResult)
        tvInput= findViewById(R.id.tviInput)
    }

    fun onDigit(view:View){
        tvInput?.append((view as Button).text)
        lastNumber= true
        if(!alreadyExecuted){
            lastDot = false
            alreadyExecuted = true
        }
    }
    fun onClear(view:View){
        tvInput?.text=""
        tvResult?.text=""
    }

    fun onDot(view:View){
        if(lastNumber == true && lastDot == false){
            tvInput?.append(".")
//            lastNumber= true
            lastDot= true
        }
//        else{
//            Toast.makeText(this, "Enter valid Operation", Toast.LENGTH_SHORT).show()
//        }
    }

    fun onOperator(view:View){
        tvInput?.text?.let {
            if(lastNumber && !isOperator(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumber = false
                lastDot = false


            }
        }
    }

    fun onDEL(view:View){
        var str:String= tvInput?.text.toString()
        if(lastNumber){
            str= str.substring(0,str.length-1)
            tvInput?.text= str

        }
    }

    fun onMainView(view:View){

    }

    fun onEqual(view:View){
        if(lastNumber){
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue= tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")

                    var valueOne = splitValue[0]
                    var valueTwo = splitValue[1]
                    if(prefix.isNotEmpty()){
                        valueOne = prefix+valueOne
                    }

                    tvResult?.text = removeZeroAfterDot((valueOne.toDouble()-valueTwo.toDouble()).toString())
                    tvInput?.text=""

                }
                else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")

                    var valueOne = splitValue[0]
                    var valueTwo = splitValue[1]
                    if(prefix.isNotEmpty()){
                        valueOne = prefix+valueOne
                    }

                    tvResult?.text = removeZeroAfterDot((valueOne.toDouble()+valueTwo.toDouble()).toString())
                    tvInput?.text=""

                }
                else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")

                    var valueOne = splitValue[0]
                    var valueTwo = splitValue[1]
                    if(prefix.isNotEmpty()){
                        valueOne = prefix+valueOne
                    }

                    tvResult?.text = removeZeroAfterDot((Math.round((valueOne.toDouble()*valueTwo.toDouble())*100000.0)/100000.0).toString())
                    tvInput?.text=""

                }
                else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")

                    var valueOne = splitValue[0]
                    var valueTwo = splitValue[1]
                    if(prefix.isNotEmpty()){
                        valueOne = prefix+valueOne
                    }

                    tvResult?.text = removeZeroAfterDot((Math.round((valueOne.toDouble()/valueTwo.toDouble())*100000.0)/100000.0).toString())
                    tvInput?.text=""

                }
            }
            catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun isOperator(value: String):Boolean{
        return if(value.startsWith("-")){
            false
        }
        else{
            value.contains("/") || value.contains("+") || value.contains("*") || value.contains("-")
        }
    }
    private fun removeZeroAfterDot(result:String):String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0, result.length-2)
        }
        return value
    }
}
