package me.zw.echo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Min

@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "echo-server")
data class EchoServerProperties(

    @field:Min(1000)
    val port: Int

)