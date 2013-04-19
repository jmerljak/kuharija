package si.merljak.magistrska.client.widgets;

import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.IngredientsConstants;
import si.merljak.magistrska.client.mvp.IngredientPresenter;
import si.merljak.magistrska.client.utils.Calc;
import si.merljak.magistrska.common.dto.IngredientDto;
import si.merljak.magistrska.common.enumeration.Unit;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Widget displaying recipe ingredients.
 * 
 * @author Jakob Merljak
 * 
 */
public class IngredientsWidget extends Composite {

	// i18n
	private final CommonConstants constants = Kuharija.constants;
	private final IngredientsConstants ingredientsConstants = Kuharija.ingredientsConstants;
	private final Map<String, String> ingredientMap = ingredientsConstants.ingredientMap();
	private final Map<String, String> unitMap = constants.unitMap();
	private final NumberFormat numberFormat = Kuharija.numberFormat;

	// widgets
	private final Heading heading = new Heading(2, ingredientsConstants.ingredients());
	private final UnorderedList ingredientsList = new UnorderedList();
	private final Button buttonPlus = new Button("+");
	private final Button buttonMinus = new Button("-");
	private final TextBox numberInput = new TextBox();
	private final CheckBox nonMetricCheckBox = new CheckBox("non metric");

	// 
	private List<IngredientDto> ingredients;
	private int numOfPeopleBase;
	private int numOfPeople;

	public IngredientsWidget() {
		numberInput.setStyleName("span7");
		numberInput.getElement().setId("appendedInputButtons");
		numberInput.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				try {
					numOfPeople = Integer.parseInt(numberInput.getText());
					updateList();
				} catch (Exception e) {
					addStyleName("control-group error");
				}
			}
		});
		
		buttonMinus.setStyleName("btn");
		buttonMinus.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (numOfPeople > 1) {
					numOfPeople--;
					updateList();
				}
			}
		});

		buttonPlus.setStyleName("btn");
		buttonPlus.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				numOfPeople++;
				updateList();
			}
		});
		
		nonMetricCheckBox.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateList();
			}
		});

		FlowPanel formPanel = new FlowPanel();
		formPanel.setStyleName("input-append");
		formPanel.add(numberInput);
		formPanel.add(buttonMinus);
		formPanel.add(buttonPlus);

		FlowPanel panel = new FlowPanel();
		panel.add(heading);
		panel.add(ingredientsList);
		panel.add(formPanel);
		panel.add(nonMetricCheckBox);
		initWidget(panel);
	}

	public void setIngredients(List<IngredientDto> ingredients, int numOfMeals) {
		this.ingredients = ingredients;
		this.numOfPeopleBase = numOfMeals;
		this.numOfPeople = numOfMeals;
		updateList();
	}

	private void updateList() {
		numberInput.setText(Integer.toString(numOfPeople));
		removeStyleName("control-group error");

		ingredientsList.clear();
		for (IngredientDto ingredient : ingredients) {
			ListItem listItem = new ListItem();
			ingredientsList.add(listItem);

			// name
			String name = ingredient.getName();
			String localizedName = ingredientMap.get(name).toLowerCase();
			Anchor ingredientLink = new Anchor(localizedName, IngredientPresenter.buildIngredientUrl(name));
			listItem.add(ingredientLink);

			// amount & unit
			Double amount = ingredient.getAmount();
			Unit unit = ingredient.getUnit();
			boolean convertToNonMetric = nonMetricCheckBox.getValue();

			if (amount != null) {
				double calculatedAmount = amount.doubleValue() * numOfPeople / numOfPeopleBase;
				if (convertToNonMetric) {
					calculatedAmount = Calc.convertToNonMetric(unit, calculatedAmount);
				}
				String amountString = numberFormat.format(calculatedAmount);
				Element span = DOM.createSpan();
				span.setInnerText(" " + amountString + " ");
				listItem.getElement().appendChild(span);
			}

			if (convertToNonMetric) {
				unit = Calc.getNonMetricUnit(unit);
			}
			String localizedUnitName = unitMap.get(unit.name());
			Element span = DOM.createSpan();
			span.setInnerText(" " + localizedUnitName + " ");
			listItem.getElement().appendChild(span);
			
		}
		
	}
}
