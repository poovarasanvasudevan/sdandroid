package com.caretech.servicefocus.activity

import android.content.Context
import android.view.View
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.BaseActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.mcxiaoke.koi.ext.onClick
import com.mcxiaoke.koi.ext.startActivity
import org.jetbrains.anko.indeterminateProgressDialog
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class LoginActivity : BaseActivity() {

    lateinit var userNameField: TextInputEditText
    lateinit var passwordField: TextInputEditText
    lateinit var loginBtn: MaterialButton
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    override fun START() {


        loginBtn.onClick {
            var error = false
            if (userNameField.text.toString().isEmpty() || userNameField.text.toString().isBlank()) {
                error = true
                userNameField.setError("Username is Requires")
            }
            if (passwordField.text.toString().isEmpty() || passwordField.text.toString().isBlank()) {
                error = true
                passwordField.setError("Password is Required")
            }

            if (!error) {
                val params = mutableMapOf<String, String>()
                params["username"] = userNameField.text.toString()
                params["password"] = passwordField.text.toString()

                val dialog = indeterminateProgressDialog("Verifying User...")
                dialog.show();

                if(userNameField.text.toString().equals("test") && passwordField.text.toString().equals("test")) {
                    startActivity<HomeActivity>()
                    finish()
                }

//                ParseCloud.callFunctionInBackground("login", params, FunctionCallback<Map<String, Any>> { mapObject, e ->
//                    if(e==null) {
//                        if (mapObject.get("error") == false) {
//                            dialog.setMessage("Logging in...")
//
//                            if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().isAuthenticated && ParseUser.getCurrentSessionToken() != null) {
//                                dialog.dismissOnShow()
//                                startActivity<HomeActivity>()
//                                finish()
//                            }
//
//                            ParseUser.logInInBackground(userNameField.text.toString(), passwordField.text.toString()) { user, e ->
//                                dialog.dismissOnShow()
//                                if (e == null) {
//                                    startActivity<HomeActivity>()
//                                    finish()
//                                } else {
//                                    toast(e.localizedMessage)
//                                }
//                            }
//                        } else {
//                            toast("Invalid Username / Password")
//                        }
//                    } else {
//                        toast(e.localizedMessage)
//                    }
//                })
            }
        }
    }

    override fun STOP() {
    }

    override fun COMMON() {
        appBarView().visibility = View.GONE

        userNameField = view().findViewById(R.id.username)
        passwordField = view().findViewById(R.id.password)
        loginBtn = view().findViewById(R.id.loginBtn)
    }

    override fun layout(): Int = R.layout.activity_login
}
