package ajuniofc.com.br.calculatorkotlin.model

/**
 * Created by admin on 15/05/2018.
 */
class Subtracao(numero1: Double, numero2: Double) : OperacaoBasica(numero1, numero2), Processador{
    override fun calcular(): Double {
        return numero1.minus(numero2)
    }

    override fun getSimboloOperacao(): String {
        return "-"
    }
}