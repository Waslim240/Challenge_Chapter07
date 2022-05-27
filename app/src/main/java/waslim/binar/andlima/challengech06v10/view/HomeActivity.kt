package waslim.binar.andlima.challengech06v10.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_profile.*
import waslim.binar.andlima.challengech06v10.R
import waslim.binar.andlima.challengech06v10.adapter.AdapterFilm
import waslim.binar.andlima.challengech06v10.datastore.DataUserManager
import waslim.binar.andlima.challengech06v10.viewmodel.ViewModelFilm

@SuppressLint("NotifyDataSetChanged", "SetTextI18n")
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var dataUserManager: DataUserManager
    private var doubleBackToExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getDataFilm()
        goToFavorite()
        goToProfile()
        getDataStore()
        doubleBackExit()
    }

// ===================================== GET DATA USERNAME ========================================//
    private fun getDataStore(){
        dataUserManager = DataUserManager(this)

        dataUserManager.username.asLiveData().observe(this){
            welcome_username.text = "Welcome, $it"
        }
    }



// ===================================== MENAMPILKAN LIST FILM =====================================//
    private fun getDataFilm(){
        val filmAdapter = AdapterFilm(){
            val pdh = Intent(this, DetailActivity::class.java)
            pdh.putExtra("detailfilm", it)
            startActivity(pdh)
        }

        rvFilm.layoutManager = LinearLayoutManager(this)
        rvFilm.adapter = filmAdapter
        val viewModel = ViewModelProvider(this)[ViewModelFilm::class.java]
        viewModel.film.observe(this){
            if (it.isNotEmpty()){
                filmAdapter.setDataFilm(it)
                filmAdapter.notifyDataSetChanged()
            }
        }
    }


// ================================================ GO TO PROFILE ==================================//
    private fun goToProfile(){
        account.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }


// ================================================ GO TO FAVORITE ==================================//
    private fun goToFavorite(){
        favorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
            finish()
        }
    }

    private fun doubleBackExit() {
        onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    if (doubleBackToExit) {
                        finish()
                    } else {
                        doubleBackToExit = true
                        Toast.makeText(this@HomeActivity, "Tekan lagi untuk kembali", Toast.LENGTH_SHORT).show()

                        Handler(Looper.getMainLooper()).postDelayed(Runnable {
                            kotlin.run {
                                doubleBackToExit = false
                            }
                        }, 2000)
                    }
                }
            })
    }


}