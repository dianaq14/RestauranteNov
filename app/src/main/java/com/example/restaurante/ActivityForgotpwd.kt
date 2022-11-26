package com.example.restaurante

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

class ActivityForgotpwd : AppCompatActivity() {

    private var ed_checkCode: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpwd)

        ed_checkCode = findViewById(R.id.ed_checkCode)
    }

    fun onSendcode(view: View) {

        val emailsend = "sergivaqx@gmail.com"
        val emailsubject = "Recovery Code"
        val emailbody = "Hi,\n" +
                "The code to recover you account is NHK2022"

        // define Intent object with action attribute as ACTION_SEND
        val intent = Intent(Intent.ACTION_SEND)

        // add three fields to intent using putExtra function
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailsend))
        intent.putExtra(Intent.EXTRA_SUBJECT, emailsubject)
        intent.putExtra(Intent.EXTRA_TEXT, emailbody)

        // set type of intent
        intent.type = "message/rfc822"

        // startActivity with intent with chooser as Email client using createChooser function
        startActivity(Intent.createChooser(intent, "Choose an Email client :"))


    }

    fun onCheckcode(view: View) {
        val messagecode = getString(R.string.messagecode)
        var code: String = ed_checkCode!!.text.toString()

        if(code=="NHK2022"){
            val intent = Intent(this,activity_changepass::class.java)
            startActivity(intent)
        }else{
            val dialog= AlertDialog.Builder(this).setTitle("ERROR")
                .setMessage(messagecode).create().show()
        }

    }
}