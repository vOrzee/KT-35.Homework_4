val commissionPercentageVisaMir = 0.0075
val commissionMinTaxVisaMir = 35_00
val commissionPercentageMastercardMaestro = 0.006
val commissionAdditionalTaxMastercardMaestro = 20_00
val SinglePaymentBorderMastercardMaestro = 300_00
val TotalPaymentBorderMastercardMaestro = 75_000_00
fun main() { //Вместо рублей в качестве значений используем копейки
    println("Комиссия составит ${transferCommission(400_00, "Mastercard", 75_000_00)} копеек")
}

fun transferCommission(amount: Int, typeAccount: String = "VK Pay", sumAmountsLetter: Int = 0) =
    when (typeAccount) {
        "VK Pay" -> 0
        "Visa", "Мир" ->
            if (amount * commissionPercentageVisaMir > commissionMinTaxVisaMir)
                (amount * commissionPercentageVisaMir).toInt()
            else commissionMinTaxVisaMir
        "Mastercard", "Maestro" ->
            if (amount > SinglePaymentBorderMastercardMaestro && (sumAmountsLetter + amount) <= TotalPaymentBorderMastercardMaestro) 0
            else if (sumAmountsLetter + amount > TotalPaymentBorderMastercardMaestro)
                ((sumAmountsLetter + amount - TotalPaymentBorderMastercardMaestro) * commissionPercentageMastercardMaestro).toInt() + commissionAdditionalTaxMastercardMaestro
            else (amount * commissionPercentageMastercardMaestro).toInt() + commissionAdditionalTaxMastercardMaestro
        else -> throw Exception("Платёжная система не зарегистрирована") //вернуть 0 было бы менее логически правильно
    }