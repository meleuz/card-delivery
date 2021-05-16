package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    public String getPlanningDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldCorrectForm1() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String date = getPlanningDate(3);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Иванов-Петров Иван");
        $("[data-test-id='phone'] input").setValue("+77777777777");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Успешно!")).waitUntil(visible, 15000);
        $("[data-test-id=notification] .notification__content").shouldHave(text("Встреча успешно забронирована на "+date));
    }

    @Test
    void shouldTestNotCorrectCity() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Рим");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String date = getPlanningDate(3);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Иванов-Петров Иван");
        $("[data-test-id='phone'] input").setValue("+77777777777");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestNotCorrectDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String date = getPlanningDate(1);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Иванов-Петров Иван");
        $("[data-test-id='phone'] input").setValue("+77777777777");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldNotCorrectName() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String date = getPlanningDate(3);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Ivanov-Petrov Ivan");
        $("[data-test-id='phone'] input").setValue("+77777777777");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotCorrectPhone() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String date = getPlanningDate(3);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Иванов-Петров Иван");
        $("[data-test-id='phone'] input").setValue("777777777777");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldCorrectFormNotAgreement() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String date = getPlanningDate(3);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Иванов-Петров Иван");
        $("[data-test-id='phone'] input").setValue("+77777777777");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldTestEmptyForm() {
        open("http://localhost:9999/");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestEmptyCity() {
        open("http://localhost:9999/");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String date = getPlanningDate(3);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Иванов-Петров Иван");
        $("[data-test-id='phone'] input").setValue("+77777777777");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestEmptyDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Иванов-Петров Иван");
        $("[data-test-id='phone'] input").setValue("+77777777777");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldEmptyName() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String date = getPlanningDate(3);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='phone'] input").setValue("+77777777777");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldEmptyPhone() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String date = getPlanningDate(3);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Иванов-Петров Иван");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}