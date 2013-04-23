package si.merljak.magistrska.client.mvp;

import java.util.List;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.RecipeListDto;

import com.github.gwtbootstrap.client.ui.Form;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SearchView extends AbstractView {

	private TextBox searchBox = new TextBox();
	private FlowPanel resultsPanel = new FlowPanel();

	public SearchView () {
		searchBox.getElement().setId("appendedInputButtons");
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					doSearch();
				}
			}
		});

		Button searchButton = new Button(constants.search());
		searchButton.setStyleName("btn");
		searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doSearch();
			}
		});

		Form formPanel = new Form();
		formPanel.setStyleName("input-append");
		formPanel.add(searchBox);
		formPanel.add(searchButton);

		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.search()));
		main.add(formPanel);
		main.add(resultsPanel);
		initWidget(main);
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
			String heading = recipe.getHeading();
			String imageUrl = recipe.getImageUrl();

			imageUrl = imageUrl != null ? RECIPE_THUMB_IMG_FOLDER + imageUrl : ""; // TODO
			Image image = new Image(imageUrl);
			image.setAltText(heading);

			Anchor link = new Anchor(heading, RecipePresenter.buildRecipeUrl(recipe.getId()));
			link.getElement().appendChild(image.getElement());

			FlowPanel resultEntry = new FlowPanel();
			resultEntry.setStyleName("resultEntry");
			resultEntry.add(link);
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

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.search());
		return super.asWidget();
	}
}
