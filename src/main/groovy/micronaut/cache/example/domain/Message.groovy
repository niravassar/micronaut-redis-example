package micronaut.cache.example.domain

import grails.gorm.annotation.Entity

@Entity
class Message implements Serializable {
    String title
}
