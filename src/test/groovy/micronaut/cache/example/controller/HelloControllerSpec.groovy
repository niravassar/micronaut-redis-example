package micronaut.cache.example.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import micronaut.cache.example.Application
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest(application = Application)
class HelloControllerSpec extends Specification {

    @Inject
    @Client("/")
    RxHttpClient client

    void "test hello world response"() {
        when:
        HttpRequest request = HttpRequest.GET('/hello')
        String rsp = client.toBlocking().retrieve(request)

        then:
        rsp == "Hello World"
    }
}
