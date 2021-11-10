package com.example.project1_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: View
    private var prevButton: View? = null
    private lateinit var questionTextView: TextView
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, true),
        Question(R.string.question_americas, false),
        Question(R.string.question_asia, true)

    )
    private val kostil = arrayListOf<Boolean>(false, false, false, false, false, false)
    var countTrueAnswer = 0
    private var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            countingTrueAnswers(true)
            kostil[currentIndex] = true
            buttonLock()
            if (kostil.all { it == true }) {
                countTrueAnswer = countTrueAnswer * 100 / 6
                Toast.makeText(applicationContext, countTrueAnswer.toString(), Toast.LENGTH_LONG).show()
            }
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            countingTrueAnswers(false)
            kostil[currentIndex] = true
            buttonLock()
            if (kostil.all { it == true }) {
                countTrueAnswer = countTrueAnswer * 100 / 6
                Toast.makeText(applicationContext, countTrueAnswer.toString(), Toast.LENGTH_LONG).show()
            }

        }
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            buttonUnlock()
            if (kostil[currentIndex]) {
                trueButton.isEnabled = false
                falseButton.isEnabled = false

            }


        }
        prevButton?.setOnClickListener {
            if (currentIndex > 0)
                currentIndex = (currentIndex - 1) % questionBank.size
            updateQuestion()
            buttonUnlock()
            if (kostil[currentIndex]) {
                trueButton.isEnabled = false
                falseButton.isEnabled = false
            }


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

    private fun buttonLock() {
        trueButton.isEnabled = false
        falseButton.isEnabled = false

    }

    private fun buttonUnlock() {
        trueButton.isEnabled = true
        falseButton.isEnabled = true
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)

    }

    private fun checkAnswer(userAnswer: Boolean) {

        val correctAnswer = questionBank[currentIndex].answer
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

}