package io.danieljph.simple_aws_iot

import java.security.KeyStore

/**
 * @author Daniel Joi Partogi Hutapea
 */
data class KeyStorePasswordPair(
    val keyStore: KeyStore,
    val keyPassword: String
)
