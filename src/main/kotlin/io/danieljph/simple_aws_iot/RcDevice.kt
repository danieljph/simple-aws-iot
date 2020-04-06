package io.danieljph.simple_aws_iot

import com.amazonaws.services.iot.client.AWSIotDevice
import com.amazonaws.services.iot.client.AWSIotDeviceProperty
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Daniel Joi Partogi Hutapea
 */
@Suppress("SuspiciousVarProperty")
class RcDevice(thingName: String, awsIotEndpoint: String) : AWSIotDevice(thingName)
{
    companion object {
        private val TAG = RcDevice::class.java.simpleName
        private val ISO_8601_SDF = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply { timeZone = TimeZone.getTimeZone("UTC") }
    }

    private var hasChanges = false

    override fun onDeviceReport(): String {
        println("AwsIotCore preparing device report...")
        val onDeviceReportResult =  super.onDeviceReport()
        println("AwsIotCore preparing device report has done.")
        return onDeviceReportResult
    }

    override fun onShadowUpdate(jsonState: String?)
    {
        super.onShadowUpdate(jsonState)
        if(hasChanges)
        {
            println("State is changed after shadow updated. Sending device report...")
            sendDeviceReport()
            println("Sending device report has done.")
            hasChanges = false
        }
    }

    @field:AWSIotDeviceProperty var lastUpdateAt: String? = null
        get() = ISO_8601_SDF.format(Date())

    @field:AWSIotDeviceProperty var id = "00000810209232"
    @field:AWSIotDeviceProperty var name = "Rumah DJPH"
    @field:AWSIotDeviceProperty var awsIotEndpoint = awsIotEndpoint
}
