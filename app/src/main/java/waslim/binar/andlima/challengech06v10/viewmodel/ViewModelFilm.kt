package waslim.binar.andlima.challengech06v10.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import waslim.binar.andlima.challengech06v10.model.datafilm.DataFilmItem
import waslim.binar.andlima.challengech06v10.network.ApiService
import javax.inject.Inject

@HiltViewModel
class ViewModelFilm @Inject constructor(apiService: ApiService) : ViewModel() {

    private val filmLiveData = MutableLiveData<List<DataFilmItem>>()
    val film : LiveData<List<DataFilmItem>> = filmLiveData

    init {
        viewModelScope.launch {
            val dataFilm = apiService.getAllFilm()
            filmLiveData.value = dataFilm
        }
    }

}