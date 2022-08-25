val commissionPercentageVisaMir = 0.0075
val commissionMinTaxVisaMir = 35_00
val commissionPercentageMastercardMaestro = 0.006
val commissionAdditionalTaxMastercardMaestro = 20_00
val SinglePaymentBorderMastercardMaestro = 300_00
val TotalPaymentBorderMastercardMaestro = 75_000_00
fun main() {
    //Вместо рублей в качестве значений используем копейки
    val amount = 400_00
    val typeAccount = "Mastercard"
    val sumAmountsLetter = 75_000_00
    println("Комиссия составит ${transferCommission(amount, typeAccount, sumAmountsLetter)} копеек")
    println("Комиссия составит ${transferCommission(amount, typeAccount)} копеек") //sumAmountsLetter = 0
    println("Комиссия составит ${transferCommission(amount,sumAmountsLetter)} копеек") //typeAccount = "VK Pay"
    println("Комиссия составит ${transferCommission(amount)} копеек") //typeAccount = "VK Pay", sumAmountsLetter = 0
}

fun transferCommission(amount: Int, type: String): Int {
    if (type == "VK Pay") return 0 //Для VK Pay комиссия всегда 0
    if (type == "Visa" || type == "Мир") { //Вычисляем комиссию для платёжных систем Visa и Мир
        if (amount * commissionPercentageVisaMir > commissionMinTaxVisaMir)
            return (amount * commissionPercentageVisaMir).toInt()
        else return commissionMinTaxVisaMir
    }
    if (type == "Mastercard" || type == "Maestro") {  //Передадим в качестве значения прошлых платежей 0 (по умолчанию)
        return transferCommission(amount, type, 0)
    }
    return 0
}

fun transferCommission(amount: Int, type: String, sumAmountsLetter: Int): Int {
    if (type == "Visa" || type == "Мир" || type == "VK Pay") { //Для этих систем предыдущие платежи не важны
        return transferCommission(amount, type)
    }
    val sumAmounts = sumAmountsLetter + amount
    if (type == "Mastercard" || type == "Maestro") { //Вычисляем комиссию для платёжных систем Mastercard и Maestro
        if (amount > SinglePaymentBorderMastercardMaestro && sumAmounts <= TotalPaymentBorderMastercardMaestro) return 0 else {
            if (sumAmounts > TotalPaymentBorderMastercardMaestro)
                return ((sumAmounts - TotalPaymentBorderMastercardMaestro) * commissionPercentageMastercardMaestro).toInt() + commissionAdditionalTaxMastercardMaestro
            else return (amount * commissionPercentageMastercardMaestro).toInt() + commissionAdditionalTaxMastercardMaestro
        }
    }
    return 0
}

fun transferCommission(amount: Int): Int {
    //По умолчанию VK Pay, сумма предыдущих платежей 0
    return 0 //Для VK Pay комиссия всегда 0
}

fun transferCommission(amount: Int, sumAmountsLetter: Int): Int {
    //По умолчанию VK Pay
    return 0 //Для VK Pay комиссия всегда 0
}