package gorm.embedded.bug

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class UserSpec extends Specification implements DomainUnitTest<User> {

    def setup() {
    }

    def cleanup() {
    }

    void "should persist and retrieve"() {
        given:
            new User(
                    name: "Jojo",
                    playerProfile: new PlayerProfile(
                            level: 15,
                            nickname: "jojofr",
                            roles: [PlayerRole.SUPPORT, PlayerRole.SHOTCALLER]
                    )
            ).save(flush: true, failOnError: true)
        when:
            def user = User.findByName("Jojo")
        then:
            user != null
            user.playerProfile.level == 15
            user.playerProfile.roles.size() == 2
    }
}
