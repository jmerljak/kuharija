package si.merljak.magistrska.client.mvp.recipe;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.search.SearchPresenter;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.Season;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * Simple recipe index view.
 * 
 * @author Jakob Merljak
 * 
 */
public class RecipeIndexView extends AbstractView {

	public RecipeIndexView() {
		UnorderedList categories = new UnorderedList();
		for (Category category : Category.values()) {
			categories.add(new ListItem(new Anchor(localizeEnum(category), SearchPresenter.buildSearchByCategoryUrl(category))));
		}

		UnorderedList seasons = new UnorderedList();
		for (Season season : Season.values()) {
			seasons.add(new ListItem(new Anchor(localizeEnum(season), SearchPresenter.buildSearchBySeasonUrl(season))));
		}

		UnorderedList difficulties = new UnorderedList();
		for (Difficulty difficulty : Difficulty.values()) {
			difficulties.add(new ListItem(new Anchor(localizeEnum(difficulty), SearchPresenter.buildSearchByDifficultyUrl(difficulty))));
		}

		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.recipes()));
		main.add(new HTML(messages.browseOrSearchRecipes()));
		main.add(new Heading(HEADING_SIZE + 1, constants.categories()));
		main.add(categories);
		main.add(new Heading(HEADING_SIZE + 1, constants.seasons()));
		main.add(seasons);
		main.add(new Heading(HEADING_SIZE + 1, constants.recipesByDifficulty()));
		main.add(difficulties);
		initWidget(main);
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.recipes());
		return super.asWidget();
	}
}
