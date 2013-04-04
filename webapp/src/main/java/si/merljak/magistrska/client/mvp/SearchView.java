package si.merljak.magistrska.client.mvp;

import java.util.List;

import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.RecipeDto;

import com.github.gwtbootstrap.client.ui.Heading;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
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
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				Window.alert("aaa");
//				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//					String searchString = searchBox.getValue().trim();
//					History.newItem("search&q=" + searchString);
//				}
			}
		});
		searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("bbb");
				String searchString = searchBox.getValue().trim();
				History.newItem("search&q=" + searchString);
			}
		});

		main.add(new Heading(2, constants.search()));
		main.add(searchBox);
		main.add(searchButton);
		main.add(resultsPanel);
		initWidget(main);
	}

	public void displaySearchResults(List<RecipeDto> results, SearchParameters parameters) {
		searchBox.setText(parameters.getSearchString());
		resultsPanel.clear();

		if (results.isEmpty()) {
			resultsPanel.add(new Label(constants.searchNoResults()));
		}

		for (RecipeDto result : results) {
			Image image = new Image(RECIPE_IMG_FOLDER + result.getImageUrl());
			Anchor link = new Anchor(result.getHeading(), RecipePresenter.buildRecipeUrl(result.getId()));

			FlowPanel recipe = new FlowPanel();
			recipe.setStyleName("resultEntry");
			recipe.add(link);
			recipe.add(image);
			recipe.add(new Label(localizeEnum(result.getDifficulty())));
			recipe.add(new Label(result.getTimeOverall()));
			resultsPanel.add(recipe);
		}
		setVisible(true);
	}

	public void clearSearchResults() {
		searchBox.setText("");
		resultsPanel.clear();
		setVisible(true);
	}
}
