package OAuthTest;

import PojoDeserial.Api;
import PojoDeserial.GetCourse;
import PojoDeserial.WebAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AuthPojo {

    public static void main(String[] args) throws InterruptedException {



        String [] title= {"Selenium Webdriver Java","Cypress","Protractor"};

        String url= "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AX4XfWigxP_H8g2Mu1amjaV2DXnAHYpbWdvJXL4e-3mwfeSXizKXPz6KmVOaEbRd4DznRw&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
        String partialCode= url.split("code=")[1];
        String code= partialCode.split("&scope")[0];
        System.out.println(code);

        String accessTokenResponse= given().
                urlEncodingEnabled(false).
                queryParams("code",code).
                queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
                queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W").
                queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php").
                queryParams("grant_type","authorization_code").
                when().log().all().
                post("https://www.googleapis.com/oauth2/v4/token").
                asString();
        JsonPath js=new JsonPath(accessTokenResponse);
        String accessToken= js.getString("access_token");


        GetCourse gc= given().
                queryParam("access_token",accessToken).
                expect().defaultParser(Parser.JSON).
                when().
                get("https://rahulshettyacademy.com/getCourse.php").
                as(GetCourse.class);
        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

        //Get the price of API course SoapUI Webservices testing

        List<Api> apiCourses=gc.getCourses().getApi();
        for (int i=0;i<apiCourses.size();i++)
        {
            if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
            {
                System.out.println(apiCourses.get(i).getPrice());
            }


        }

        //Get the courses names of WebAutomation in array and comparing

        ArrayList<String> a= new ArrayList<String>();

        List<WebAutomation> web=gc.getCourses().getWebAutomation();
        for (int j=0;j<web.size();j++)
        {
            a.add(web.get(j).getCourseTitle());
        }

        List<String> expectedList= Arrays.asList(title);
        Assert.assertTrue(a.equals(expectedList));



    }

}

