package OAuthTest;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class OAutho {

    public static void main(String[] args) throws InterruptedException {

       /*System.setProperty("webdriver.chrome.driver","driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("\"https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php\"");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("anands.220193");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("xxxx");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
         String url= driver.getCurrentUrl();*/

        String url= "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AX4XfWhqpoJeyZ2XcGDScueDN7OritSlg63kAocQAYTcjL4etzua8mNczFWGH0iol13N6Q&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
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


        String response= given().
                queryParam("access_token",accessToken).
                when().log().all().
                get("https://rahulshettyacademy.com/getCourse.php").
                asString();

        System.out.println(response);


    }

}

