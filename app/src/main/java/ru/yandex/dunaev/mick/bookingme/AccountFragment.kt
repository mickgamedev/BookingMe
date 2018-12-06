package ru.yandex.dunaev.mick.bookingme


import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import ru.yandex.dunaev.mick.bookingme.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var model: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_account,
            container,
            false
        )

        binding.apply {
            viewModel = model
            textPrivacy.apply{
                setSpanColor("Terms & Conditions",resources.getColor(R.color.colorWhite, activity!!.theme),
                    resources.getColor(R.color.colorTextSpanClick, activity!!.theme)) {  }
                setSpanColor("Privacy Statement",resources.getColor(R.color.colorWhite, activity!!.theme),
                    resources.getColor(R.color.colorTextSpanClick, activity!!.theme)) {  }
            }
            buttonSignEmail.setOnClickListener {  }
            buttonSignGoogle.setOnClickListener {  }
            buttonSignFacebook.setOnClickListener {  }
            textCreateAccount.setOnClickListener {  }


        }

        return binding.root
    }

}
