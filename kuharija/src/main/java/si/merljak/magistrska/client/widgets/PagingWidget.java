package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.handler.PagingHandler;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.CommonMessages;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.base.InlineLabel;
import com.github.gwtbootstrap.client.ui.base.TextBox;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.constants.IconPosition;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.aria.client.Id;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Reusable paging widget.
 * 
 * @author Jakob Merljak
 * 
 */
public class PagingWidget extends Composite {

	// i18n
	private final CommonConstants constants = Kuharija.constants;
	private final CommonMessages messages = Kuharija.messages;

	// handler
	private PagingHandler handler;

	// widgets
	private final TextBox pageBox = new TextBox();
	private final InlineLabel pageCount = new InlineLabel();
	private final Button firstPageAnchor = new Button();
	private final Button previousPageAnchor = new Button();
	private final Button nextPageAnchor = new Button();
	private final Button lastPageAnchor = new Button();

	// variables
	private int page = 1;
	private int allPages = 1;

	public PagingWidget(PagingHandler pagingHandler) {
		this.handler = pagingHandler;

		firstPageAnchor.setIcon(IconType.FAST_BACKWARD);
		firstPageAnchor.setTitle(constants.pageFirst());
		firstPageAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					handler.changePage(1);
				}
			}
		});

		previousPageAnchor.setIcon(IconType.STEP_BACKWARD);
		previousPageAnchor.setTitle(constants.pagePrevious());
		previousPageAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					handler.changePage(page - 1);
				}
			}
		});

		InlineLabel pageLabel = new InlineLabel(constants.page());
		pageLabel.getElement().setId("pageLabel");
		pageCount.getElement().setId("pageCount");

		pageBox.setTitle(constants.pageInput());
		Roles.getTextboxRole().setAriaLabelledbyProperty(pageBox.getElement(), Id.of(pageLabel.getElement()), Id.of(pageCount.getElement()));
		pageBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (handler != null && event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					try {
						int newPage = Integer.parseInt(pageBox.getText());
						if (newPage > allPages) {
							newPage = allPages;
						} else if (newPage < 1) {
							newPage = 1;
						}
						handler.changePage(newPage);
					} catch (Exception e) {
						addStyleName("error");
					}
				}
			}
		});

		nextPageAnchor.setIcon(IconType.STEP_FORWARD);
		nextPageAnchor.setIconPosition(IconPosition.RIGHT);
		nextPageAnchor.setTitle(constants.pageNext());
		nextPageAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					handler.changePage(page + 1);
				}
			}
		});

		lastPageAnchor.setIcon(IconType.FAST_FORWARD);
		lastPageAnchor.setIconPosition(IconPosition.RIGHT);
		lastPageAnchor.setTitle(constants.pageLast());
		lastPageAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					handler.changePage((int) allPages);
				}
			}
		});

		FlowPanel main = new FlowPanel();
		main.setStyleName("pagingWidget");
		main.addStyleName(Constants.CONTROL_GROUP);
		main.add(firstPageAnchor);
		main.add(previousPageAnchor);
		main.add(pageLabel);
		main.add(pageBox);
		main.add(pageCount);
		main.add(nextPageAnchor);
		main.add(lastPageAnchor);
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
	public void setPage(int page, int pageSize, int allCount) {
		this.page = page;
		this.allPages = (allCount - 1) / pageSize + 1;

		firstPageAnchor.setEnabled(page > 1);
		previousPageAnchor.setEnabled(page > 1);
		if (page > 1) {
			firstPageAnchor.removeStyleName(Constants.DISABLED);
			previousPageAnchor.removeStyleName(Constants.DISABLED);
			Roles.getLinkRole().setAriaDisabledState(firstPageAnchor.getElement(), false);
			Roles.getLinkRole().setAriaDisabledState(previousPageAnchor.getElement(), false);
		} else {
			firstPageAnchor.addStyleName(Constants.DISABLED);
			previousPageAnchor.addStyleName(Constants.DISABLED);
			Roles.getLinkRole().setAriaDisabledState(firstPageAnchor.getElement(), true);
			Roles.getLinkRole().setAriaDisabledState(previousPageAnchor.getElement(), true);
		}

		removeStyleName("error");
		pageBox.setEnabled(allPages > 1);
		pageBox.setText(page + "");
		pageCount.setText(messages.ofPages(allPages));

		nextPageAnchor.setEnabled(page < allPages);
		lastPageAnchor.setEnabled(page < allPages);
		if (page < allPages) {
			nextPageAnchor.removeStyleName(Constants.DISABLED);
			lastPageAnchor.removeStyleName(Constants.DISABLED);
			Roles.getLinkRole().setAriaDisabledState(nextPageAnchor.getElement(), false);
			Roles.getLinkRole().setAriaDisabledState(lastPageAnchor.getElement(), false);
		} else {
			nextPageAnchor.addStyleName(Constants.DISABLED);
			lastPageAnchor.addStyleName(Constants.DISABLED);
			Roles.getLinkRole().setAriaDisabledState(nextPageAnchor.getElement(), true);
			Roles.getLinkRole().setAriaDisabledState(lastPageAnchor.getElement(), true);
		}
	}
}
