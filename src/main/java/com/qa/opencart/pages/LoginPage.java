package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	//1.Page Objects : By Locators
	
	private By emailId=By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn=By.xpath("//input[@value='Login']");
	private By forgotPwdLink=By.linkText("Forgotten Password");
	private By registerLink=By.linkText("Register");
	
	//2. public constructor of the page
	public LoginPage(WebDriver driver)
	{
		this .driver=driver;
		eleUtil =new ElementUtil(driver);
	}
	
	//3. public page Actions/Methods{features of this paticular page}
	public 	String getLoginPageTitle()
	{
		String title=eleUtil.waitForTitleToBe(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_TIME);
		System.out.println("login page title:"+title);
		return title;
	}
	public 	String getLoginPageURL()
	{
		String url=eleUtil.waitForURLContains(AppConstants.ACC_PAGE_FRACTION_URL,TimeUtil.DEFAULT_TIME);
		System.out.println("login page title:"+url);
		return url;
	}
	
	public boolean checkForgotPwdLinkExists()
	{
		return eleUtil.doIsDisplayed(forgotPwdLink);
		
	}
	public AccountsPage doLogin(String username,String pwd)
	{
		
		eleUtil.doSendKeys(emailId,username,TimeUtil.DEFAULT_MEDIUM_TIME);
		eleUtil.doSendkeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
//		String title= driver.getTitle();
//		System.out.println("Account page title:"+title);
//		return title;
	}
	public RegisterPage navigateToRegisterPage()
	{
		eleUtil.doClick(registerLink,TimeUtil.DEFAULT_TIME);
		return new RegisterPage(driver);
	}
	
	
}
