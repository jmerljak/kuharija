package si.merljak.magistrska.client.mvp;

import java.util.List;

import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.RecipeListDto;

import com.github.gwtbootstrap.client.ui.Heading;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class SearchView extends AbstractView {

	private static final RootPanel main = RootPanel.get("searchWrapper");
	
	private TextBox searchBox = new TextBox();
	private Button searchButton = new Button(constants.search());
	private FlowPanel resultsPanel = new FlowPanel();

	public SearchView () {
		main.add(new Heading(2, constants.search()));
		main.add(resultsPanel);
		initWidget(main);

		RootPanel formPanel = RootPanel.get("searchField");
		formPanel.setStyleName("input-append");
		formPanel.add(searchBox);
		formPanel.add(searchButton);

		searchBox.getElement().setId("appendedInputButtons");
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					doSearch();
				}
			}
		});
		searchButton.setStyleName("btn");
		searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doSearch();
			}
		});
	}

	private void doSearch() {
		SearchPresenter.doSearch(searchBox.getValue());
	}

	public void displaySearchResults(RecipeListDto results, SearchParameters parameters) {
		// clear old data
		resultsPanel.clear();

		// display search string, filters, sorting
		searchBox.setText(parameters.getSearchString());
		// TODO filters, sorting

		List<RecipeDto> recipes = results.getRecipes();
		if (recipes.isEmpty()) {
			resultsPanel.add(new Label(constants.searchNoResults()));
		}

		for (RecipeDto recipe : recipes) {
			Image image = new Image(RECIPE_THUMB_IMG_FOLDER + recipe.getImageUrl());
			image.setAltText(recipe.getHeading());
			Anchor link = new Anchor(recipe.getHeading(), RecipePresenter.buildRecipeUrl(recipe.getId()));

			FlowPanel resultEntry = new FlowPanel();
			resultEntry.setStyleName("resultEntry");
			resultEntry.add(link);
			resultEntry.add(image);
			resultEntry.add(new Label(localizeEnum(recipe.getDifficulty())));
			resultEntry.add(new Label(timeFromMinutes(recipe.getTimeOverall())));

			resultsPanel.add(resultEntry);
		}

		// paging
		long pageCount = (results.getAllCount() - 1) / parameters.getPageSize() + 1;
		resultsPanel.add(new Label("page " + parameters.getPage() + " of " + pageCount));

		setVisible(true);
	}

	public void clearSearchResults() {
		searchBox.setText("");
		resultsPanel.clear();
		setVisible(true);
	}
}
