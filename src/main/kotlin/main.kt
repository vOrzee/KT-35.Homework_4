const val COMMISSION_PERCENTAGE_VISA_MIR = 0.0075
const val COMMISSION_MIN_TAX_VISA_MIR = 35_00
const val COMMISSION_PERCENTAGE_MASTERCARD_MAESTRO = 0.006
const val COMMISSION_ADDITIONAL_TAX_MASTERCARD_MAESTRO = 20_00
const val SINGLE_PAYMENT_BORDER_MASTERCARD_MAESTRO = 300_00
const val TOTAL_PAYMENT_BORDER_MASTERCARD_MAESTRO = 75_000_00
const val LIMIT_SIMPLE_TRANSACTION = 150_000_00
const val LIMIT_SUM_MONTH_TRANSACTION = 600_000_00
const val LIMIT_SIMPLE_TRANSACTION_VK = 15_000_00
const val LIMIT_SUM_MONTH_TRANSACTION_VK = 40_000_00
fun main() { //Вместо рублей в качестве значений используем копейки
    /*
        Было бы правильнее создать класс TransactionAccount
        с полями "номер счёта/карты", "тип платёжной системы",
        "список платежей" (MutableMap<Date, Int>) и соответствующие
        функции для расчёта суммы платежей по этому счёту/карте
        за сутки и за месяц;
        Передавать в функцию transferCommission объект класса
        и в ней использовать свойства и функции объекта
        Но для упрощения будем считать максимальную сумму перевода
        по карте не за сутки, а за одну операцию
     */
    println("Комиссия составит ${transferCommission(400_00, "Mastercard", 75_000_00)} копеек")
}

fun transferCommission(amount: Int, typeAccount: String = "VK Pay", sumAmountsLetter: Int = 0) = when (typeAccount) {
    "VK Pay" ->
        if (LIMIT_SIMPLE_TRANSACTION_VK < amount || LIMIT_SUM_MONTH_TRANSACTION_VK < sumAmountsLetter + amount)
            throw Exception("Превышены лимиты по данной платёжной системе")
        else 0
    "Visa", "Мир" ->
        if (LIMIT_SIMPLE_TRANSACTION < amount || LIMIT_SUM_MONTH_TRANSACTION < sumAmountsLetter + amount)
            throw Exception("Превышены лимиты по этой карте")
        else
            if (amount * COMMISSION_PERCENTAGE_VISA_MIR > COMMISSION_MIN_TAX_VISA_MIR)
                (amount * COMMISSION_PERCENTAGE_VISA_MIR).toInt()
            else COMMISSION_MIN_TAX_VISA_MIR
    "Mastercard", "Maestro" ->
        if (LIMIT_SIMPLE_TRANSACTION < amount || LIMIT_SUM_MONTH_TRANSACTION < sumAmountsLetter + amount)
            throw Exception("Превышены лимиты по этой карте")
        else
            if (amount > SINGLE_PAYMENT_BORDER_MASTERCARD_MAESTRO && (sumAmountsLetter + amount) <= TOTAL_PAYMENT_BORDER_MASTERCARD_MAESTRO) 0
            else if (sumAmountsLetter + amount > TOTAL_PAYMENT_BORDER_MASTERCARD_MAESTRO)
                ((sumAmountsLetter + amount - TOTAL_PAYMENT_BORDER_MASTERCARD_MAESTRO) * COMMISSION_PERCENTAGE_MASTERCARD_MAESTRO).toInt() + COMMISSION_ADDITIONAL_TAX_MASTERCARD_MAESTRO
            else (amount * COMMISSION_PERCENTAGE_MASTERCARD_MAESTRO).toInt() + COMMISSION_ADDITIONAL_TAX_MASTERCARD_MAESTRO
    else -> throw Exception("Платёжная система не зарегистрирована") //вернуть 0 было бы менее логически правильно
}
