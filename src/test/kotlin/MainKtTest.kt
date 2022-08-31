import org.junit.Test

import org.junit.Assert.*
import java.lang.Exception

class MainKtTest {

    @Test
    fun transferCommissionRegular() {
        val amount = 10_000_00
        val result: Int = transferCommission(amount)
        assertEquals(0, result)
    }

    @Test
    fun transferCommissionRegularMir() {
        val amount = 14_000_00
        val typeAccount = "VK Pay"
        val sumAmountsLetter = 20_000_00
        val result: Int = transferCommission(amount, typeAccount, sumAmountsLetter)
        assertEquals(0, result)
    }

    @Test
    fun transferCommissionVkLimitExceeded() {
        val amount = 24_000_00
        val typeAccount = "VK Pay"
        val sumAmountsLetter = 20_000_00
        try {
            transferCommission(amount, typeAccount, sumAmountsLetter)
        } catch (e: Exception) {
            assertEquals(e.message, Exception("Превышены лимиты по данной платёжной системе").message)
        }
    }

    @Test
    fun transferCommissionVisaLimitExceeded() {
        val amount = 34_500_00
        val typeAccount = "Visa"
        val sumAmountsLetter = 580_000_00
        try {
            transferCommission(amount, typeAccount, sumAmountsLetter)
        } catch (e: Exception) {
            assertEquals(e.message, Exception("Превышены лимиты по этой карте").message)
        }
    }

    @Test
    fun transferCommissionMaestroLimitExceeded() {
        val amount = 30_000_00
        val typeAccount = "Maestro"
        val sumAmountsLetter = 580_000_00
        try {
            transferCommission(amount, typeAccount, sumAmountsLetter)
        } catch (e: Exception) {
            assertEquals(e.message, Exception("Превышены лимиты по этой карте").message)
        }
    }

    @Test
    fun transferCommissionUnRegularType() {
        val amount = 14_000_00
        val typeAccount = "VK"
        val sumAmountsLetter = 20_000_00
        try {
            transferCommission(amount, typeAccount, sumAmountsLetter)
        } catch (e: Exception) {
            assertEquals(e.message, Exception("Платёжная система не зарегистрирована").message)
        }
    }

    @Test
    fun transferCommissionVisaRegular() {
        val amount = 10_000_00
        val typeAccount = "Visa"
        val sumAmountsLetter = 0
        val result = transferCommission(amount, typeAccount, sumAmountsLetter)
        assertEquals(75_00, result)
    }

    @Test
    fun transferCommissionMirRegular() {
        val amount = 2_000_00
        val typeAccount = "Мир"
        val sumAmountsLetter = 0
        val result = transferCommission(amount, typeAccount, sumAmountsLetter)
        assertEquals(35_00, result)
    }

    @Test
    fun transferCommissionMastercardUnRegular() {
        val amount: Int = 400_00
        val typeAccount: String = "Mastercard"
        val sumAmountsLetter: Int = 75_000_00
        val result = transferCommission(amount, typeAccount, sumAmountsLetter)
        assertEquals(22_40, result)
    }

    @Test
    fun transferCommissionMastercardUnRegularSingleBorder() {
        val amount: Int = 200_00
        val typeAccount: String = "Mastercard"
        val sumAmountsLetter: Int = 70_000_00
        val result = transferCommission(amount, typeAccount, sumAmountsLetter)
        assertEquals(21_20, result)
    }

    @Test
    fun transferCommissionMastercardRegular() {
        val amount: Int = 400_00
        val typeAccount: String = "Mastercard"
        val sumAmountsLetter: Int = 70_000_00
        val result = transferCommission(amount, typeAccount, sumAmountsLetter)
        assertEquals(0, result)
    }

    @Test
    fun transferCommissionMaestroRegular() {
        val amount: Int = 10_000_00
        val typeAccount: String = "Maestro"
        val sumAmountsLetter: Int = 20_000_00
        val result = transferCommission(amount, typeAccount, sumAmountsLetter)
        assertEquals(0, result)
    }
}