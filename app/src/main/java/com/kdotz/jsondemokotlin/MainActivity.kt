package com.kdotz.jsondemokotlin

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val task = DownloadTask(this)
        var result: String? = null

        try {
            result = task.execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22").get()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        Log.i("Result", result)
    }

    companion object {
        class DownloadTask internal constructor(context: MainActivity) : AsyncTask<String, Void, String?>() {
            override fun doInBackground(vararg urls: String?): String? {
                var result = ""
                val url: URL
                var urlConnection: HttpURLConnection? = null

                try {
                    url = URL(urls[0])
                    urlConnection = url.openConnection() as HttpURLConnection
                    val file = urlConnection.inputStream
                    val reader = InputStreamReader(file)
                    var data: Int = reader.read()

                    while (data != -1) {
                        val current: Char = data.toChar()
                        result += current
                        data = reader.read()
                    }

                    return result

                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                    return "failed"
                }

                return "AsyncTask Done"
            }
        }
    }
}
