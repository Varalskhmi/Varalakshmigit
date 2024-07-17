package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void actSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void actPageTitleTest() {
		Assert.assertEquals(accPage.getAccPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}

	@Test
	public void accPageURLTest() {
		Assert.assertTrue(accPage.getAccPageURL().contains(AppConstants.ACC_PAGE_FRACTION_URL), AppError.URL_NOT_FOUND);
	}


	@Test
	  public void accPageHeadersTest() {
        List<String> accPageHeadersList = accPage.getAccPageHeaders();
        Assert.assertEquals(accPageHeadersList, AppConstants.ACC_PAGE_HEADERS_LIST, AppError.LIST_IS_NOT_MATCHED);
    }
	
	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] {
			{"macbook", 3},
			{"imac", 1},
			{"samsung", 2},
			{"Airtel", 0}
		};
	}
	
	
	@Test(dataProvider="getSearchData")
	public void searchTest(String searchkey,int resultsCount)
	{
		searchResultsPage = accPage.doSearch("macbook");
		Assert.assertEquals(searchResultsPage.getSearchResultsCount(),3,AppError.RESULTS_COUNT_MISMATCHED);
	}

}
