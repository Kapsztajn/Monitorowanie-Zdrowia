package kamil.com.monitorowaniezdrowia.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kamil.com.monitorowaniezdrowia.DatabaseHelper

import kamil.com.monitorowaniezdrowia.R
import kotlinx.android.synthetic.main.fragment_kuchnia.*
import kotlinx.android.synthetic.main.fragment_kuchnia.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class kuchnia : Fragment() {

    private var ctx: Context? = null
    private var self: View? = null
    lateinit var handler: DatabaseHelper
    private var param1: String? = null
    private var param2: String? = null
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
        handler = DatabaseHelper(this!!.getActivity()!!)
        ctx = container?.context
        self = LayoutInflater.from(ctx).inflate(R.layout.fragment_kuchnia, container, false)
        val bDaButton = self?.findViewById<Button>(R.id.button3)
        bDaButton?.setOnClickListener {
            var wartosc = handler.readKroki()
            var kaloriezkrokow = String.format("%.0f", wartosc.toInt()*0.05)
            var podanekalorie = podanekalorie.text.toString()
            var obliczonekalorie = (podanekalorie.toInt().minus(kaloriezkrokow.toInt()))
            if (obliczonekalorie<=0)
            {
                Toast.makeText(ctx, "Brawo! Dzisiaj już masz fajrant", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(
                    ctx,
                    "Musisz jeszcze spalić: " + obliczonekalorie.toString() + " kalorii",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return self

    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment kuchnia.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            kuchnia().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
