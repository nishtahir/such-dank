# Such Dank!

A memegenerator slackbot written in KotlinJS.

## Building

After cloning the repo you can build using gradle 

```
$ ./gradlew build 
```

This compiles the project to javascript.

## Setup

This app uses `config` to manage configuration files. Here's a sample config.

```
{
    "bot" : {
        "client-id" : "[ ... ]",
        "client-secret" : "[ ... ]",
        "bot-user-token" : "[ ... ]",
        "oauth-token" : "[ ... ]"
    }, 
    "server" : {
        "port" : 3000
    },
    "data" : {
        "datastore" : "./json_datastore"
    }
}

```

This should be placed in `config/<configuration_name>.json`. You can have multiple configurations and set the env using

```
export NODE_ENV=<configuration_name>
```

## Running

You can run the app using node

```
$ node ./build/web/output.js
```