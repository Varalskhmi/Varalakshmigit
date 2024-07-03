package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.ElementException;



public class ElementUtil {
	
	private WebDriver driver;
	public ElementUtil(WebDriver driver)
	{
		this.driver=driver;
	}
	
	private void nullCheck(String value)
	{
		if(value==null)
		{
			throw new ElementException("value is null:"+value);
		}
	}
	public void doSendkeys(By locator,String value)
	{
		nullCheck(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}
	
	public void doSendKeys(By locator, String value, int timeOut) {
		nullCheck(value);
		waitForElementVisible(locator, timeOut).clear();
		waitForElementVisible(locator, timeOut).sendKeys(value);
	}
	
	
	public void doSendKeys(By locator, CharSequence... value) {
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}
	
	public  WebElement getElement(By locator)
	{
		try {
			WebElement element=driver.findElement(locator);
			return element;
			
		}catch(NoSuchElementException e){
			System.out.println("Element is not present on the page...."+locator);
			e.printStackTrace();
			return null;
			
		}
		
	}
	
	public void doClick(By locator)
	{
		getElement(locator).click();
	}
	
	public void doClick(By locator, int timeOut) {
		waitForElementVisible(locator, timeOut).click();
	}
	public String doGetText(By locator)
	{
		return getElement(locator).getText();
	}


	public String doGetAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public boolean doIsDisplayed(By locator) {
		try {
			boolean flag = getElement(locator).isDisplayed();
			System.out.println("element is displayed: " + locator);
			return flag;
		} catch (NoSuchElementException e) {
			System.out.println("element with locator : " + locator + " is not displayed");
			return false;
		}

	}

	public boolean isElementDisplayed(By locator) {
		int elementCount = getElements(locator).size();
		if (elementCount == 1) {
			System.out.println("single element is displayed: " + locator);
			return true;
		} else {
			System.out.println("multiple or zero elements are displayed: " + locator);
			return false;
		}
	}

	public boolean isElementDisplayed(By locator, int expectedElementCount) {
		int elementCount = getElements(locator).size();
		if (elementCount == expectedElementCount) {
			System.out.println("element is displayed: " + locator + " with the occurrence of " + elementCount);
			return true;
		} else {
			System.out.println(
					"multiple or zero elements are displayed: " + locator + " with the occurrence of " + elementCount);
			return false;
		}
	}
	
	
	public  List<WebElement> getElements(By locator)
	{
		return driver.findElements(locator);
	}
	public  int getElementCount(By locator)
	{
		return getElements(locator).size();
	}

	//WAF : to fetch  the text of each link , store it in some list and return . text should not be  a blnk
	//name: getElementTextList
	//param: By locator
	//return : List<String>
	
	public  List<String> getElementsTextList(By locator)
	{
		List<WebElement> eleList=getElements(locator);
		List<String> eleTextList=new ArrayList<String>();
		for(WebElement e:eleList)
		{
			String text=e.getText();
			if(text.length()!=0)
			{
				eleTextList.add(text);
			}
		}
		return eleTextList;
		
	}
	public List<String> getElementAttributeList(By locator,String attrName)
	{
		List<WebElement> imagesList=getElements(locator);
		List<String> attrList=new ArrayList<String>();
		for(WebElement e: imagesList)
		{
			String attrVal=e.getAttribute(attrName);
			if(attrVal!=null)
			{
			System.out.println(attrVal);
		}
	}
		return attrList;
}
	
	//***************Select dropdownlist utils*********************
	public  void doSelectByIndex(By locator,int index)
	{
		Select select=new Select(getElement(locator));
		select.selectByIndex(index);
	}
	public void doSelectByValue(By locator,String value)
	{
		Select select=new Select(getElement(locator));
		select.selectByValue(value);
	}
	public void doSelectByVisibleText(By locator,String visibleText)
	{
		Select select=new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}
	
	//**************Select dropdrownOptions
	
	public  int getDropDrownOptionsCount(By locator)
	{
		Select select =new Select(driver.findElement(locator));
		return select.getOptions().size();
	}
	public  List<String> getDropDownOptionsTextList(By locator) {

		Select select = new Select(driver.findElement(locator));
		List<WebElement> optionList = select.getOptions();
		List<String> optionTextList = new ArrayList<String>();

		for (WebElement e : optionList) {
			String text = e.getText();
			optionTextList.add(text);
			// System.out.println(text);
		}
		return optionTextList;
	}
	public void selectValueFromDropDown(By locator,String optionText)
	{
		Select select=new Select(getElement(locator));
		List<WebElement> optionsList=select.getOptions();
		for(WebElement e:optionsList)
		{
			String text=e.getText();
			if(text.equals(optionText.trim()))
			{
				e.click();
				break;
			}
		}
	}
		
		public void selectValueFromDropDownwithoutselect(By locator,String optionText)
		{
			List<WebElement> optionsList=getElements(locator);
			for(WebElement e: optionsList)
			{
				String text=e.getText();
				if(text.equals(optionText))
				{
					e.click();
					break;
				}
			}
			
		}
		//************google search*******
		public void doSearch(By searchField, String searchKey, By suggestions, String value) throws InterruptedException {
			doSendkeys(searchField, searchKey);
			Thread.sleep(3000);
			List<WebElement> suggList = getElements(suggestions);
			System.out.println(suggList.size());
			for (WebElement e : suggList) {
				String text = e.getText();
				System.out.println(text);
				if (text.contains(value)) {
					e.click();
					break;
				}
			}
		}
		
		//*****************Actions utils**************
		public  void handleParentSubMenu(By parentLocator, By childLocator) throws InterruptedException {

			Actions act = new Actions(driver);

			act.moveToElement(getElement(parentLocator)).perform();

			Thread.sleep(2000);

			doClick(childLocator);

		}
		
		public void doDragAndDrop(By sourcelocator,By targetlocator)
		{
			Actions act=new Actions(driver);
			act.dragAndDrop(getElement(sourcelocator), getElement(targetlocator)).perform();
		}
		public void doActionsSendKeys(By locator, String value) {
			Actions act = new Actions(driver);
			act.sendKeys(getElement(locator), value).perform();
		}
		
		public void doActionsClick(By locator) {
			Actions act = new Actions(driver);
			act.click(getElement(locator)).perform();
		}
	
		/**
		 * This method is used to enter the value in the text field with a pause.
		 * @param locator
		 * @param value
		 * @param pauseTime
		 */
		public void doActionsSendKeysWithPause(By locator, String value, long pauseTime) {
			Actions act = new Actions(driver);
			char ch[] = value.toCharArray();
			for (char c : ch) {
				act.sendKeys(getElement(locator), String.valueOf(c)).pause(pauseTime).perform();
			}
		}
		
		/**
		 * This method is used to enter the value in the text field with a pause of 500 ms (by default).
		 * @param locator
		 * @param value
		 */
		
		public void doActionsSendKeysWithPause(By locator, String value) {
			Actions act = new Actions(driver);
			char ch[] = value.toCharArray();
			for (char c : ch) {
				act.sendKeys(getElement(locator), String.valueOf(c)).pause(500).perform();
			}
		}

		public void level4MenuSubMenuHandlingUsingClick(By level1, String level2, String level3, String level4)
				throws InterruptedException {

			doClick(level1);
			Thread.sleep(1000);

			Actions act = new Actions(driver);
			act.moveToElement(getElement(By.linkText(level2))).perform();
			Thread.sleep(1000);
			act.moveToElement(getElement(By.linkText(level3))).perform();
			Thread.sleep(1000);
			doClick(By.linkText(level4));
		}

		public void level4MenuSubMenuHandlingUsingClick(By level1, By level2, By level3, By level4)
				throws InterruptedException {

			doClick(level1);
			Thread.sleep(1000);

			Actions act = new Actions(driver);
			act.moveToElement(getElement(level2)).perform();
			Thread.sleep(1000);
			act.moveToElement(getElement(level3)).perform();
			Thread.sleep(1000);
			doClick(level4);

		}

		public void level4MenuSubMenuHandlingUsingMouseHover(By level1, By level2, By level3, By level4)
				throws InterruptedException {

			Actions act = new Actions(driver);

			act.moveToElement(getElement(level1)).perform();
			Thread.sleep(1000);

			act.moveToElement(getElement(level2)).perform();
			Thread.sleep(1000);
			act.moveToElement(getElement(level3)).perform();
			Thread.sleep(1000);
			doClick(level4);

		}
		
		
		//***************Wait Utils**************
		
		// *******************Wait Utils***************//

		/**
		 * An expectation for checking that an element is present on the DOM of a page.
		 * This does not necessarily mean that the element is visible.
		 * 
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public WebElement waitForElementPresence(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		}
				
		public WebElement waitForElementVisible(By locator, int timeOut, int intervalTime) {
			
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(intervalTime))
					.ignoring(NoSuchElementException.class)
					.withMessage("===element is not found===");


			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			
		}
		
		
		
		/**
		 * An expectation for checking that there is at least one element present on a web page.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public List<WebElement> waitForPresenceOfElemenetsLocated(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}
		
		/**
		 * An expectation for checking that all elements present on the web page that match the locator are visible.
		 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public List<WebElement> waitForVisibilityOfElemenetsLocated(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
				return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
			}
			catch(Exception e)
			{
				return List.of();//return empty rrayList
			}
		}
		public void doClickWithWait(By locator,int timeOut)
		{
			waitForElementVisible(locator,timeOut).click();
		}
		
		
		/**
		 * An expectation for checking that an element is present on the DOM of a page and visible.
		 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public  WebElement waitForElementVisible(By locator, int timeOut)
		{
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		/**
		 * An expectation for checking an element is visible and enabled such that you
		 * can click it.
		 * 
		 * @param locator
		 * @param timeOut
		 */
		public void clickWhenReady(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		}
		
		//*****************for title************************
		public String waitForTitleContains(String titleFraction, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

			try {
				if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
					return driver.getTitle();
				}
			} catch (TimeoutException e) {
				System.out.println("title not found");
			}
			return driver.getTitle();
		}

