import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.fkn_kotlin_project.MainActivity
import com.example.fkn_kotlin_project.generateRandomLogNormal
import org.junit.Assert.*
import org.junit.Test
import kotlin.math.*

class MainActivityTest {

    @Test
    fun testGenerateRandomLogNormal() {
        val mu = 1.0
        val sigmaSquared = 0.5

        // Генерация нескольких значений для проверки
        val generatedValues = List(1000) { generateRandomLogNormal(mu, sigmaSquared) }

        // Проверка, что все значения положительные (что характерно для логнормального распределения)
        assertTrue(generatedValues.all { it > 0 })

        // Проверка средней и дисперсии
        val mean = generatedValues.average()
        val variance = generatedValues.map { it * it }.average() - mean * mean

        val expectedMean = exp(mu + sigmaSquared / 2)
        val expectedVariance = (exp(sigmaSquared) - 1) * exp(2 * mu + sigmaSquared)

        assertEquals(expectedMean, mean, expectedMean * 0.1)  // Допускаем 10% отклонение
        assertEquals(
            expectedVariance,
            variance,
            expectedVariance * 0.1
        )  // Допускаем 10% отклонение
    }

    @Test
    fun testGenerateRandomLogNormalBounds() {
        val mu = 1.0
        val sigmaSquared = 0.5

        // Проверка, что значения не выходят за допустимые границы для разумных mu и sigmaSquared
        repeat(1000) {
            val value = generateRandomLogNormal(mu, sigmaSquared)
            assertTrue(value > 0)  // Логнормальное распределение не может быть отрицательным
        }
    }

    // Мок классы для тестирования UI
    class MockEditText(var textValue: String) : EditText(null) {
        override fun getText(): Editable = Editable.Factory.getInstance().newEditable(textValue)
    }

    class MockTextView : TextView(null) {
        var textValue: String = ""
        override fun setText(text: CharSequence?, type: BufferType?) {
            textValue = text.toString()
        }

        override fun getText(): CharSequence = textValue
    }

    class MockButton : Button(null) {
        private var clickListener: OnClickListener? = null

        override fun setOnClickListener(l: OnClickListener?) {
            clickListener = l
        }
    }
}
