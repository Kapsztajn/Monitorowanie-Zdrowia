package kamil.com.monitorowaniezdrowia.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kamil.com.monitorowaniezdrowia.R
import kotlinx.android.synthetic.main.content_main2.*
import kotlinx.android.synthetic.main.fragment_fit.*
import android.widget.TextView
import kamil.com.monitorowaniezdrowia.DatabaseHelper
import kotlinx.android.synthetic.main.fragment_fit.view.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class fit : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var handler: DatabaseHelper

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        handler = DatabaseHelper(this!!.getActivity()!!)
        return inflater.inflate(R.layout.fragment_fit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var wartosc = handler.readKroki()
        kroki.text = wartosc
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            fit().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
