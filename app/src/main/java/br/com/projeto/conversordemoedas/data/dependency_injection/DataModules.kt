package br.com.projeto.conversordemoedas.data.dependency_injection

import android.util.Log
import br.com.projeto.conversordemoedas.data.db.ExchangeDataBase
import br.com.projeto.conversordemoedas.data.repository.CoinRepository
import br.com.projeto.conversordemoedas.data.repository.CoinRepositoryImp
import br.com.projeto.conversordemoedas.data.services.AwesomeService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModules {
    private const val HTTP_TG = "OkHttp"
    private const val BASE_URL = "https://economia.awesomeapi.com.br/"

    fun load() {
        loadKoinModules(networkModule()
                + repositoryModule()
                + dataBaseModule()
        )
    }

    private fun networkModule(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.d(HTTP_TG, "httpLogginInterceptor : $it",)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createService<AwesomeService>(get(), get())
            }
        }
    }

    private fun dataBaseModule(): Module {
        return module {
            single { ExchangeDataBase.getInstance(androidContext()) }
        }
    }

    private fun repositoryModule(): Module {
       return module{
           single<CoinRepository> {CoinRepositoryImp(get(), get())}
       }
    }

    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(factory)
            .build()
            .create(T::class.java)
    }
}