package com.medhdj.gow.features.fizzbuzz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.medhdj.gow.BuildConfig
import com.medhdj.gow.databinding.FragmentConfigBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ConfigFragment : Fragment() {
    private var _binding: FragmentConfigBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfigBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindConfigForm()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun FragmentConfigBinding.bindConfigForm() {
        goBtn.setOnClickListener {
            if (checkForm()) {
                val directions = ConfigFragmentDirections.actionGoToResult(
                    fizzNumber = fizzNumber.editText.asLong(),
                    buzzNumber = buzzNumber.editText.asLong(),
                    rangeLimit = limitNumber.editText.asLong(),
                    fizzWord = fizzWord.editText.asString(),
                    buzzWord = buzzWord.editText.asString()
                )

                findNavController().navigate(directions)
            }
        }

        // to speedup debugging and testing
        if (BuildConfig.DEBUG) {
            goBtn.setOnLongClickListener {
                fizzNumber.editText?.setText("3")
                buzzNumber.editText?.setText("5")
                limitNumber.editText?.setText("100")
                fizzWord.editText?.setText("fizz")
                buzzWord.editText?.setText("buzz")
                true
            }
        }
    }

    private fun checkForm(): Boolean = kotlin.runCatching {
        with(binding) {
            check(!fizzNumber.editText?.asString().isNullOrEmpty())
            check(!buzzNumber.editText?.asString().isNullOrEmpty())
            check(!limitNumber.editText?.asString().isNullOrEmpty())
            check(!fizzWord.editText?.asString().isNullOrEmpty())
            check(!buzzWord.editText?.asString().isNullOrEmpty())
            true
        }
    }.getOrDefault(false)
}

inline fun EditText?.asString() = this?.text.toString()
inline fun EditText?.asLong() = asString().toLong()
