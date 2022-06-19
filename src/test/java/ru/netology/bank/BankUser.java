package ru.netology.bank;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static ru.netology.bank.BankTest.requestSpec;

public class BankUser {
    private BankUser() {
    }

    public static class User {
        private static final Faker faker = new Faker(new Locale("en"));
        private static final String login = faker.name().username();
        private static final String password = faker.internet().password();

        public static void userActive() {
            // сам запрос
            given() // "дано"
                    .spec(requestSpec) // указываем, какую спецификацию используем
                    .body(new newUser(login, password, "active")) // передаём в теле объект, который будет преобразован в JSON
                    .when() // "когда"
                    .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                    .then() // "тогда ожидаем"
                    .statusCode(200); // код 200 OK
        }

        public static void userBlocked() {
            given()
                    .spec(requestSpec)
                    .body(new newUser(login, password, "blocked"))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        public static String getLogin() {
            return login;
        }

        public static String getPassword() {
            return password;
        }
    }

    @Value
    private static class newUser {
        String login;
        String password;
        String status;
    }
}
