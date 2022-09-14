public class UserCreator {

    public static User createUser() {
        User.Address.Geo geo = new User.Address.Geo("123456", "123456");
        User.Address address = new User.Address("Iskrivska", "12", "Kyiv", "03186", geo);
        User.Company company = new User.Company("CD Projekt Red", "We Are Rebbels", "ccTLD");

        return new User(11, "Orest", "Dymarchuk", "email@email.com", address, "123-456-789", "abracadabra.com", company);
    }
}
