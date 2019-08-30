package micronaut.cache.example.service

import grails.gorm.transactions.Rollback
import io.micronaut.cache.DefaultCacheManager
import io.micronaut.cache.SyncCache
import io.micronaut.configuration.lettuce.cache.RedisCache
import io.micronaut.test.annotation.MicronautTest
import micronaut.cache.example.Application
import micronaut.cache.example.domain.Message
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest(application = Application)
@Rollback
class MessageServiceSpec extends Specification {

    static final String MESSAGE_TITLE = "myMessage"

    @Inject MessageService messageService
    @Inject DefaultCacheManager defaultCacheManager

    def setup(){
        messageService.invocationCounter = 0
    }

    def "test method cached after first invocation"() {
        when: 'called the first time'
        Message message = messageService.findMessageByTitle(MESSAGE_TITLE)

        then: 'method runs by getting the gorm object'
        message.title == "myMessage"
        messageService.invocationCounter == 1

        when: 'called a second time'
        message = messageService.findMessageByTitle(MESSAGE_TITLE)

        then: 'the cache is accessed and the method doesnt run again'
        message.title == "myMessage"
        messageService.invocationCounter == 1

        when: 'called again with a different param'
        message = messageService.findMessageByTitle(MESSAGE_TITLE+"Again")

        then: 'method is invoked bc cache doesnt have the stored value'
        !message
        messageService.invocationCounter == 2
    }

    def "inspect the default cache manager"() {
        when: 'use the manager to find the caches'
        Set<String> cacheNames = defaultCacheManager.getCacheNames()

        then:
        cacheNames.size() == 1
        cacheNames.asList()[0] == "my-cache"

        when: 'get the cache'
        SyncCache<RedisCache> cache = defaultCacheManager.getCache("my-cache")
        Message message = (Message) cache.get("myMessage", Message).get()

        then:
        cache.getName() == "my-cache"
        message.title == "myMessage"

        when: 'cache is invalidated'
        cache.invalidateAll()
        Optional<Message> result = cache.get("myMessage", Message)

        then:
        !result.isPresent()
    }
}
