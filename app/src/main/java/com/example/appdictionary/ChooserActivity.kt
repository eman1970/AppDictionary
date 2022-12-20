package com.example.appdictionary

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*


class ChooserActivity : AppCompatActivity(), WordAdapter2.UserClickListener {
    var rvWords: RecyclerView? = null
    var wordAdapter2: WordAdapter2? = null
    var wordModelList: MutableList<WordModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chooseractivity)
        rvWords = findViewById(R.id.rvWords2)
        prepareRecyclerView()
        addItemFromJSON()

        var ImageButton = findViewById<ImageButton>(R.id.button)
        ImageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addItemFromJSON() {
        try {
            val jsonDataString = readJSONDataFromFile()
            val jsonArray = JSONArray(jsonDataString)
            for (i in 0 until jsonArray.length()) {
                val itemObject = jsonArray.getJSONObject(i)
                val Svenska = itemObject.getString("Svenska")
                val Ryska = itemObject.getString("Ryska")
                val ImgURL = itemObject.getString("ImgURL")
                val SvenskaKort = itemObject.getString("SvenskaKort")
                val RyskaKort = itemObject.getString("RyskaKort")
                val LinkSV = itemObject.getString("LinkSV")
                val LinkRY = itemObject.getString("LinkRY")
                val model =
                    WordModel(Svenska, Ryska, ImgURL, SvenskaKort, RyskaKort, LinkSV, LinkRY)
                wordModelList.add(model)
                Collections.sort(
                    wordModelList,
                    Comparator.comparing { obj: WordModel -> obj.ordRyska })

            }
        } catch (e: JSONException) {
            Log.d(ContentValues.TAG, "addItemFromJSON: ", e)
        } catch (e: IOException) {
            Log.d(ContentValues.TAG, "addItemFromJSON: ", e)
        }
    }

    @Throws(IOException::class)
    private fun readJSONDataFromFile(): String {
        var inputStream: InputStream? = null
        val builder = StringBuilder()
        try {
            var jsonstring: String? = null
            inputStream = resources.openRawResource(R.raw.inputdata)
            val bufferedReader = BufferedReader(
                InputStreamReader(inputStream, StandardCharsets.UTF_8)
            )
            while (bufferedReader.readLine().also { jsonstring = it } != null) {
                builder.append(jsonstring)
            }
        } finally {
            inputStream?.close()
        }
        return String(builder)
    }

    fun prepareRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvWords!!.layoutManager = linearLayoutManager
        rvWords!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        preAdapter()
    }

    fun preAdapter() {
        wordAdapter2 = WordAdapter2(wordModelList, this,this)
        rvWords!!.adapter = wordAdapter2
    }

    override fun selectedWord(wordModel: WordModel) {
        Toast.makeText(this, "Selected word: " + wordModel.ordRyska, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, SelectedWordActivity::class.java).putExtra("data", wordModel))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.searchView) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val menuItem = menu.findItem(R.id.searchView)
        val searchView = menuItem.actionView as SearchView?
        searchView!!.queryHint = "Type your word here.."
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                wordAdapter2?.getFilter()?.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}





