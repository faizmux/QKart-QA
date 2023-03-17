package QKART_SANITY_LOGIN.Module1;

import java.sql.Driver;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResult {
    WebElement parentElement;

    public SearchResult(WebElement SearchResultElement) {
        this.parentElement = SearchResultElement;
    }

    /*
     * Return title of the parentElement denoting the card content section of a search result
     */
    public <parentElement> String getTitleofResult() {
        String titleOfSearchResult = "";
        // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
        // Find the element containing the title (product name) of the search result and
        // assign the extract title text to titleOfSearchResult
        WebElement titleOfElement = parentElement.findElement(
                By.xpath(".//p[@class='MuiTypography-root MuiTypography-body1 css-yg30e6']"));
        titleOfSearchResult = titleOfElement.getText();
        return titleOfSearchResult;
    }

    /*
     * Return Boolean denoting if the open size chart operation was successful
     */
    public Boolean openSizechart() {
        try {

            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 04: MILESTONE 2
            // Find the link of size chart in the parentElement and click on it
            WebElement sizeChart =
                    parentElement.findElement(By.xpath(".//button[text()='Size chart']"));
            sizeChart.click();

            return true;
        } catch (Exception e) {
            System.out.println("Exception while opening Size chart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the close size chart operation was successful
     */
    public Boolean closeSizeChart(WebDriver driver) {
        try {
            Thread.sleep(2000);
            Actions action = new Actions(driver);

            // Clicking on "ESC" key closes the size chart modal
            action.sendKeys(Keys.ESCAPE);
            action.perform();
            Thread.sleep(2000);
            return true;
        } catch (Exception e) {
            System.out.println("Exception while closing the size chart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean based on if the size chart exists
     */
    public Boolean verifySizeChartExists() {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 04: MILESTONE 2
            /*
             * Check if the size chart element exists. If it exists, check if the text of the
             * element is "SIZE CHART". If the text "SIZE CHART" matches for the element, set status
             * = true , else set to false
             */
            WebElement sizeChart =
                    parentElement.findElement(By.xpath(".//button[text()='Size chart']"));
            if (sizeChart.isDisplayed()) {
                String expectedText = "Size chart";
                String actualText = sizeChart.getText();
                if (expectedText.equalsIgnoreCase(actualText)) {
                    status = true;
                }
            }
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if the table headers and body of the size chart matches the expected values
     */
    public Boolean validateSizeChartContents(List<String> expectedTableHeaders,
            List<List<String>> expectedTableBody, WebDriver driver) {
        Boolean status = true;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 04: MILESTONE 2
            /*
             * Locate the table element when the size chart modal is open
             * 
             * Validate that the contents of expectedTableHeaders is present as the table header in
             * the same order
             * 
             * Validate that the contents of expectedTableBody are present in the table body in the
             * same order
             */
            List<WebElement> tableHeaderElements =
                    driver.findElements(By.xpath("//table/thead//th"));
            for (int i = 0; i < expectedTableHeaders.size(); i++) {

                WebElement tableHeader = tableHeaderElements.get(i);
                String actualHeaderText = tableHeader.getText();
                String expectedHeaderText = expectedTableHeaders.get(i);
                if (!actualHeaderText.equals(expectedHeaderText)) {
                    status = false;
                }
            }

            // table/tbody/tr[1]/td[2]
            for (int i = 0; i < expectedTableBody.size(); i++) {

                List<String> expectedColumns = expectedTableBody.get(i);
                for (int j = 0; j < expectedColumns.size(); j++) {
                    int row = i + 1;
                    int column = j + 1;

                    WebElement actualTableBody = driver.findElement(
                            By.xpath("//table/tbody/tr[" + row + "]/td[" + column + "]"));
                    String expectedColumnText = expectedColumns.get(j);
                    String actualColumnText = actualTableBody.getText();
                    if (!actualColumnText.equals(expectedColumnText)) {
                        status = false;
                    }
                }
            }



            return status;

        } catch (Exception e) {
            System.out.println("Error while validating chart contents");
            return false;
        }
    }

    /*
     * Return Boolean based on if the Size drop down exists
     */
    public Boolean verifyExistenceofSizeDropdown(WebDriver driver) {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 04: MILESTONE 2
            // If the size dropdown exists and is displayed return true, else return false
            WebElement sizeDropdown =
                    driver.findElement(By.xpath("//select[@id='uncontrolled-native']"));
            if (sizeDropdown.isDisplayed()) {
                status = true;
            }
            return status;
        } catch (Exception e) {
            return status;
        }
    }
}
