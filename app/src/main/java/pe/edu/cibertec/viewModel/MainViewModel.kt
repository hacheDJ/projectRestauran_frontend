package pe.edu.cibertec.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.cibertec.dto.LoginReq
import pe.edu.cibertec.dto.LoginRes
import pe.edu.cibertec.model.User
import pe.edu.cibertec.repository.UserRepository
import pe.edu.cibertec.util.localstorage.GlobalApp

class MainViewModel: ViewModel() {
//    private val _state = MutableStateFlow(MainUiState())
//    val state: StateFlow<MainUiState> = _state.asStateFlow()
    private val _state = MutableLiveData(MainUiState())
    val state: LiveData<MainUiState> get() = _state

    data class MainUiState(
        var loading: Boolean = false
    )


    suspend  fun loginUser(data: LoginReq): LoginRes{
        var resLogin: LoginRes? = null
        viewModelScope.launch {
            _state.value = _state.value?.copy(loading = true)
            println("params -------> "+data.usernameLogin+" "+data.passwordLogin)
            val response = withContext(Dispatchers.IO){
                UserRepository.build().login(data)
            }

            if(response.isSuccessful){
                resLogin = response.body()!!
                println("obj -------> "+resLogin)
                resLogin?.let {res ->
                    if(res.err){
                        _state.value = _state.value?.copy(loading = false)
                    }else{
                        GlobalApp.prefs.setIsAuth(true)
                        GlobalApp.prefs.setActiveUser(res.data)
                        GlobalApp.prefs.setJWT(res.token)
                        _state.value = _state.value?.copy(loading = false)
                    }
                }

            }

        }.join()

        return resLogin!!
    }





}