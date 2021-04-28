package jogodamemoria.fiap.com.br

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import jogodamemoria.fiap.com.br.data.ASSharedPreferences
import jogodamemoria.fiap.com.br.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var sharedPrefs: ASSharedPreferences
    private lateinit var bindings: FragmentHomeBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPrefs = ASSharedPreferences(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindings = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings.next.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToGameFragment()
            view.findNavController().navigate(action)
        }
        renderBestScore()
    }

    private fun renderBestScore() {
        val seconds = sharedPrefs.getSeconds().toString()
        val stars = sharedPrefs.getStars()
        bindings.setSeconds(seconds)
        renderStars(stars)
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
}