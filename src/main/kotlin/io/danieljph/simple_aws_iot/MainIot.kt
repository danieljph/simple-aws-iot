package io.danieljph.simple_aws_iot

import com.amazonaws.services.iot.client.AWSIotMqttClient

private const val AWS_CONNECT_TIMEOUT_IN_MILLISECONDS = 30_000L

fun main()
{
    testConnect("a200oqh54iecas-ats.iot.us-east-1.amazonaws.com")
    testConnect("a23x7roz31nypq-ats.iot.us-east-1.amazonaws.com")
    testConnect("a200oqh54iecas-ats.iot.ap-southeast-1.amazonaws.com")
}

fun testConnect(endpoint: String)
{
    println("====================================================")
    println("Test connect with endpoint: $endpoint")
    val thingName = "00000810209232"
    val certificatePathname = "ssl/00000810209232-certificate.pem.crt"
    val privateKeyPathname = "ssl/00000810209232-private.pem.key"


    println("Reading Certificate and Private Key...")
    val keyStorePasswordPair = KeyStoreUtil.getKeyStorePasswordPair(certificatePathname, privateKeyPathname)
    println("Reading Certificate and Private Key has done.")

    println("Creating new IotMqttClient...")
    val client = AWSIotMqttClient(endpoint, thingName, keyStorePasswordPair.keyStore, keyStorePasswordPair.keyPassword)
    println("Creating new IotMqttClient has done.")

    println("Creating new RcDevice...")
    val rcDevice = RcDevice(thingName, endpoint)
    println("Creating new RcDevice has done.")
    rcDevice.reportInterval = 1_000
    println("Set RcDevice.reportInterval = ${rcDevice.reportInterval}ms.")
    client.attach(rcDevice)
    println("RcDevice attached to AWSIotMqttClient.")

    println("IotMqttClient connecting...")
    client.connect(AWS_CONNECT_TIMEOUT_IN_MILLISECONDS)
    println("IotMqttClient connected.")

    println("Will sleep for 5s and make sure RcDevice info being pushed to AWS IOT Core.")
    Thread.sleep(5_000)
    client.disconnect()
    Thread.sleep(1_000)
    println("====================================================")
}
