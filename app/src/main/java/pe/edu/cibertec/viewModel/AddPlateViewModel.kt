package pe.edu.cibertec.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.cibertec.dto.AddPlateReq
import pe.edu.cibertec.dto.AddPlateRes
import pe.edu.cibertec.repository.PlateRepository

class AddPlateViewModel: ViewModel() {
    private val _state = MutableLiveData(AddPlateUiState())
    val state: LiveData<AddPlateUiState> get() = _state

    data class AddPlateUiState(
        val loading: Boolean = false,
        val message: String? = null
    )

    suspend fun addPlate(addPlateReq: AddPlateReq): AddPlateRes?{
        var resAddPlate: AddPlateRes? = null

        viewModelScope.launch {
            _state.value = _state.value?.copy(loading = true)

            val response = PlateRepository.build().register(addPlateReq.namePlate, addPlateReq.descriptionPlate, addPlateReq.price, addPlateReq.photo)

            if(response.isSuccessful){
                val addPlateRes = response.body()
                resAddPlate = addPlateRes

                addPlateRes?.let {res ->
                    if(res.err){
                        _state.value = _state.value?.copy(loading = false)
                        _state.value = _state.value?.copy(message = res.msg)

                    }else{
                        _state.value = _state.value?.copy(loading = false)
                        _state.value = _state.value?.copy(message = res.msg)
                    }
                }

            }

        }.join()

        return resAddPlate
    }

}