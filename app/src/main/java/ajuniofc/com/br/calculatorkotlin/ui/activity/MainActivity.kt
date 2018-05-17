package ajuniofc.com.br.calculatorkotlin.ui.activity

import ajuniofc.com.br.calculatorkotlin.R
import ajuniofc.com.br.calculatorkotlin.model.Processador
import ajuniofc.com.br.calculatorkotlin.model.Soma
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var processamento: Processador? = null
    private var display: TextView? = null
    private var buttonC: Button? = null
    private var resultado: Double = 0.0
    private var operando: Double = 0.0
    var operacao: String? = null
    private var displayValue: Double
        get(){
            val stringValue = display?.text.toString()
            return stringValue.toDouble()
        }
        set(value) {
            display?.text = value.toString()
        }
    private var userIsInMidleOfTyping = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        display = calculator_display
        buttonC = calculator_c
        buttonC?.setOnLongClickListener{
            inicializar()
            true
        }
    }

    // Função inicia a calculadora zerando todos os valores
    fun inicializar(){
        displayValue = 0.0
        userIsInMidleOfTyping = false
        resultado = 0.0
        operando = 0.0
        operacao = null
    }

    // Função que pega o numero tocado e coloca no display da tela
    fun onNumber(view: View){
        val button: Button = view as Button
        val digit: String = button.text.toString()
        val currentValue: String = display?.text.toString()

        if (userIsInMidleOfTyping) {
            display?.text = currentValue + digit
        }else {
            display?.text = digit
            userIsInMidleOfTyping = true
        }
    }

    fun onAction(view: View){
        userIsInMidleOfTyping = false
        val button = view as Button
        operacao = button.text.toString()

        when(operacao){
            "C" -> redo()
            "+" -> setNumber1()
            "-" -> setNumber1()
            "*" -> setNumber1()
            "/" -> setNumber1()
            "=" -> {
                    setNumber2()
                    calcula()
                    }

        }
    }

    fun calcula(){

    }

    fun setNumber1(){
        resultado = displayValue
    }

    fun setNumber2(){
        operando = displayValue
    }

    // Função que apaga o ultimo numero inserido
    fun redo(){
        val stringValue = display?.text.toString()
        if (stringValue.isEmpty()){
            inicializar()
        }else if(displayValue > 0.0) {
            val currentValue = stringValue.substring(0, stringValue.lastIndex)
            display?.text = currentValue
            if (currentValue.isEmpty()){
                inicializar()
            }
        }
    }
}