package tests;
import com.github.javafaker.Faker;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class TestPracticeForm extends TestBase {

    Faker faker = new Faker();
    String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            email = faker.internet().emailAddress(),
            gender = "Female",
            mobile = faker.number().digits(10),
            birthDay = "13",
            birthMonth = "August",
            birthYear = "1985",
            birthCheck = "13 August,1985",
            subjects = "Biology",
            hobby = "Music",
            picture = "1.jpg",
            address = faker.address().fullAddress(),
            state = "Haryana",
            city = "Karnal";

    @Test
    void successfulFillTest() {
        step("Open students registration form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });
        step("Fill students registration form", () -> {
            step("Fill common data", () -> {
                $("#firstName").val(firstName);
                $("#lastName").val(lastName);
                $("#userEmail").val(email);
                $("#genterWrapper").$(byText(gender)).click();
                $("#userNumber").val(mobile);
            });
            step("Set date", () -> {
                $("#dateOfBirthInput").clear();
                $(".react-datepicker__month-select").selectOption(birthMonth);
                $(".react-datepicker__year-select").selectOption(birthYear);
                $(".react-datepicker__day--0" + birthDay).click();
            });

            step("Set subjects", () -> {
                $("#subjectsInput").val(subjects);
                $(".subjects-auto-complete__menu-list").$(byText(subjects)).click();
            });
            step("Set hobbies", (Allure.ThrowableRunnableVoid) $("#hobbiesWrapper").$(byText(hobby))::click);
            step("Upload image", () ->
                    $("#uploadPicture").uploadFromClasspath("img/" + picture));
            step("Set address", () -> {
                $("#currentAddress").val(address);
                $("#state").scrollTo().click();
                $("#stateCity-wrapper").$(byText(state)).click();
                $("#city").click();
                $("#stateCity-wrapper").$(byText(city)).click();
            });
            step("Submit form", () ->
                    $("#submit").click());
            step("Verify successful form submit", () -> {
                $(".modal-header").shouldHave(text("Thanks for submitting the form"));
                $(byText(firstName + " " + lastName)).shouldBe(visible);
                $(".modal-body").$(byText(gender)).shouldHave(text(gender));
                $(byText(mobile)).shouldBe(visible);
                $(byText(email)).shouldBe(visible);
                $(byText(birthCheck)).shouldBe(visible);
                $(byText(subjects)).shouldBe(visible);
                $(byText(hobby)).shouldBe(visible);
                $(byText(picture)).shouldBe(visible);
                $(byText(address)).shouldBe(visible);
                $(byText(state + " " + city)).shouldBe(visible);
            });

        });
    }
}