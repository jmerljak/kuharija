package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.handler.PagingHandler;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.CommonMessages;

import com.github.gwtbootstrap.client.ui.base.IconAnchor;
import com.github.gwtbootstrap.client.ui.base.InlineLabel;
import com.github.gwtbootstrap.client.ui.base.TextBox;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.constants.IconPosition;
import com.github.gwtbootstrap.client.ui.constants.IconType;
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
	private final IconAnchor firstPageAnchor = new IconAnchor();
	private final IconAnchor previousPageAnchor = new IconAnchor();
	private final IconAnchor nextPageAnchor = new IconAnchor();
	private final IconAnchor lastPageAnchor = new IconAnchor();

	// variables
	private long page = 1;
	private long allPages = 1;

	public PagingWidget(PagingHandler pagingHandler) {
		this.handler = pagingHandler;

		firstPageAnchor.setIcon(IconType.FAST_BACKWARD);
		firstPageAnchor.setText(constants.pageFirst());
		firstPageAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					handler.onPageChange(1);
				}
			}
		});

		previousPageAnchor.setIcon(IconType.STEP_BACKWARD);
		previousPageAnchor.setText(constants.pagePrevious());
		previousPageAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					handler.onPageChange(page - 1);
				}
			}
		});

		pageBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (handler != null && event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					try {
						long newPage = Long.parseLong(pageBox.getText());
						if (newPage > allPages) {
							newPage = allPages;
						} else if (newPage < 1) {
							newPage = 1;
						}
						handler.onPageChange(newPage);
					} catch (Exception e) {
						addStyleName("error");
					}
				}
			}
		});

		nextPageAnchor.setIcon(IconType.STEP_FORWARD);
		nextPageAnchor.setIconPosition(IconPosition.RIGHT);
		nextPageAnchor.setText(constants.pageNext());
		nextPageAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					handler.onPageChange(page + 1);
				}
			}
		});

		lastPageAnchor.setIcon(IconType.FAST_FORWARD);
		lastPageAnchor.setIconPosition(IconPosition.RIGHT);
		lastPageAnchor.setText(constants.pageLast());
		lastPageAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					handler.onPageChange(allPages);
				}
			}
		});

		FlowPanel main = new FlowPanel();
		main.setStyleName("pagingWidget");
		main.addStyleName(Constants.CONTROL_GROUP);
		main.add(firstPageAnchor);
		main.add(previousPageAnchor);
		main.add(new InlineLabel(constants.page()));
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
	public void setPage(long page, long pageSize, long allCount) {
		this.page = page;
		this.allPages = (allCount - 1) / pageSize + 1;

		firstPageAnchor.setEnabled(page > 1);
		previousPageAnchor.setEnabled(page > 1);

		removeStyleName("error");
		pageBox.setEnabled(allPages > 1);
		pageBox.setText(page + "");
		pageCount.setText(messages.ofPages(allPages));

		nextPageAnchor.setEnabled(page < allPages);
		lastPageAnchor.setEnabled(page < allPages);
	}
}
