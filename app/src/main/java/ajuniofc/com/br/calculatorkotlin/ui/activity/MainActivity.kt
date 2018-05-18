package ajuniofc.com.br.calculatorkotlin.ui.activity

import ajuniofc.com.br.calculatorkotlin.R
import ajuniofc.com.br.calculatorkotlin.model.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // propriedades da activity
    private var processamento: Processador? = null
    private var display: TextView? = null
    private var buttonC: Button? = null
    private var buttonChange: Button? = null
    private var layoutOperations: LinearLayout? = null
    private var buttonAction1: Button? = null
    private var buttonAction2: Button? = null
    private var buttonAction3: Button? = null
    private var buttonAction4: Button? = null
    private var resultado: Double = 0.0
    private var operando: Double = 0.0
    var operacao: Operation? = null
    // variavel computada
    private var displayValue: Double
        get(){
            val stringValue = display?.text.toString()
            return stringValue.toDouble()
        }
        set(value) {
            display?.text = value.toString()
        }
    private var userIsInMidleOfTyping = false
    private var isCalculatorBasic = true

    // Mapa de operacoes
    private val operationMap = mapOf<String,Operation>("+" to Operation.SOMAR,
                                    "-" to Operation.SUBTRAIR,
                                    "*" to Operation.MULTIPLICAR,
                                    "/" to Operation.DIVIDIR,
                                    "!" to Operation.FATORIAL,
                                    "x²" to Operation.QUADRADO,
                                    "√" to Operation.RAIZ,
                                    "C" to Operation.LIMPAR,
                                    "=" to Operation.IGUAL)

    // Funcao do ciclo de vida da activity executada sempre que cria a activity
    // Faz as ligacoes entre a activity e as views do xml
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        display = calculator_display
        buttonC = calculator_c
        // adiciona um click longo no botao
        buttonC?.setOnLongClickListener{
            inicializar()
            true
        }

        buttonChange = calculator_change
        layoutOperations = calculator_operations
        buttonAction1 = calculator_operation1
        buttonAction2 = calculator_operation2
        buttonAction3 = calculator_operation3
        buttonAction4 = calculator_operation4
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

    // Função que pega a operacao tocada pelo usuario
    fun onAction(view: View){
        val button = view as Button
        val keyOperation = button.text.toString()
        val operation = operationMap[keyOperation]

        when(operation){
            Operation.LIMPAR -> redo()
            Operation.SOMAR -> setNumber1(operation)
            Operation.SUBTRAIR -> setNumber1(operation)
            Operation.MULTIPLICAR -> setNumber1(operation)
            Operation.DIVIDIR -> setNumber1(operation)
            Operation.RAIZ -> calculoAvancado(operation)
            Operation.QUADRADO -> calculoAvancado(operation)
            Operation.FATORIAL -> calculoAvancado(operation)
            Operation.IGUAL -> {
                if (isCalculatorBasic) {
                    setNumber2()
                }
                calcula()
                userIsInMidleOfTyping = false
            }
        }
    }

    // Realiza o calculo avancado
    private fun calculoAvancado(operation: Operation?) {
        operacao = operation
        calcula()
        userIsInMidleOfTyping = false
    }

    // Verifica qual a operacao o usuario escolheu e realiza o calculo
    fun calcula(){
        when(operacao) {
            Operation.SOMAR -> processamento = Soma(numero1 = resultado, numero2 = operando)
            Operation.SUBTRAIR -> processamento = Subtracao(numero1 = resultado, numero2 = operando)
            Operation.MULTIPLICAR -> processamento = Multiplicacao(numero1 = resultado, numero2 = operando)
            Operation.DIVIDIR -> processamento = Divisao(numero1 = resultado, numero2 = operando)
            Operation.FATORIAL -> processamento = Fatorial(displayValue)
            Operation.QUADRADO -> processamento = Quadrado(displayValue)
            Operation.RAIZ -> processamento = Raiz(displayValue)
        }
        // mostra no display o resultado do calculo
        displayValue = processamento?.calcular()!!
    }

    // guarda o primeiro numero digitado
    fun setNumber1(operation: Operation){
        resultado = displayValue
        displayValue = 0.0
        operacao = operation
        userIsInMidleOfTyping = false
    }

    // guarda o segundo numero digitado
    fun setNumber2(){
        operando = displayValue
    }

    // Função que apaga o ultimo numero inserido
    fun redo(){
        if(userIsInMidleOfTyping) {
            val stringValue = display?.text.toString()
            if (stringValue.isEmpty()) {
                inicializar()
            } else if (displayValue > 0.0) {
                val currentValue = stringValue.substring(0, stringValue.lastIndex)
                display?.text = currentValue
                if (currentValue.isEmpty()) {
                    inicializar()
                }
            }
        }else{
            inicializar()
        }
    }

    // Função que controla a mudança da calculadora
    fun onChange(view: View){
        if (isCalculatorBasic){
            changeToAvanced()
        }else{
            changeToBasic()
        }
        inicializar()
    }

    // Muda a calculadora para basica
    private fun changeToBasic() {
        buttonChange?.text = "Avançada"
        isCalculatorBasic = true
        buttonAction1?.text = "+"
        buttonAction2?.text = "-"
        buttonAction3?.text = "*"
        layoutOperations?.addView(buttonAction4)
    }

    // Muda a calculadora para avançada
    private fun changeToAvanced() {
        buttonChange?.text = "Básica"
        isCalculatorBasic = false
        buttonAction1?.text = "!"
        buttonAction2?.text = "x²"
        buttonAction3?.text = "√"
        layoutOperations?.removeView(buttonAction4)
    }
}
