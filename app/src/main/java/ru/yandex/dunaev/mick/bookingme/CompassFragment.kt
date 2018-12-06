package ru.yandex.dunaev.mick.bookingme


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import ru.yandex.dunaev.mick.bookingme.databinding.FragmentCompassBinding

class CompassFragment : Fragment() {
    private lateinit var binding: FragmentCompassBinding
    private lateinit var model: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding = (DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_compass,
            container,
            false
        ) as FragmentCompassBinding).apply {
            viewModel = model
        }

        return binding.root
    }


}
