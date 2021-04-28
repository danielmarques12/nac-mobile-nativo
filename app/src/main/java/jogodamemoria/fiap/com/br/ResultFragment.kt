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
import jogodamemoria.fiap.com.br.data.ASSharedPreferences
import jogodamemoria.fiap.com.br.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var sharedPrefs: ASSharedPreferences
    private lateinit var bindings: FragmentResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

        bindings.ok.setOnClickListener {
            view.findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }

        val bestScore = sharedPrefs.getScore().toString()
        val seconds = sharedPrefs.getSeconds().toString()
        val stars = sharedPrefs.getStars().toString()

        bindings.setSeconds(seconds)
    }

    override fun onDetach() {
        super.onDetach()
        findNavController().popBackStack()
    }
}