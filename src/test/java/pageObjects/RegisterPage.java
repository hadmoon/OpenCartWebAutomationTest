package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePage {

	public RegisterPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-firstname']")
	private WebElement txtFirstName;

	@FindBy(xpath = "//input[@id='input-lastname']")
	private WebElement txtLastName;

	@FindBy(xpath = "//input[@id='input-email']")
	private WebElement txtEmail;

	@FindBy(xpath = "//input[@id='input-password']")
	private WebElement txtPassword;

	@FindBy(xpath = "//input[@id='input-newsletter']")
	private WebElement tglNewsletter;

	@FindBy(xpath = "//input[@name='agree']")
	private WebElement tglAgree;

	@FindBy(xpath = "//button[normalize-space()='Continue']")
	private WebElement btnContinue;

	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")  
	private WebElement msgConfirmation;

	public void setFirstName(String firstName) {
		txtFirstName.sendKeys(firstName);
	}

	public void setLastName(String lastName) {
		txtLastName.sendKeys(lastName);
	}

	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}

	public void setPassword(String password) {
		txtPassword.sendKeys(password);
	}

	public void setNewsletter() {
		if (!tglNewsletter.isSelected()) {
			tglNewsletter.click();
		}
	}

	public void setArgee() {
		if (!tglAgree.isSelected()) {
			tglAgree.click();
		}
	}

	public void clickContinue() {
		// solution1
		btnContinue.click();

		// solution2
		// btnContinue.submit();

		// solution3
		// Actions act = new Action(driver)
		// act.moveToElement(btnContinue).click().perform();

		// solution4
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		// js.executeScript("arguments[0].click();", btnContinue);

		// solution5
		// btnContinue.sendKeys(Keys.RETURN);

		// solution6
		// WebDriverWait myWait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// myWait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
	}
	
	public String getConfirmationMsg() {
		try {
			return (msgConfirmation.getText());
		}catch (Exception e) {
			return (e.getMessage());
		}
	}

}
