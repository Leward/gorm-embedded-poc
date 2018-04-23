package gorm.embedded.bug

class BootStrap {

    def init = { servletContext ->
        new User(
                name: "Jojo",
                playerProfile: new PlayerProfile(
                        level: 7,
                        nickname: "titi",
                        roles: [PlayerRole.SUPPORT, PlayerRole.SHOTCALLER]
                )
        ).save(flush: true, failOnError: true)
    }
    def destroy = {
    }
}
