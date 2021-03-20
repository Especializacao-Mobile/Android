package br.imaginefree.weather.features.favorite

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import br.imaginefree.weather.R
import br.imaginefree.weather.data.model.BaseResponse
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.network.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Service
            .getService()
            .getCity("Recife", "metric", "PT", "b02f5abb291a5a402a86d45e3807c357")
            .enqueue(object : Callback<BaseResponse<City>>{
                override fun onResponse(
                    call: Call<BaseResponse<City>>,
                    response: Response<BaseResponse<City>>
                ) {
                    response.body()?.list?.let {
                        it.forEach{
                            Log.d("Response:", it.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<BaseResponse<City>>, t: Throwable) {
                    t.printStackTrace()
                }

            })

    }

}