package com.shristi.quiz_app

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity(), OnCheckedChangeListener {

    private var answer1: String = "final"
    private var answer2 = mutableListOf("A variable that cannot change, read-only")

    private var userAnswer1: String = ""
    private var userAnswer2 = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Question 1: All classes in Kotlin are by default?"
        radioGroup1.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton1 -> userAnswer1 = "public"
                R.id.radioButton2 -> userAnswer1 = "abstract"
                R.id.radioButton3 -> userAnswer1 = "final"
            }
        }

        // Question 2 What is an immutable variable?
        checkBox1.setOnCheckedChangeListener(this)
        checkBox2.setOnCheckedChangeListener(this)
        checkBox3.setOnCheckedChangeListener(this)

        submitBtn.setOnClickListener {

            if (userAnswer1.isEmpty() || userAnswer2.isEmpty()) {
                Toast.makeText(this, "Please answer all the questions first !", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            var score = 0
            if (userAnswer1 == answer1) {
                score += 50
            }
            if (userAnswer2.containsAll(answer2)) {
                score += 50
            }
            // current date and time in format
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat("MM/dd/yyyy, hh:mm:ss a", Locale.getDefault())
            val currentDateTime = formatter.format(date)


            val message = "You submitted on $currentDateTime. You achieved $score%"

            val alertTitle =
                if (score > 0) "Congratulations!!" else "Sorry ! Better luck next time!"
            AlertDialog.Builder(this)
                .setTitle(alertTitle)
                .setMessage(message)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        //resetting both answers
        resetBtn.setOnClickListener {
            radioGroup1.clearCheck()
            resetCheckBox()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            R.id.checkBox1 -> addAnswer("A variable that cannot change, read-only", isChecked)
            R.id.checkBox2 -> addAnswer("A variable that can be changed", isChecked)
            R.id.checkBox3 -> addAnswer("A variable used for string interpolation", isChecked)
        }
    }

    private fun addAnswer(answer: String, isChecked: Boolean) {
        if (isChecked) userAnswer2.add(answer) else userAnswer2.remove(answer)
    }

    private fun resetCheckBox() {
        checkBox1.isChecked = false
        checkBox2.isChecked = false
        checkBox3.isChecked = false
    }
}