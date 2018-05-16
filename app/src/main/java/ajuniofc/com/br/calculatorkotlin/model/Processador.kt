package ajuniofc.com.br.calculatorkotlin.model

/**
 * Created by admin on 15/05/2018.
 */
interface Processador {

    fun calcular(): Double
    fun getSimboloOperacao(): String
}