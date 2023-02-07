package weston.luke.navdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class VerifyDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_verify_details, container, false)

        val name = arguments?.getString("name")
        val mobileNumber = arguments?.getLong("mobile")

        val tvName = rootView.findViewById<TextView>(R.id.tv_name)
        val tvMobileNumber = rootView.findViewById<TextView>(R.id.tv_mobile)

        tvName.text = name
        tvMobileNumber.text = mobileNumber.toString()



        // Inflate the layout for this fragment
        return rootView
    }


}