package com.example.restaurante

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private val GOOGLE_SING_IN = 100
    private var ed_textUsername :EditText? = null
    private var ed_textPassword :EditText? = null
    private var authLayout :LinearLayout?=null
    private var btn_google :SignInButton?= null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        ed_textUsername =findViewById(R.id.ed_textUsername)
        ed_textPassword =findViewById(R.id.ed_textPassword)
        authLayout =findViewById(R.id.authLayout)

        btn_google = findViewById(R.id.btn_google)
        session()
        onLoginGoogle()


    }

    fun onLogin(botonLogin: View) {
        val messagepassword=getString(R.string.messagepassword)
        val messageusername=getString(R.string.messageusername)
        var username: String= ed_textUsername!!.text.toString()

        if(username=="correo@gmail.com")
        {
            if(ed_textPassword!!.text.toString()=="abc123")
            {
                val negativeButton={_:DialogInterface,_:Int->}
                val positiveButton={dialog:DialogInterface, which:Int ->
                    val intent = Intent(this,WelcomeActivity::class.java)
                    startActivity(intent)
                }
                val dialog= AlertDialog.Builder(this).setTitle("¡WELCOME!").setMessage("User: "+ username)
                    .setPositiveButton("OK", positiveButton)
                    .setNegativeButton("CANCEL", negativeButton)
                    .create().show()
            }
            else
            {
                val dialog= AlertDialog.Builder(this).setTitle("ERROR")
                    .setMessage(messagepassword).create().show()
            }
        }
        else
        {
            Toast.makeText(this,messageusername,Toast.LENGTH_LONG).show()
        }

    }

    fun onRegister(view: View) {
        val intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }

    fun onLoginGoogle() {
        btn_google!!.setOnClickListener{
            val googleleConf :GoogleSignInOptions = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient : GoogleSignInClient = GoogleSignIn.getClient(this,googleleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent,GOOGLE_SING_IN)
        }
    }

    fun onLoginFacebook(view: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://es-la.facebook.com/"))
        startActivity(browserIntent)
    }

    fun onLoginInstagram(view: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"))
        startActivity(browserIntent)
    }

    fun onForgotpwd(view: View) {
        val intent = Intent(this,ActivityForgotpwd::class.java)
        startActivity(intent)
    }
    fun onForgotusr(view: View) {
        val intent = Intent(this,ActivityForgotuser::class.java)
        startActivity(intent)
    }

    fun onRegistrer_email(view: View) {
        title="Autentification"
        if (ed_textUsername!!.text.isNotEmpty() && ed_textPassword!!.text.isNotEmpty()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                ed_textUsername!!.text.toString(),
                ed_textPassword!!.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this,"Correcto", Toast.LENGTH_LONG).show()
                    showHome(it.result?.user?.email?:"",ProviderType.BASIC)
                }else{
                    showAlert()
                }
            }

        }

    }

    private fun showAlert(){
        val builder =AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog=builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider : ProviderType){
        val homeIntent =Intent(this,WelcomeActivity::class.java)
            .apply{
                putExtra("email", email)
                putExtra("provider",provider.name)
            }
        startActivity(homeIntent)
    }

    fun onLogin_email(view: View) {
        title="Autentification"
        if (ed_textUsername!!.text.isNotEmpty() && ed_textPassword!!.text.isNotEmpty()){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                ed_textUsername!!.text.toString(),
                ed_textPassword!!.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful){
                    // Toast.makeText(this,"Correcto", Toast.LENGTH_LONG).show()
                    showHome(it.result?.user?.email?:"",ProviderType.BASIC)
                }else{
                    showAlert()
                }
            }

        }

    }

    override fun onStart() {
        super.onStart()
        authLayout!!.visibility=View.VISIBLE
    }

    private fun session(){
        val prefs : SharedPreferences=getSharedPreferences(
            getString(R.string.prefs_file),
            Context.MODE_PRIVATE
        )
        val email :String? = prefs.getString("email",null)
        val provider :String? = prefs.getString("provider",null)
        if (email != null && provider != null){
            authLayout!!.visibility=View.INVISIBLE
            showHome(email, ProviderType.valueOf(provider))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val prefs = getSharedPreferences(
            getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()
        FirebaseAuth.getInstance().signOut()
        onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== GOOGLE_SING_IN){
            val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account : GoogleSignInAccount? = task.getResult(ApiException::class.java)
                if (account != null){
                    val credential : AuthCredential = GoogleAuthProvider.getCredential(account.idToken,null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{
                        if (it.isSuccessful){
                            showHome(account.email?:" ",ProviderType.GOOGLE)
                        }
                        else{
                            showAlert()
                        }
                    }
                }
            }catch(e: ApiException){
                val builder= AlertDialog.Builder(this)
                builder.setTitle("Error")
                builder.setMessage(e.toString())
                builder.setPositiveButton("Aceptar",null)

                val dialog : AlertDialog = builder.create()
                dialog.show()
            }
        }

    }
}
