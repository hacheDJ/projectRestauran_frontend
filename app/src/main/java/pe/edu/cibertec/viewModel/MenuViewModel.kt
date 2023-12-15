package pe.edu.cibertec.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.cibertec.util.localstorage.GlobalApp

class MenuViewModel: ViewModel() {
    private val _state = MutableLiveData(MenuUiState())
    val state: LiveData<MenuUiState> get() = _state

    data class MenuUiState(
        var loading: Boolean = false,
        val isAuth: Boolean = false
    )

    init {
        if(GlobalApp.prefs.getActiveUser() != null){
            _state.value = _state.value?.copy(isAuth = true)
        }else{
            _state.value = _state.value?.copy(isAuth = false)
        }
    }
}