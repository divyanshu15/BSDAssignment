package com.divyanshu.assignmentbsd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn=findViewById<AppCompatButton>(R.id.loginbtn)
        val username = findViewById<TextInputEditText>(R.id.usernameET)
        val userpass = findViewById<TextInputEditText>(R.id.userpassET)

        btn.setOnClickListener{
            var uname : String = username.text.toString()
            var upass : String = userpass.text.toString()
            if (uname.isEmpty()||upass.isEmpty()){
                Toast.makeText(applicationContext,"Please fill all details!", Toast.LENGTH_SHORT).show()
            }
            else if (uname== "Test" && upass== "Pass@123"){
                val intent = Intent(this,AllData::class.java)
                startActivity(intent)

            }
            else{
                Toast.makeText(applicationContext,"Invalid input", Toast.LENGTH_SHORT).show()
            }
        }
    }
}