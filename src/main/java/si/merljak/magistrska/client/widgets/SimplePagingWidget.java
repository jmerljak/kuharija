package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.handler.PagingHandler;
import si.merljak.magistrska.client.i18n.CommonConstants;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.constants.IconPosition;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Simple paging widget (just back and forward).
 * 
 * @author Jakob Merljak
 * 
 */
public class SimplePagingWidget extends Composite {

	// i18n
	private final CommonConstants constants = Kuharija.constants;

	// handler
	private PagingHandler handler;

	// widgets
	private final Button previousPageAnchor = new Button();
	private final Button nextPageAnchor = new Button();

	// variables
	private int page = 0;
	private int allPages = 1;

	public SimplePagingWidget(PagingHandler pagingHandler) {
		this.handler = pagingHandler;

		previousPageAnchor.setIcon(IconType.CHEVRON_LEFT);
		previousPageAnchor.setText(constants.stepPrevious());
		previousPageAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					handler.changePage(page - 1);
					setPage(--page, allPages);
				}
			}
		});

		nextPageAnchor.setIcon(IconType.CHEVRON_RIGHT);
		nextPageAnchor.setIconPosition(IconPosition.RIGHT);
		nextPageAnchor.setType(ButtonType.SUCCESS);
		nextPageAnchor.setText(constants.stepNext());
		nextPageAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					handler.changePage(page + 1);
					setPage(++page, allPages);
				}
			}
		});

		FlowPanel main = new FlowPanel();
		main.setStyleName("pagingWidget");
		main.add(previousPageAnchor);
		main.add(nextPageAnchor);
		initWidget(main);
	}

	/**
	 * Sets current page and enables appropriate paging buttons.
	 * 
	 * @param page current page
	 * @param pageSize page size
	 * @param allCount all results count (regardless page size)
	 * 
	 */
	public void setPage(int page, int allCount) {
		this.page = page;
		this.allPages = allCount;

		previousPageAnchor.setEnabled(page > 0);
		if (page > 0) {
			previousPageAnchor.removeStyleName(Constants.DISABLED);
			Roles.getLinkRole().setAriaDisabledState(previousPageAnchor.getElement(), false);
		} else {
			previousPageAnchor.addStyleName(Constants.DISABLED);
			Roles.getLinkRole().setAriaDisabledState(previousPageAnchor.getElement(), true);
		}

		nextPageAnchor.setEnabled(page < allPages - 1);
		if (page < allPages - 1) {
			nextPageAnchor.removeStyleName(Constants.DISABLED);
			Roles.getLinkRole().setAriaDisabledState(nextPageAnchor.getElement(), false);
		} else {
			nextPageAnchor.addStyleName(Constants.DISABLED);
			Roles.getLinkRole().setAriaDisabledState(nextPageAnchor.getElement(), true);
		}
	}

	public boolean hasPrevious() {
		return page > 0;
	}

	public boolean hasNext() {
		return page < allPages - 1;
	}

	public int getPage() {
		return page;
	}

	public int getAllCount() {
		return allPages;
	}
}
