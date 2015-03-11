package si.merljak.magistrska.client.utils;

import si.merljak.magistrska.client.mvp.search.SearchPresenter;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Season;

import com.github.gwtbootstrap.client.ui.Badge;
import com.google.gwt.user.client.ui.Anchor;

/**
 * Utility class for creating bottstrap badges.
 * 
 * @author Jakob Merljak
 *
 */
public final class Badges {
	
	private Badges() {}
	
	/** Gets category anchor badge with link to search recipes by season. */
	public static Anchor getCategoryBadge(Category category) {
		Badge categoryBadge = new Badge(EnumUtils.localizeEnum(category));
		categoryBadge.addStyleName("category");
		categoryBadge.addStyleName("category-" + category.name().toLowerCase());
		categoryBadge.setWordWrap(false);
		Anchor categoryAnchor = new Anchor("", SearchPresenter.buildSearchByCategoryUrl(category));
		categoryAnchor.getElement().appendChild(categoryBadge.getElement());
		return categoryAnchor;
	}
	
	/** Gets season anchor badge with link to search recipes by season. */
	public static Anchor getSeasonBadge(Season season) {
		Badge seasonBadge = new Badge(EnumUtils.localizeEnum(season));
		seasonBadge.addStyleName("season");
		seasonBadge.addStyleName("season-" + season.name().toLowerCase());
		seasonBadge.setWordWrap(false);
		Anchor seasonAnchor = new Anchor("", SearchPresenter.buildSearchBySeasonUrl(season));
		seasonAnchor.getElement().appendChild(seasonBadge.getElement());
		return seasonAnchor;
	}
}
