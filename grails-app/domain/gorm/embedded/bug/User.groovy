package gorm.embedded.bug

class User {
    String name
    PlayerProfile playerProfile = new PlayerProfile()
    static embedded = ['playerProfile']

    static mapping = {}
}
