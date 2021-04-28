package jogodamemoria.fiap.com.br.data

import android.content.Context
import android.content.SharedPreferences

class ASSharedPreferences(private val context: Context) {

    private val sharedPref: SharedPreferences
    private val playerStars = "playerStars"
    private val playerScore = "playerScore"
    private val playerSeconds = "playerSeconds"

    init {
        sharedPref = context.getSharedPreferences(playerScore, Context.MODE_PRIVATE)
    }

    fun getScore(): Int {
        return sharedPref.getInt(playerScore, 0)
    }

    fun getStars(): Int {
        return sharedPref.getInt(playerStars, 0)
    }

    fun getSeconds(): Int {
        return sharedPref.getInt(playerSeconds, 0)
    }

    fun updateTopScore(gameScore: Int, gameStars: Int, gameSeconds: Int) {
        val score = getScore()
        val editor = sharedPref.edit()


        if (score == 0) {
            editor.putInt(playerScore, gameScore)
            editor.putInt(playerStars, gameStars)
            editor.putInt(playerSeconds, gameSeconds)
            editor.apply()
            return
        }

        if (gameScore < score) {
            editor.putInt(playerScore, gameScore)
            editor.putInt(playerStars, gameStars)
            editor.putInt(playerSeconds, gameSeconds)
            editor.apply()
            return
        }
    }
}