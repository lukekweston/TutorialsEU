package weston.luke.navdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass.
 * Use the [EnterDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EnterDetailsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_enter_details, container, false)

        val btnVerifyDetails = rootView.findViewById<Button>(R.id.btn_verify_details)
        btnVerifyDetails.setOnClickListener {
            findNavController().navigate(R.id.action_enterDetailsFragment_to_verifyDetailsFragment)
        }

        return rootView
    }

}