package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.errors.AppError;

public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void productInfoPageTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));

	}

	@DataProvider()
	public Object[][] getProductData()
	{
		return new Object[][] {
			{"macbook","MacBook Pro"},
			{"imac","iMac"},
			{"samsung","Samsung SyncMaster 941BW"},
			{"samsung","Samsung Galaxy Tab 10.1"},
			{"canon","Canon EOS 5D"},

		};
	}
	@Test(dataProvider="getProductData")
	public void productHeaderTest(String searchKey,String productName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeader(),productName ,AppError.HEADER_NOT_FOUND);
	}
	
	
	@DataProvider()
	public Object[][] getProductImageData()
	{
		return new Object[][] {
			{"macbook","MacBook Pro",4},
			{"imac","iMac",3},
			{"samsung","Samsung SyncMaster 941BW",1},
			{"samsung","Samsung Galaxy Tab 10.1",7},
			{"canon","Canon EOS 5D",3},

		};
	}
	
	@Test(dataProvider="getProductImageData")
	public void productImagesCountTest(String searchKey,String productName,int imagesCount)
	{
		searchResultsPage=accPage.doSearch(searchKey);
		productInfoPage=searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductImagesCount(),imagesCount,AppError.IMAGES_COUNT_MISMATCHED);
	}
	
	@Test
	public void productInfoTest()
	{
		searchResultsPage=accPage.doSearch("macbook");
		productInfoPage=searchResultsPage.selectProduct("MacBook Pro");
		Map<String,String> productInfoMap=productInfoPage.getProductInfoMap();
		System.out.println("===========product Information===============");
		System.out.println(productInfoMap);
		
		softAssert.assertEquals(productInfoMap.get("productname"), "MacBook Pro");//F
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");//P
		softAssert.assertEquals(productInfoMap.get("Reward Points"), "800");//P
		softAssert.assertEquals(productInfoMap.get("Availability"), "In Stock");//F
		softAssert.assertEquals(productInfoMap.get("productprice"), "$2,000.00");//P
		softAssert.assertEquals(productInfoMap.get("exTaxPrice"), "$2,000.00");//F
		
		softAssert.assertAll();//Failure Info(4)
	}
}


//Assert vs verify
	//hard assert(Assert) vs soft assert(verify - SoftAssert)
	//Assert---> methods (static)
	//SoftAssert ---> methods (non static)
	
	//single assertion -- hard assertion
	//multiple assertions -- soft assertion
	
	
	
	
	

	//act vs exp: Assert.assertEquals(act, exp)
	//Assert.assertTrue(10>5)
	//Assert.assertTrue(title.contains("Google123")) ===> +ve
	//Assert.assertFalse(false)
	//Assert.assertFalse(5>10) --- -ve