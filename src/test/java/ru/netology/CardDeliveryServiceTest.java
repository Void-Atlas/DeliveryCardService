package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;

public class CardDeliveryServiceTest {
}

class registrationTest{

    public String generateDate(int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldRegisterByDeliverCard() {
        String planningDate = generateDate(4, "dd.MM.yyyy");

        open("http://localhost:9999");
        SelenideElement form = $$("form").find(Condition.visible);
        form.$("[data-test-id='city'] input").setValue("Краснод");
        $$("div.popup__content div").find(Condition.text("Краснодар")).click();
        form.$("[data-test-id='date'] input").should(Condition.visible)
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                        .setValue(planningDate);
        form.$("[data-test-id='name'] input").setValue("Дэниел Нит");
        form.$("[data-test-id='phone'] input").setValue("+79999999969");
        form.$("[data-test-id='agreement']").click();
        $$("button").filter(Condition.visible).find(Condition.text("Забронировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.visible, Duration.ofSeconds(18));
        $("[data-test-id='notification']").should(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(18)).should(Condition.visible);
    }
}