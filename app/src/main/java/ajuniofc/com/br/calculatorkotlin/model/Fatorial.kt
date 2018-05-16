package ajuniofc.com.br.calculatorkotlin.model

/**
 * Created by admin on 15/05/2018.
 */
class Fatorial(numero1: Double) : OperacaoAvancada(numero1), Processador{
    val STEP: Double = 1.0

    override fun calcular(): Double {
        return fatorial(numero)
    }

    override fun getSimboloOperacao(): String {
        return "!"
    }

    fun fatorial(numero: Double): Double{
        if(numero == STEP){
            return STEP;
        }
        return numero * fatorial(numero -1)
    }
}