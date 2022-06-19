package ru.netology.bank;

import com.codeborne.selenide.Condition;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.bank.BankUser.User.*;

public class BankTest {
    static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void testUserLogin() {
        userActive();
        $x("//input[@name=\"login\"]").val(getLogin());
        $x("//input[@name=\"password\"]").val(getPassword());
        $x("//button[@class=\"button button_view_extra button_size_m button_theme_alfa-on-white\"]").click();
        $(".heading").shouldHave(Condition.text("  Личный кабинет"));
    }

    @Test
    void testUserBlocked() {
        userBlocked();
        $x("//input[@name=\"login\"]").val(getLogin());
        $x("//input[@name=\"password\"]").val(getPassword());
        $x("//button[@class=\"button button_view_extra button_size_m button_theme_alfa-on-white\"]").click();
        $(".notification__content").shouldHave(Condition.text("Пользователь заблокирован"));
    }

    @Test
    void invalidLogin() {
        userActive();
        $x("//input[@name=\"login\"]").val("badlogin");
        $x("//input[@name=\"password\"]").val(getPassword());
        $x("//button[@class=\"button button_view_extra button_size_m button_theme_alfa-on-white\"]").click();
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    void invalidPassword() {
        userActive();
        $x("//input[@name=\"login\"]").val(getLogin());
        $x("//input[@name=\"password\"]").val("badpassword");
        $x("//button[@class=\"button button_view_extra button_size_m button_theme_alfa-on-white\"]").click();
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
}
