package com.example.fkn_kotlin_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.math.*
import kotlin.random.Random


fun generateRandomLogNormal(mu: Double, sigmaSquared: Double): Double {
    /*
    Генерация двух случайных чисел из равномерного
    распределения на интервале [0, 1).
     */
    val n = Random.nextDouble()
    val m = Random.nextDouble()

    /*
    Генерация случайной величины x по правилам стандартного
    нормального распределения
    с помощью преобразования Бокса-Мюллера.
     */
    val x = sqrt(-2 * ln(n)) * cos(2 * PI * m)

    /*
    Возврат случайного числа по правилам логнормального
    распределения с заданными параметрами mu и sigmaSquared.
     */
    return exp(mu + sqrt(sigmaSquared) * x)
}


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val result = findViewById<TextView>(R.id.rundom_number_result)
        val textMu = findViewById<EditText>(R.id.mean_val)
        val textSquaredSigma = findViewById<EditText>(R.id.variance_value)
        val btn = findViewById<Button>(R.id.get_random_num)

        btn.setOnClickListener {
            val mu = textMu.text.toString().trim()
            val squaredSigma = textSquaredSigma.text.toString().trim()

            /*
            Если поле ввода mu или поле ввода sigma^2 не заполнено или
            хотя бы одно их введеных значений меньше 1,5, то нужно предупредить
            об ошибке пользователя. Иначе - считать. Выводится округленное до целых число человек,
            было бы странно, если бы Анжелика искала место, где работают 1,5 человека:).
             */
            if (mu == "" || squaredSigma ==  "")
                Toast.makeText(this, "Пожалуйста, заполните все окна.", Toast.LENGTH_LONG).show()
            else if (mu.toDouble() < 0.5 || squaredSigma.toDouble() < 0.5)
                Toast.makeText(this, "Все введеные значения должны быть больше 0,5.", Toast.LENGTH_LONG).show()
            else
                result.text = "Вам стоит выбрать компанию, в которой работает " + generateRandomLogNormal(mu.toDouble(), squaredSigma.toDouble()).roundToInt().toString() + " человек(а)."
        }
    }
}