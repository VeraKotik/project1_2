package com.example.project1_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: View
    private var prevButton: View? = null

    private lateinit var questionTextView: TextView


    private val kostil = arrayOf(false, false, false, false, false, false)

    var countTrueAnswer = 0

    private val quizViewModel: QuizViewModel by
    lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate(Bundle?) called")
        Log.d(TAG, "Got a QuizViewModel:$quizViewModel")

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { onAnswerButtonClick(true) }
        falseButton.setOnClickListener { onAnswerButtonClick(false) }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            toggleButtons(!kostil[currentIndex])
        }

        prevButton?.setOnClickListener {
           quizViewModel.moveToPrev()
            updateQuestion()
            toggleButtons(!kostil[currentIndex])
        }

        updateQuestion()
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onStop() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun toggleButtons(isEnabled: Boolean) {
        trueButton.isEnabled = isEnabled
        falseButton.isEnabled = isEnabled
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun countingTrueAnswers(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        if (userAnswer == correctAnswer) {
            countTrueAnswer++
        }
    }

    private fun onAnswerButtonClick(isTrueButton: Boolean) {
        checkAnswer(isTrueButton)
        countingTrueAnswers(isTrueButton)
        kostil[currentIndex] = true
        toggleButtons(isEnabled = false)
        if (kostil.all { it }) {
            countTrueAnswer = countTrueAnswer * 100 / 6
            Toast.makeText(applicationContext, countTrueAnswer.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
