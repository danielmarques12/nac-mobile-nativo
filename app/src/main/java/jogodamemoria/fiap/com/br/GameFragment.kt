package jogodamemoria.fiap.com.br

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import jogodamemoria.fiap.com.br.data.MemoryCard
import jogodamemoria.fiap.com.br.databinding.FragmentGameBinding
import jogodamemoria.fiap.com.br.R.drawable.*
import jogodamemoria.fiap.com.br.data.ASSharedPreferences

class GameFragment : Fragment() {

    private var endGameStars: Int = 5
    private var elapsedTime: Int = 0
    private lateinit var chronometer: Chronometer
    private lateinit var handler: Handler
    private lateinit var sharedPrefs: ASSharedPreferences
    private lateinit var bindings: FragmentGameBinding
    private lateinit var buttons: List<ImageButton>
    private lateinit var images: MutableList<Int>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPrefs = ASSharedPreferences(context)
    }

    override fun onDetach() {
        super.onDetach()
        calculateScore(elapsedTime, endGameStars)
        Log.d("elapsed", elapsedTime.toString())
        handler.removeCallbacksAndMessages(null)
    }

    private fun calculateScore(seconds: Int, stars: Int) {
        val score = seconds * stars
        sharedPrefs.updateTopScore(score, stars, seconds)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindings = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startChronometer()
        startStarsDecrement()

        images = mutableListOf(elixir, flutter, java, js, kotlin, rust)
        images.addAll(images)
        images.shuffle()

        buttons = listOf(bindings.imageButton, bindings.imageButton2, bindings.imageButton3,
                bindings.imageButton4, bindings.imageButton5, bindings.imageButton6,
                bindings.imageButton7, bindings.imageButton8, bindings.imageButton9,
                bindings.imageButton10, bindings.imageButton11, bindings.imageButton12)

        cards = buttons.indices.map { index -> MemoryCard(images[index]) }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                updateModels(index)
                updateViews()
            }
        }
    }

    private fun startChronometer() {
        chronometer = bindings.chronometer
        chronometer.start()
    }

    private fun startStarsDecrement() {
        handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                elapsedTime = ((SystemClock.elapsedRealtime() - chronometer.base) / 1000).toInt()
                when (elapsedTime) {
                    20 -> {
                        bindings.star5.visibility = View.GONE
                        endGameStars--
                    }
                    25 -> {
                        bindings.star4.visibility = View.GONE
                        endGameStars--
                    }
                    30 -> {
                        bindings.star3.visibility = View.GONE
                        endGameStars--
                    }
                    35 -> {
                        bindings.star2.visibility = View.GONE
                        endGameStars--
                    }
                }
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            button.setImageResource(if (card.isFaceUp) card.id else R.drawable.question_mark)
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]
        if (card.isFaceUp) {
            return
        }
        if (indexOfSingleSelectedCard == null) {
            restoreCards()
            indexOfSingleSelectedCard = position
        }
        else {
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        if(cards[position1].id == cards[position2].id) {
            cards[position1].isMatched = true
            cards[position2].isMatched = true

            if (gameFinished()) {
                view?.findNavController()?.navigate(R.id.action_gameFragment_to_rankingFragment)
            }
        }
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    private fun gameFinished(): Boolean {
        cards.forEachIndexed() { _, card ->
            if (!card.isMatched) { return false }
        }
        return true
    }
}