package com.example.damn_examen1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.damn_examen1.databinding.FragmentGameBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lista de switches (orden: bit7 a bit0)
        val switches = listOf(
            binding.switchBit7, binding.switchBit6, binding.switchBit5, binding.switchBit4,
            binding.switchBit3, binding.switchBit2, binding.switchBit1, binding.switchBit0
        )

        // Listener para todos los switches
        switches.forEach { switch ->
            switch.setOnCheckedChangeListener { _, _ ->
                updateBinaryAndDecimal(switches)
            }
        }

        // Botón para ir al quiz
        binding.goToQuizButton.setOnClickListener {
            findNavController().navigate(R.id.action_gameFragment_to_quizActivity)
        }
    }

    private fun updateBinaryAndDecimal(switches: List<SwitchMaterial>) {
        // Actualizar representación binaria (ej: "10101010")
        val binaryString = switches.joinToString("") { if (it.isChecked) "1" else "0" }
        binding.binaryRepresentation.text = binaryString

        // Calcular valor decimal
        val decimal = switches.foldIndexed(0) { index, acc, switch ->
            acc + if (switch.isChecked) 1 shl (switches.size - 1 - index) else 0
        }
        binding.decimalValue.text = decimal.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}