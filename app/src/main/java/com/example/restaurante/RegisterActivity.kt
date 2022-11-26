package com.example.restaurante

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private var edName: EditText? = null
    private var edLastName: EditText? = null
    private var edNit: EditText? = null
    private var edEmail: EditText? = null
    private var Registerpass: EditText? = null
    private var edTel: EditText? = null
    private var chb_policy: CheckBox? = null

    private val text_Pattern: Pattern = Pattern.compile(
        "[a-zA-Z]*"
    )
    private val patternNumber: Pattern = Pattern.compile(
        "[0-9]*"
    )
    private val password_Pattern: Pattern = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{8,}" +
                "$"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        edName = findViewById(R.id.edName)
        edLastName = findViewById(R.id.edLastName)
        edNit = findViewById(R.id.edNit)
        edEmail = findViewById(R.id.edEmail)
        Registerpass = findViewById(R.id.Registerpass)
        edTel = findViewById(R.id.ed_Tel)
        chb_policy = findViewById(R.id.chb_policy)
    }

    fun onRegister(view: View) {
        if (ValidateForm() || validateNumbers() || validatepass()) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show()
        }
    }

    private fun ValidateForm(): Boolean {
        var validate = true
        val nameInput = edName!!.text.toString()
        val lastnameInpu = edLastName!!.text.toString()
        val nitInput = edNit!!.text.toString()
        val EmailInput = edEmail!!.text.toString()
        val passwordInput = Registerpass!!.text.toString()
        val telInput = edTel!!.text.toString()
        if (!chb_policy!!.isChecked) {
            validate = false
        }
        if (TextUtils.isEmpty(edName!!.text.toString())) {
            edName!!.error = "Requerido"
            validate = false
        } else if (!text_Pattern.matcher(nameInput.replace(" ", "")).matches()) {
            edName!!.error = "nombre no valido"
            validate = false
        } else edName!!.error = null

        if (TextUtils.isEmpty(edLastName!!.text.toString())) {
            edLastName!!.error = "Requerido"
            validate = false
        } else if (!text_Pattern.matcher(lastnameInpu.replace(" ", "")).matches()) {
            edLastName!!.error = "apellido no valido"
            validate = false
        } else edLastName!!.error = null

        return validate
    }

    private fun validatepass(): Boolean {

        var validate = true
        val passwordInput = Registerpass!!.text.toString()
        if (TextUtils.isEmpty(Registerpass!!.text.toString())) {
            Registerpass!!.error = "Requerido"
            validate = false
        } else if (!password_Pattern.matcher(passwordInput).matches()) {
            Registerpass!!.error = "la contraseña no cumple los requerimientos"
            validate = false
        } else Registerpass!!.error = null

        return validate
    }

    private fun validateNumbers(): Boolean {
        var validate = true
        val nitInput = edNit!!.text.toString()
        val telInput = edTel!!.text.toString()
        if (TextUtils.isEmpty(edNit!!.text.toString())) {
            edNit!!.error = "Requerido"
            validate = false
        } else if (!patternNumber.matcher(nitInput).matches()) {
            edNit!!.error = "Numero invalido"
            validate = false
        } else edNit!!.error = null

        if (TextUtils.isEmpty(edTel!!.text.toString())) {
            edTel!!.error = "Requerido"
            validate = false
        } else if (!patternNumber.matcher(telInput).matches()) {
            edNit!!.error = "Numero invalido"
            validate = false
        }
        return validate
    }
}

