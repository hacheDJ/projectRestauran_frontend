package pe.edu.cibertec.viewModel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.cibertec.model.Plate
import pe.edu.cibertec.model.PreOrder
import pe.edu.cibertec.repository.PlateRepository
import pe.edu.cibertec.util.db.LocalDataBase
import java.io.InputStream
import java.lang.Exception

class ListPlatesViewModel(application: Application): ViewModel() {
    private val _state = MutableLiveData(ListPlatesUiState())
    val state: LiveData<ListPlatesUiState> get() = _state
    val appContext = application

    data class ListPlatesUiState(
        var loading: Boolean = false,
        var lstPlates: List<Plate> = emptyList()
    )

    init {
        viewModelScope.launch {
            _state.value = _state.value?.copy(loading = true)

            val response = PlateRepository.build().getAll()

            if(response.isSuccessful){
                val resBody = response.body()

                _state.value = _state.value?.copy(lstPlates = resBody!!)
                _state.value = _state.value?.copy(loading = false)
            }

        }

    }

     suspend fun findPhoto(nameFile: String) : InputStream?{
         var inputStream: InputStream? = null
        viewModelScope.launch {
            _state.value = _state.value?.copy(loading = true)

            val response = PlateRepository.build().getPhoto(nameFile)

            if(response.isSuccessful) {
                inputStream = response.body()
            }


        }.join()

         return inputStream
    }

    suspend fun addToPreOrder(preOrder: PreOrder): String{
        try {
            viewModelScope.launch {
                _state.value = _state.value?.copy(loading = true)

                //viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        LocalDataBase.getInstance(appContext).preorderDAO().insert(preOrder)
                    }

               // }.join()

                _state.value = _state.value?.copy(loading = false)
            }.join()


        }catch (e: Exception){
            println("Error: "+e.message)
        }

        return "Se agrego el plato en preorden"
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListPlatesViewModel::class.java)) {

                return ListPlatesViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}