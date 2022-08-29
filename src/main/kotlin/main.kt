const val COMMISSION_PERCENTAGE_VISA_MIR = 0.0075
const val COMMISSION_MIN_TAX_VISA_MIR = 35_00
const val COMMISSION_PERCENTAGE_MASTERCARD_MAESTRO = 0.006
const val COMMISSION_ADDITIONAL_TAX_MASTERCARD_MAESTRO = 20_00
const val SINGLE_PAYMENT_BORDER_MASTERCARD_MAESTRO = 300_00
const val TOTAL_PAYMENT_BORDER_MASTERCARD_MAESTRO = 75_000_00
fun main() { //Вместо рублей в качестве значений используем копейки
    println("Комиссия составит ${transferCommission(400_00, "Mastercard", 75_000_00)} копеек")
}

fun transferCommission(amount: Int, typeAccount: String = "VK Pay", sumAmountsLetter: Int = 0) = when (typeAccount) {
        "VK Pay" -> 0
        "Visa", "Мир" ->
            if (amount * COMMISSION_PERCENTAGE_VISA_MIR > COMMISSION_MIN_TAX_VISA_MIR)
                (amount * COMMISSION_PERCENTAGE_VISA_MIR).toInt()
            else COMMISSION_MIN_TAX_VISA_MIR
        "Mastercard", "Maestro" ->
            if (amount > SINGLE_PAYMENT_BORDER_MASTERCARD_MAESTRO && (sumAmountsLetter + amount) <= TOTAL_PAYMENT_BORDER_MASTERCARD_MAESTRO) 0
            else if (sumAmountsLetter + amount > TOTAL_PAYMENT_BORDER_MASTERCARD_MAESTRO)
                ((sumAmountsLetter + amount - TOTAL_PAYMENT_BORDER_MASTERCARD_MAESTRO) * COMMISSION_PERCENTAGE_MASTERCARD_MAESTRO).toInt() + COMMISSION_ADDITIONAL_TAX_MASTERCARD_MAESTRO
            else (amount * COMMISSION_PERCENTAGE_MASTERCARD_MAESTRO).toInt() + COMMISSION_ADDITIONAL_TAX_MASTERCARD_MAESTRO
        else -> throw Exception("Платёжная система не зарегистрирована") //вернуть 0 было бы менее логически правильно
    }
