package micronaut.cache.example.service

import grails.gorm.services.Service
import micronaut.cache.example.domain.Message

@Service(Message)
interface MessageDataService {

    Message save(Message message)
    Message findByTitle(String title)
    Integer count()
}