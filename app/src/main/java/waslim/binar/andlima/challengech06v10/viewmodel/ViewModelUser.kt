package waslim.binar.andlima.challengech06v10.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import waslim.binar.andlima.challengech06v10.model.datauser.DataUserResponseItem
import waslim.binar.andlima.challengech06v10.model.datauser.PostRequest
import waslim.binar.andlima.challengech06v10.model.datauser.PutRequest
import waslim.binar.andlima.challengech06v10.network.ApiClient
import waslim.binar.andlima.challengech06v10.network.ApiService
import javax.inject.Inject

@HiltViewModel
class ViewModelUser @Inject constructor(apiService: ApiService) : ViewModel(){

    private val userLogin = MutableLiveData<List<DataUserResponseItem>>()

    val login : LiveData<List<DataUserResponseItem>> = userLogin

    init {
        viewModelScope.launch {
            val dataLogin = apiService.getDataUser()

            userLogin.value = dataLogin
        }
    }


    var liveDataUserRegister : MutableLiveData<DataUserResponseItem?> = MutableLiveData()
    var liveDataUserUpdate : MutableLiveData<List<DataUserResponseItem>?> = MutableLiveData()


    fun getLiveRegister() : MutableLiveData<DataUserResponseItem?> {
        return liveDataUserRegister
    }

    fun getLiveUpdate() : MutableLiveData<List<DataUserResponseItem>?> {
        return liveDataUserUpdate
    }


    fun makeApiRegister(username : String, email : String, password : String){
        ApiClient.instance.postDataUser(PostRequest(email, password, username))
            .enqueue(object : Callback<DataUserResponseItem>{
                override fun onResponse(
                    call: Call<DataUserResponseItem>,
                    response: Response<DataUserResponseItem>
                ) {
                    if (response.isSuccessful){
                        liveDataUserRegister.postValue(response.body())
                    } else {
                        liveDataUserRegister.postValue(null)
                    }
                }

                override fun onFailure(call: Call<DataUserResponseItem>, t: Throwable) {
                    liveDataUserRegister.postValue(null)
                }

            })
    }


    fun makeApiUpdate(id : String, address : String, dateOfBirth : String, fullName : String, image : String, username : String){
        ApiClient.instance.updateDataUser(id, PutRequest(address, dateOfBirth, fullName, image, username))
            .enqueue(object : Callback<List<DataUserResponseItem>>{
                override fun onResponse(
                    call: Call<List<DataUserResponseItem>>,
                    response: Response<List<DataUserResponseItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataUserUpdate.postValue(response.body())
                    } else {
                        liveDataUserUpdate.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<DataUserResponseItem>>, t: Throwable) {
                    liveDataUserUpdate.postValue(null)
                }

            })
    }

}