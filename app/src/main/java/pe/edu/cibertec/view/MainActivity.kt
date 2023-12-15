package pe.edu.cibertec.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Toast

import pe.edu.cibertec.databinding.ActivityMainBinding
import pe.edu.cibertec.viewModel.MainViewModel
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope

import kotlinx.coroutines.launch

import pe.edu.cibertec.dto.LoginReq
import pe.edu.cibertec.util.localstorage.GlobalApp


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnGetInto.setOnClickListener {
                lifecycleScope.launch {
                    val res = viewModel.loginUser(
                        LoginReq(
                            usernameLogin = etUsername.text.toString(),
                            passwordLogin = etPassword.text.toString()
                        )
                    )

                    if(res.err){
                        Toast.makeText(this@MainActivity, res.msg, Toast.LENGTH_LONG).show()
                    }else{
                        val intent = Intent(this@MainActivity, MenuActivity::class.java)
                        startActivity(intent)
                    }

                }
            }
        }

        viewModel.state.observe(this){
            binding.apply {
                pbLogin.visibility = if(it.loading)  View.VISIBLE else View.GONE

                if(GlobalApp.prefs.getIsAuth()){
                    startActivity(Intent(this@MainActivity, MenuActivity::class.java))
                }
            }

        }


    }

    override fun onResume() {
        super.onResume()

        isInForeground = true
    }

    override fun onPause() {
        super.onPause()

        isInForeground = false
    }

    companion object{
        private var isInForeground = false

        fun isInForeground(): Boolean {
            return isInForeground
        }
    }
}