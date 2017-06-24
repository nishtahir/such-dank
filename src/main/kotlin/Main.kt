import kotlin.js.*

@JsModule("botkit")
external val botkit: dynamic = definedExternally

@JsModule("config")
external val config: dynamic = definedExternally

external val process: dynamic = definedExternally

fun main(args: Array<String>) {

    val clientId = config.get("bot.client-id")
    val clientSecret = config.get("bot.client-secret")
    val botUserToken = config.get("bot.bot-user-token")
    val datastore = config.get("data.datastore")
    val port = config.get("server.port")

    var controller = botkit.slackbot(json { 
        "debug" to true
        "studio_token" to process.env.studio_token 
        "json_file_store" to datastore
    })
    with(controller) {
        configureSlackApp(json {
                "clientId" to clientId
                "clientSecret" to clientSecret
                "redirectUri" to process.env.redirectUri
                "scopes" to arrayOf("bot", "commands")
        })

        setupWebserver(port) { error, webserver ->
            createWebhookEndpoints(webserver)
            createOauthEndpoints(webserver) { error, request, response ->
                if (error) {
                    response.status(500).send("ERROR: $err")
                } else {
                    response.send("Success!");
                }
            }
        }

        on("slash_command") { bot, message ->
            when(message.command) {
                "/dank" -> {
                    var text: String = message.text
                    bot.replyPublic(message, "https://memegen.link/${text.replace(" ", "_").replace(";", "/")}.jpg")
                }
                else -> {

                }
            }
        }
    }
}

fun json(build: JsonBuilder.() -> Unit): Json {
    val builder = JsonBuilder()
    builder.build()
    return builder.json
}

class JsonBuilder {
    val json = json()

    infix fun <T> String.to(value: T) {
        json.set(this, value)
    }
}