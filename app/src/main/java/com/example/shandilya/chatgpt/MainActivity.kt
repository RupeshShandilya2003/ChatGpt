package com.example.shandilya.chatgpt

import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var queryEdit: TextInputEditText
    lateinit var messageRV: RecyclerView
    lateinit var messageRVAdapter: MessageRVAdapter
    lateinit var messageList: ArrayList<MessageRVModal>
    var url = "https://api.openai.com/v1/completions"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queryEdit = findViewById(R.id.idEdtQuery)
         messageRV = findViewById(R.id.idRVMessages)
        messageList = ArrayList()
        messageRVAdapter = MessageRVAdapter(messageList)
        val layoutManager = LinearLayoutManager(applicationContext)
        messageRV.layoutManager = layoutManager
        messageRV.adapter = messageRVAdapter


        queryEdit.setOnEditorActionListener(TextView.OnEditorActionListener{ textView, i, keyEvent ->
            if(i == EditorInfo.IME_ACTION_SEND){
                if(queryEdit.text.toString().length > 0){
                    messageList.add(MessageRVModal(queryEdit.text.toString(), "user"))
                    messageRVAdapter.notifyDataSetChanged()
                    getResponse(queryEdit.text.toString())
                } else{
                    Toast.makeText(this,"Please Enter Your Query Rupesh!",Toast.LENGTH_SHORT).show()
                }
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun getResponse(query: String){
        queryEdit.setText("")
        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
        val jsonObject: JSONObject? = JSONObject()
        jsonObject?.put("model","text-davinci-003")
        jsonObject?.put("prompt",query)
        jsonObject?.put("temperature",0)
        jsonObject?.put("max_tokens",100)
        jsonObject?.put("top_p",1)
        jsonObject?.put("frequency_penalty",0.0)
        jsonObject?.put("presence_penalty",0.0)

        val postRequest: JsonObjectRequest = object: JsonObjectRequest(Method.POST,url,jsonObject,Response.Listener { response ->
            val responseData: String = response.getJSONArray("choices").getJSONObject(0).getString("text")
            messageList.add(MessageRVModal(responseData,"bot"))
            messageRVAdapter.notifyDataSetChanged()
        },Response.ErrorListener {
            Toast.makeText(applicationContext,"Fail to Response...",Toast.LENGTH_SHORT).show()
        }){
            override fun getHeaders(): MutableMap<String, String> {
                val params: MutableMap<String,String> = HashMap()

                params["Content-Type"] = "application/json"
                params["Authorization"] = "Bearer Enter Your Api key"

                return  params
            }
        }

        postRequest.setRetryPolicy(object : RetryPolicy{
            override fun getCurrentTimeout(): Int {
                return 20000
            }

            override fun getCurrentRetryCount(): Int {
                return 20000
            }

            override fun retry(error: VolleyError?) {
                TODO("Not yet implemented")
            }
        })
        queue.add(postRequest)
    }
}