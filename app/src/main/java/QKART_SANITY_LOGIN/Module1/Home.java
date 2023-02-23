package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.hc.client5.http.impl.cookie.PublicSuffixDomainFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app";

    public Home(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
            logout_button.click();

            // Wait for Logout to Complete
            Thread.sleep(3000);

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any errors
     */
    public Boolean searchForProduct(String product) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Clear the contents of the search box and Enter the product name in the search
            // box
            WebElement searchBox = driver.findElement(
                    By.xpath("//div[@class='header MuiBox-root css-0']/div[2]/div[1]//input"));
            searchBox.clear();
            searchBox.sendKeys(product);
            Thread.sleep(3000);
            return true;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /*
     * Returns Array of Web Elements that are search results and return the same
     */
    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>() {};
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Find all webelements corresponding to the card content section of each of
            // search results
            searchResults = driver
                    .findElements(By.xpath("//div[@class='MuiCardContent-root css-1qw96cp']"));
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;

        }
    }

    /*
     * Returns Boolean based on if the "No products found" text is displayed
     */
    public Boolean isNoResultFound() {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Check the presence of "No products found" text in the web page. Assign status
            // = true if the element is *displayed* else set status = false
            WebElement noProductFoundElement =
                    driver.findElement(By.xpath("//h4[text()=' No products found ']"));
            if (noProductFoundElement.isDisplayed()) {
                String expectedText = " No products found ";
                String actualText = noProductFoundElement.getText();
                if (actualText.equals(expectedText)) {
                    status = true;
                }
            }
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if add product to cart is successful
     */
    public Boolean addProductToCart(String productName) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Iterate through each product on the page to find the WebElement corresponding to the
             * matching productName
             * 
             * Click on the "ADD TO CART" button for that element
             * 
             * Return true if these operations succeeds
             */
            List<WebElement> productNameElements = driver.findElements(
                    By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 css-yg30e6']"));
            List<WebElement> addToCartButtonElements =
                    driver.findElements(By.xpath("//button[text()='Add to cart']"));

            for (int i = 0; i < productNameElements.size(); i++) {

                WebElement productNameWebElement = productNameElements.get(i);
                String actualProductName = productNameWebElement.getText();

                if (actualProductName.equals(productName)) {
                    WebElement addToCartButton = addToCartButtonElements.get(i);
                    addToCartButton.click();

                }
            }

            // System.out.println("Unable to find the given product");
            return false;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of clicking on the checkout button
     */
    public Boolean clickCheckout() {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find and click on the the Checkout button
            WebElement checkOutButton = driver.findElement(By.xpath("//button[text()='Checkout']"));
            checkOutButton.click();
            return status;
        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout: " + e.getMessage());
            return status;
        }
    }

    /*
     * Return Boolean denoting the status of change quantity of product in cart operation
     */
    public Boolean changeProductQuantityinCart(String productName, int expectedQuantity) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 06: MILESTONE 5

            // Find the item on the cart with the matching productName

            // Increment or decrement the quantity of the matching product until the current
            // quantity is reached (Note: Keep a look out when then input quantity is 0,
            // here we need to remove the item completely from the cart)
            Thread.sleep(3000);
            List<WebElement> productNameElements =
                    driver.findElements(By.xpath("//div[@class='MuiBox-root css-1gjj37g']/div[1]"));
            List<WebElement> currentQuantityElements =
                    driver.findElements(By.xpath("//div[@data-testid='item-qty']"));
            List<WebElement> decreaseButtonElements =
                    driver.findElements(By.xpath("//*[@data-testid='RemoveOutlinedIcon']"));
            List<WebElement> increaseButtonElements =
                    driver.findElements(By.xpath("//*[@data-testid='AddOutlinedIcon']"));

            for (int i = 0; i < productNameElements.size(); i++) {
                WebElement productNameElement = productNameElements.get(i);
                String actualTitle = productNameElement.getText();
                if (actualTitle.equals(productName)) {

                    while (true) {
                        WebElement currentQuantityElement = currentQuantityElements.get(i);
                        String currentQuantityString = currentQuantityElement.getText();
                        int currentQuantity = Integer.parseInt(currentQuantityString);

                        if (currentQuantity == expectedQuantity) {
                            break;
                        }

                        if (expectedQuantity < currentQuantity) {
                            WebElement decreaseButtonElement = decreaseButtonElements.get(i);
                            decreaseButtonElement.click();
                        } else if (expectedQuantity > currentQuantity) {
                            WebElement increaseButtonElement = increaseButtonElements.get(i);
                            increaseButtonElement.click();
                        }
                        Thread.sleep(2000);
                    }
                }
            }

            return false;
        } catch (Exception e) {
            if (expectedQuantity == 0)
                return true;
            System.out.println("exception occurred when updating cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the cart contains items as expected
     */
    public Boolean verifyCartContents(List<String> expectedCartContents) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 07: MILESTONE 6

            // Get all the cart items as an array of webelements

            // Iterate through expectedCartContents and check if item with matching product
            // name is present in the cart
            List<WebElement> productCartElements =
                    driver.findElements(By.xpath("//div[@class='MuiBox-root css-1gjj37g']/div[1]"));
            for (int i = 0; i < productCartElements.size(); i++) {
                WebElement productElement = productCartElements.get(i);
                String actualTitleText = productElement.getText();
                String expectedTitleText = expectedCartContents.get(i);
                if (!actualTitleText.equals(expectedTitleText)) {
                    return false;
                }
            }
            return true;

        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }
}
