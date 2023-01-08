package com.example.projetbar.ui.notifications

import android.annotation.SuppressLint
import android.content.Context.SENSOR_SERVICE
import android.content.res.Configuration
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projetbar.MainActivity
import com.example.projetbar.R
import com.example.projetbar.databinding.FragmentNotificationsBinding
import com.example.projetbar.ui.home.HomeViewModel
import com.example.projetbar.ui.map.MapsFragment
import nl.adaptivity.xmlutil.serialization.structure.PolymorphicMode.*


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var mainActivity: MainActivity
    private lateinit var sensorManager: SensorManager
    private lateinit var gyroscopeSensor: Sensor
    private lateinit var image_alcootest: ImageView
    private lateinit var textView: TextView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        textView = binding.textNotifications
        notificationsViewModel.textAlcootest.observe(viewLifecycleOwner) {
            textView.text = it
        }

        mainActivity = context as MainActivity

        sensorManager = mainActivity.getSystemService(SENSOR_SERVICE) as SensorManager

        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        image_alcootest = binding.imageAlcootest


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Create a listener
    private val gyroscopeSensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(sensorEvent: SensorEvent) {
            // More code goes here
            if(sensorEvent.values[2] > 1f || sensorEvent.values[2] < -1f) {
                 image_alcootest.rotation = 180f
                textView.text = "Perdu ! T'as trop bu"
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            gyroscopeSensorListener,
            gyroscopeSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(gyroscopeSensorListener)
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nightModeFlags =
            requireView().context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                getContext()?.getResources()
                    ?.let {
                        image_alcootest.setColorFilter(it.getColor(R.color.yellow))
                        //binding.root.setBackgroundColor(R.color.background_black)
                    }
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                getContext()?.getResources()
                    ?.let {
                        image_alcootest.setColorFilter(it.getColor(R.color.black))
                        //binding.root.setBackgroundColor(R.color.yellow)
                    }
            }
        }
    }
}