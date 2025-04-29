package com.seamlesspay.demo.config

import com.seamlesspay.api.models.ClientConfiguration

object GlobalConfiguration {
  /**
   * Global [ClientConfiguration] instance used across the application.
   *
   * This configuration is initialized using environment-specific values such as the
   * API environment (e.g., "staging", "production"), secret API key and Proxy Account ID (Optional).
   *
   * The values are typically supplied from `BuildConfig` fields defined per build type
   * (e.g., `debug`, `release`) in the `build.gradle` file.
   *
   * @see ClientConfiguration.fromKeys
   */
  val clientConfiguration: ClientConfiguration by lazy {
    ClientConfiguration.fromKeys(
      /* environment = */ "staging",
      /* secretKey = */ "sk_XXXXXXXXXXXXXXXXXXXXXX",
      /* proxyAccountId = */ "MRT_XXXXXXXXXXXXXXXXXXXXXX"
    )
  }
}