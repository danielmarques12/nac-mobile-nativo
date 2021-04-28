package jogodamemoria.fiap.com.br

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jogodamemoria.fiap.com.br.data.ASSharedPreferences
import jogodamemoria.fiap.com.br.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var sharedPrefs: ASSharedPreferences
    private lateinit var bindings: FragmentResultBinding
    private val args: ResultFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPrefs = ASSharedPreferences(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindings = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        renderResults()
        bindings.ok.setOnClickListener {
            view.findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }
    }

    private fun renderResults() {
        if (args.score >= sharedPrefs.getScore()) {
            bindings.setSeconds(args.seconds)
            bindings.setScoreText("Score")
            renderStars(args.stars)
        }
        else {
            val seconds = sharedPrefs.getSeconds().toString()
            val stars = sharedPrefs.getStars()
            bindings.setSeconds(seconds)
            bindings.setScoreText("Best score")
            renderStars(stars)
        }
    }

    private fun renderStars(stars: Int) {
        when (stars) {
            1 -> bindings.star.visibility = View.VISIBLE
            2 -> {
                bindings.star.visibility = View.VISIBLE
                bindings.star2.visibility = View.VISIBLE
            }
            3 -> {
                bindings.star.visibility = View.VISIBLE
                bindings.star2.visibility = View.VISIBLE
                bindings.star3.visibility = View.VISIBLE
            }
            4 -> {
                bindings.star.visibility = View.VISIBLE
                bindings.star2.visibility = View.VISIBLE
                bindings.star3.visibility = View.VISIBLE
                bindings.star4.visibility = View.VISIBLE
            }
            5 -> {
                bindings.star.visibility = View.VISIBLE
                bindings.star2.visibility = View.VISIBLE
                bindings.star3.visibility = View.VISIBLE
                bindings.star4.visibility = View.VISIBLE
                bindings.star5.visibility = View.VISIBLE
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        findNavController().popBackStack()
    }
}