package com.example.coroutines_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val RESULT_1 = "Result #1"

    private val RESULT_2 = "Result #2"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            // 3 types of scopes 1. IO - input output for networking and local database,
            // 2. Main for the main thread, the UI,
            //and Default for heavy computations

            CoroutineScope(IO).launch {
                fakeApiRequest()
            }

        }
    }

    private fun setNewText(input:String){
        val newText = textview.text.toString() + "\n$input"
        textview.text = newText
    }

   private suspend fun setTextOnMainThread(input : String){
       withContext(Main){
           setNewText(input)
       }
   }

    private suspend fun fakeApiRequest() {

        val result1 = getResult1FromApi()
        println("debug: ${result1}")
        setTextOnMainThread(result1)

        val result2 = getResult2FromApi()
        setTextOnMainThread(result2)
    }

    /** suspend a Kotlin keyword means the method as something that can be asycrounous , can be
     * called from withing a coroutine*/
    private suspend fun getResult1FromApi(): String {
        logThread("getResult1FromApi")
        // delay() method will delay the single coroutine its called on. unlike sleep() method that will
        // delay the whole thread and the coroutines inside the thread..
        delay(100)
        return RESULT_1
    }

    private suspend fun getResult2FromApi():String {
        logThread("getResult2FromApi")
        delay(100)
        return RESULT_2
    }

    private fun logThread(methodName: String) {
        println("debug: ${methodName} :  ${Thread.currentThread().name}")
    }


}