		public String waitForTitleToBe(String titleVal, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

			try {
				if (wait.until(ExpectedConditions.titleIs(titleVal))) {
					return driver.getTitle();
				}
			} catch (TimeoutException e) {
				System.out.println("title not found");
			}
			return driver.getTitle();
		}
		
		//**************FOr URL****************
		public  String waitForURLContains(String urlFraction, int timeOut)
		{
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));

			try
			{
				if(wait.until(ExpectedConditions.urlContains(urlFraction)))
				{
					return driver.getCurrentUrl();
				}
			}
			catch(TimeoutException e){
				System.out.println("URL not found");			
			}
			return driver.getCurrentUrl();
		}
		
		public  String waitForURLToBe(String urlValue,int timeOut)
		{
			WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			try {
				if(wait.until(ExpectedConditions.urlToBe(urlValue)))
				{
					return driver.getCurrentUrl();
				}
			}
			catch(TimeoutException e)
			{
				System.out.println("URL not found");
			}
			return driver.getCurrentUrl();
		}
		
		
		//**************Frame********************
//		public static void waitForFrameBtLocator(By frameLocator,int timeOut)
//		{
//			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
//			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
//		}
//		public static void waitForFrameByIndex(int frameIndex,int timeOut)
//		{
//			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
//			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
//		}
//		public static void waitForFrameByIndex(String frameIDOrName,int timeOut)
//		{
//			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
//			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDOrName));
//		}
//		public static void waitForFrameByIndex(WebElement frameElement,int timeOut)
//		{
//			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
//			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
//		}
		
		
		
		//***********for windows*********
		public  boolean waitForWindowsToBe(int totalWindows,int timeOut)
		{
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindows));
			
		}
		
		public void isPageLoaded(int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'")).toString();//"true"
			
				if(Boolean.parseBoolean(flag)) {
					System.out.println("page is completely loaded");
				}
				else {
					throw new RuntimeException("page is not loaded");
				}
		}
		
		//click
		//isPageLoaded -- new page
}
