package micronaut.cache.example

import groovy.transform.CompileDynamic
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.Micronaut
import groovy.transform.CompileStatic
import io.micronaut.runtime.server.event.ServerStartupEvent
import micronaut.cache.example.domain.Message
import micronaut.cache.example.service.MessageDataService

import javax.inject.Inject

@CompileStatic
class Application implements ApplicationEventListener<ServerStartupEvent> {

    @Inject MessageDataService messageDataService

    static void main(String[] args) {
        Micronaut.run(Application)
    }

    @Override
    @CompileDynamic
    void onApplicationEvent(ServerStartupEvent serverStartupEvent) {
        Message message = new Message(title: "myMessage")
        messageDataService.save(message)
    }
}