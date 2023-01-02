package com.example.projetbar.ui.notifications

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projetbar.MainActivity
import com.example.projetbar.databinding.FragmentNotificationsBinding


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
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        textView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
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
            if(sensorEvent.values[2] > 0.5f || sensorEvent.values[2] < -0.5f) {
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
}