package gorm.embedded.bug

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class UserServiceSpec extends Specification {

    UserService userService
    SessionFactory sessionFactory

    private Long setupData() {
        def user = new User(
                name: "Jojo",
                playerProfile: new PlayerProfile(
                        level: 15,
                        inGameNickName: "jojofr",
                        roles: [PlayerRole.SUPPORT, PlayerRole.SHOTCALLER]
                )
        ).save(flush: true, failOnError: true)
        return user.id
    }

    void "test get"() {
        setupData()

        expect:
        userService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<User> userList = userService.list(max: 2, offset: 2)

        then:
        userList.size() == 1
    }

    void "test count"() {
        setupData()

        expect:
        userService.count() == 1
    }

    void "test delete"() {
        Long userId = setupData()

        expect:
        userService.count() == 1

        when:
        userService.delete(userId)
        sessionFactory.currentSession.flush()

        then:
        userService.count() == 0
    }

    void "test save"() {
        when:
        User user = new User(
                name: "Fifou",
                playerProfile: new PlayerProfile(
                        level: 1,
                        inGameNickName: "fifi",
                        roles: []
                )
        ).save(flush: true, failOnError: true)
        userService.save(user)

        then:
        user.id != null
    }
}
