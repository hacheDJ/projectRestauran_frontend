package pe.edu.cibertec.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.cibertec.dto.AddPlateRes
import pe.edu.cibertec.dto.OrderByStatePendingRes
import pe.edu.cibertec.repository.DetailOrderRepository
import pe.edu.cibertec.repository.OrderRepository
import pe.edu.cibertec.util.localstorage.GlobalApp

class ListOrdersViewModel: ViewModel() {
    private val _state = MutableLiveData(ListOrdersUiState())
    val state: LiveData<ListOrdersUiState> get() = _state

    data class ListOrdersUiState(
        var loading: Boolean = false,
        val isAuth: Boolean = false,
        var lstOrders: List<OrderByStatePendingRes> = emptyList()
    )

    init {
        if(GlobalApp.prefs.getActiveUser() != null){
            _state.value = _state.value?.copy(isAuth = true)
        }else{
            _state.value = _state.value?.copy(isAuth = false)
        }

        if(GlobalApp.prefs.getActiveUser()!!.role == "waiter") showOrdersByWaiter()
        if(GlobalApp.prefs.getActiveUser()!!.role == "chef") showOrdersByStatePending()
    }

    private fun showOrdersByStatePending(): List<OrderByStatePendingRes>{
        var lstOrder = emptyList<OrderByStatePendingRes>()

        viewModelScope.launch {
            _state.value = _state.value?.copy(loading = true)

            val response = withContext(Dispatchers.IO){
                OrderRepository.build().getOrdersByStatePending()
            }

            if(response.isSuccessful){
                lstOrder = response.body()!!
                _state.value = _state.value?.copy(lstOrders = lstOrder)
            }


            _state.value = _state.value?.copy(loading = false)
        }

        return lstOrder
    }

    private fun showOrdersByWaiter(): List<OrderByStatePendingRes>{
        var lstOrder = emptyList<OrderByStatePendingRes>()



        return lstOrder
    }

    suspend fun updateState(idDetailOrder: Int, nameState: String): String {
        var msg = ""

        viewModelScope.launch {
            _state.value = _state.value?.copy(loading = true)

            val response = withContext(Dispatchers.IO){
                DetailOrderRepository.build().editStateDelivered(idDetailOrder, nameState)
            }

            if(response.isSuccessful){
                val resEdit = response.body()

                resEdit?.let {
                    if(it.err){
                        println("ER---> "+it)
                        msg = it.msg
                        _state.value = _state.value?.copy(loading = false)
                    }else{
                        println("OK---> "+it)
                        msg = it.msg
                        _state.value = _state.value?.copy(lstOrders = showOrdersByStatePending())
                        _state.value = _state.value?.copy(loading = false)
                    }
                }

            }

        }.join()

        return msg
    }


}