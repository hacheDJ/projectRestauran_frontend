package pe.edu.cibertec.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.processNextEventInCurrentThread
import kotlinx.coroutines.withContext
import pe.edu.cibertec.dto.ConfirmOrderReq
import pe.edu.cibertec.dto.DetailOrderReq
import pe.edu.cibertec.dto.OrderReq
import pe.edu.cibertec.model.DetailOrder
import pe.edu.cibertec.model.Order
import pe.edu.cibertec.model.PreOrder
import pe.edu.cibertec.repository.OrderRepository
import pe.edu.cibertec.util.db.LocalDataBase

class ListPreOrdersViewModel(application: Application): ViewModel() {
    private val _state = MutableLiveData(ListPreOrdersUiState())
    val state: LiveData<ListPreOrdersUiState> get() = _state
    val appContext = application

    data class ListPreOrdersUiState(
        var loading: Boolean = false,
        var lstPreOrders: List<PreOrder> = emptyList()
    )

    init {
        showAll()
    }

    fun showAll(): List<PreOrder>{
        var allPreorders = emptyList<PreOrder>()

        viewModelScope.launch{
            _state.value = _state.value?.copy(loading = true)

             allPreorders = withContext(Dispatchers.IO){
                LocalDataBase.getInstance(appContext).preorderDAO().getAll()
            }

            _state.value = _state.value?.copy(lstPreOrders = allPreorders)
            _state.value = _state.value?.copy(loading = false)
        }

        return allPreorders
    }

    fun incrementQuantity(preOrder: PreOrder){
        viewModelScope.launch{
            _state.value = _state.value?.copy(loading = true)

            if(preOrder.quantity in 1..10){
                val increace = preOrder.quantity + 1

                preOrder.quantity = increace
            }

            withContext(Dispatchers.IO){
                LocalDataBase.getInstance(appContext).preorderDAO().update(preOrder)
            }

            _state.value = _state.value?.copy(loading = false)
        }
    }

    fun decreaseQuantity(preOrder: PreOrder){
        viewModelScope.launch{
            _state.value = _state.value?.copy(loading = true)

            if(preOrder.quantity >= 2){
                val decrease = preOrder.quantity - 1

                preOrder.quantity = decrease
            }

            withContext(Dispatchers.IO){
                LocalDataBase.getInstance(appContext).preorderDAO().update(preOrder)

            }
            //_state.value = _state.value?.copy(lstPreOrders = showAll())

            _state.value = _state.value?.copy(loading = false)
        }
    }

    fun remove(preOrder: PreOrder){
        viewModelScope.launch{
            _state.value = _state.value?.copy(loading = true)

            withContext(Dispatchers.IO){
                LocalDataBase.getInstance(appContext).preorderDAO().delete(preOrder)

            }
            _state.value = _state.value?.copy(lstPreOrders = showAll())
            _state.value = _state.value?.copy(loading = false)
        }
    }

    suspend fun confirmPreOrder(confirmOrderReq: ConfirmOrderReq): String{
        var msg = ""
        var ok = false

        viewModelScope.launch{
            _state.value = _state.value?.copy(loading = true)

            val response = withContext(Dispatchers.IO){
                OrderRepository.build().register(confirmOrderReq)
            }

            if(response.isSuccessful){
                val resBody = response.body()

                resBody?.let {
                    if(it.err){
                        msg = it.msg
                        ok = false
                    }else{
                        msg = it.msg
                        ok = true
                        withContext(Dispatchers.IO){
                            LocalDataBase.getInstance(appContext).preorderDAO().deleteAll()
                        }
                    }
                }
            }

            if(ok) _state.value = _state.value?.copy(lstPreOrders = emptyList())

            _state.value = _state.value?.copy(loading = false)
        }.join()

        return msg
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListPreOrdersViewModel::class.java)) {

                return ListPreOrdersViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}