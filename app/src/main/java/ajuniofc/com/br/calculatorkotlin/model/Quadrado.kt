package ajuniofc.com.br.calculatorkotlin.model

import kotlin.math.pow

/**
 * Created by admin on 15/05/2018.
 */
class Quadrado(numero: Double): OperacaoAvancada(numero), Processador {
    val NUMBER: Int = 2
    override fun getSimboloOperacao(): String {
        return "²"
    }

    override fun calcular(): Double {
        return numero.pow(NUMBER)
    }

}