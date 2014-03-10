package com.wikia.webdriver.TestCases.SearchTests;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.wikia.webdriver.Common.Clicktracking.ClickTrackingScriptsProvider;
import com.wikia.webdriver.Common.Clicktracking.Events.EventsCrossWikiSearch;
import com.wikia.webdriver.Common.Properties.Credentials;
import com.wikia.webdriver.Common.Templates.NewTestTemplate;
import com.wikia.webdriver.PageObjectsFactory.PageObject.Search.CrossWikiSearch.CrossWikiSearchPageObject;
import com.wikia.webdriver.PageObjectsFactory.PageObject.WikiPage.WikiArticleHomePage;

public class ClickTrackingCrossWikiSearchTests extends NewTestTemplate {

	Credentials credentials = config.getCredentials();

	@Test(groups = {
			"ClickTrackingCrossWikiSearchTests_001",
			"Search",
			"CrossWikiSearch",
			"ClickTracking"
	})
	public void ClicktrackingCrossWikiSearch_001_verifyEvents() {
		CrossWikiSearchPageObject search = new CrossWikiSearchPageObject(driver);
		search.goToSearchPage(wikiCorporateURL);
		search.executeScript(ClickTrackingScriptsProvider.trackerInstallation);
		search.searchForEnter("Mario");
		search.executeScript(ClickTrackingScriptsProvider.trackerInstallation);
		for (int i = 1; i < 7; i++) {
			WikiArticleHomePage home = search.openResult(i);
			search.executeScript(ClickTrackingScriptsProvider.trackerInstallation);
			home.navigateBack();
			search.executeScript(ClickTrackingScriptsProvider.trackerInstallation);
		}
		search.searchFor("noresultsatall");
		search.executeScript(ClickTrackingScriptsProvider.trackerInstallation);

		List<String> expectedEvents = Arrays.asList(
				EventsCrossWikiSearch.eventHitEnterCrossWikiSearchInput,
				EventsCrossWikiSearch.eventPushToTop,
				EventsCrossWikiSearch.eventFirstCrossWikiSearchResult,
				EventsCrossWikiSearch.eventSecondCrossWikiSearchResult,
				EventsCrossWikiSearch.eventThirdCrossWikiSearchResult,
				EventsCrossWikiSearch.eventFourthCrossWikiSearchResult,
				EventsCrossWikiSearch.eventFifthCrossWikiSearchResult,
				EventsCrossWikiSearch.eventSixthCrossWikiSearchResult,
				EventsCrossWikiSearch.eventClickSearchButton,
				EventsCrossWikiSearch.eventEmptySearchResultPage
		);

		search.compareTrackedEventsTo(expectedEvents);
	}
}