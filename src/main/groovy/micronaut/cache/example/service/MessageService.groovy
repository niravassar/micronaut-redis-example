package micronaut.cache.example.service

import io.micronaut.cache.annotation.Cacheable
import micronaut.cache.example.domain.Message

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageService {

    @Inject MessageDataService messageDataService
    int invocationCounter = 0

    @Cacheable("my-cache")
    Message findMessageByTitle(String title) {
        ++invocationCounter
        println "findMessageByTitle called"
        Message message = messageDataService.findByTitle(title)
        message
    }
}