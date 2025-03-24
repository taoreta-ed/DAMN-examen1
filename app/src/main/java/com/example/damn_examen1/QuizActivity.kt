package com.example.damn_examen1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.damn_examen1.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private var currentQuestionIndex = 0
    private var score = 0

    private val questions = listOf(
        Question(
            "¿Cuál es el valor decimal del número binario 1011?",
            listOf("9", "11", "13", "15"),
            1 // Índice de la respuesta correcta (11)
        ),
        Question(
            "¿Cómo se representa el número decimal 5 en binario?",
            listOf("100", "101", "110", "111"),
            1 // Respuesta correcta: 101
        ),
        Question(
            "¿Qué valor decimal representa el binario 1111?",
            listOf("15", "16", "17", "18"),
            0 // Respuesta correcta: 15
        ),
        Question(
            "¿Cuántos bits hay en un byte?",
            listOf("4", "8", "16", "32"),
            1 // Respuesta correcta: 8
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showQuestion(currentQuestionIndex)

        binding.submitButton.setOnClickListener {
            checkAnswer()
        }

        binding.nextButton.setOnClickListener {
            nextQuestion()
        }

        // Botón para regresar al menú principal
        binding.backButton.setOnClickListener {
            finish() // Cierra esta actividad y regresa a la anterior
        }
    }

    private fun showQuestion(index: Int) {
        if (index < questions.size) {
            val question = questions[index]
            binding.questionText.text = question.text
            binding.answerGroup.clearCheck()

            // Actualizar las opciones de respuesta
            (binding.answerGroup.getChildAt(0) as RadioButton).text = question.options[0]
            (binding.answerGroup.getChildAt(1) as RadioButton).text = question.options[1]
            (binding.answerGroup.getChildAt(2) as RadioButton).text = question.options[2]
            (binding.answerGroup.getChildAt(3) as RadioButton).text = question.options[3]

            binding.feedbackText.visibility = View.GONE
            binding.nextButton.visibility = View.GONE
            binding.submitButton.visibility = View.VISIBLE

            // Actualizar contador de preguntas
            binding.progressText.text = "Pregunta ${index + 1} de ${questions.size}"
        } else {
            // Fin del quiz
            showFinalScore()
        }
    }

    private fun checkAnswer() {
        val selectedId = binding.answerGroup.checkedRadioButtonId

        if (selectedId != -1) {
            val selectedIndex = when (selectedId) {
                R.id.answer_a -> 0
                R.id.answer_b -> 1
                R.id.answer_c -> 2
                R.id.answer_d -> 3
                else -> -1
            }

            val isCorrect = selectedIndex == questions[currentQuestionIndex].correctAnswer

            if (isCorrect) {
                score++
                binding.feedbackText.text = "¡Correcto!"
                binding.feedbackText.setTextColor(getColor(android.R.color.holo_green_dark))
            } else {
                binding.feedbackText.text = "Incorrecto. La respuesta correcta es: ${
                    questions[currentQuestionIndex].options[questions[currentQuestionIndex].correctAnswer]
                }"
                binding.feedbackText.setTextColor(getColor(android.R.color.holo_red_dark))
            }

            binding.feedbackText.visibility = View.VISIBLE
            binding.submitButton.visibility = View.GONE
            binding.nextButton.visibility = View.VISIBLE
        }
    }

    private fun nextQuestion() {
        currentQuestionIndex++
        showQuestion(currentQuestionIndex)
    }

    private fun showFinalScore() {
        binding.quizTitle.text = "¡Quiz completado!"
        binding.questionText.text = "Puntuación final: $score/${questions.size}"
        binding.answerGroup.visibility = View.GONE
        binding.submitButton.visibility = View.GONE
        binding.nextButton.visibility = View.GONE
        binding.feedbackText.visibility = View.GONE
        binding.backButton.visibility = View.VISIBLE

        // Mostrar mensaje según el puntaje
        val message = when {
            score == questions.size -> "¡Excelente! Dominas el sistema binario"
            score >= questions.size / 2 -> "¡Buen trabajo! Sigue practicando"
            else -> "Sigue estudiando, ¡puedes mejorar!"
        }
        binding.questionText.text = "$message\n\nPuntuación: $score/${questions.size}"
    }

    data class Question(
        val text: String,
        val options: List<String>,
        val correctAnswer: Int // Índice de la respuesta correcta
    )
}