package ajuniofc.com.br.calculatorkotlin.model

import kotlin.math.sqrt

/**
 * Created by admin on 15/05/2018.
 */
class Raiz(numero: Double) : OperacaoAvancada(numero), Processador {
    override fun calcular(): Double {
        return sqrt(numero)
    }

    override fun getSimboloOperacao(): String {
        return "¬"
    }
}