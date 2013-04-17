package si.merljak.magistrska.client.widgets;

import java.util.List;

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
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

// TODO cleanup, refactorize
public class IngredientsWidget extends Composite {
	private static final CommonConstants constants = Kuharija.constants;
	private static final IngredientsConstants ingredientsConstants = GWT.create(IngredientsConstants.class);
	private static final NumberFormat numberFormat = Kuharija.numberFormat;

	private Heading heading = new Heading(2, constants.ingredients());
	private UnorderedList ingredientsList = new UnorderedList();
	private Button buttonPlus = new Button("+");
	private Button buttonMinus = new Button("-");
	private TextBox textBox = new TextBox();
	
	private List<IngredientDto> ingredients;
	private int numOfPeopleBase;
	private int numOfPeople;
	private CheckBox convertToNonMetric = new CheckBox("non metric");

	public IngredientsWidget(List<IngredientDto> ingredients, int numOfMeals) {
		this.ingredients = ingredients;
		this.numOfPeopleBase = numOfMeals;
		this.numOfPeople = numOfMeals;

		textBox.setStyleName("span7");
		textBox.getElement().setId("appendedInputButtons");
		textBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				try {
					numOfPeople = Integer.parseInt(textBox.getText());
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
		
		convertToNonMetric.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateList();
			}
		});

		FlowPanel formPanel = new FlowPanel();
		formPanel.setStyleName("input-append");
		formPanel.add(textBox);
		formPanel.add(buttonMinus);
		formPanel.add(buttonPlus);

		FlowPanel panel = new FlowPanel();
		panel.add(heading);
		panel.add(ingredientsList);
		panel.add(formPanel);
		panel.add(convertToNonMetric);
		initWidget(panel);

		updateList();
	}

	private void updateList() {
		textBox.setText(numOfPeople + "");
		removeStyleName("control-group error");

		ingredientsList.clear();
		for (IngredientDto ingredient : ingredients) {
			Double amount = ingredient.getAmount();
			Unit unit = ingredient.getUnit();
			final String ingredientName = ingredient.getName();
			String ingredientNameString = ingredientsConstants.ingredientMap().get(ingredientName).toLowerCase();
			if (amount == null) {
				// uncountable
				if (convertToNonMetric.getValue()) {
					unit = Calc.getNonMetricUnit(unit);
				}

				Anchor label = new Anchor(ingredientNameString, IngredientPresenter.buildIngredientUrl(ingredientName));
				ingredientsList.add(new ListItem(label, new Label(" " + constants.unitMap().get(unit.name()))));
				continue;
			}
			
			amount = ingredient.getAmount()  * numOfPeople / numOfPeopleBase;
			
			if (convertToNonMetric.getValue()) {
				amount = Calc.convertToNonMetric(unit, amount).doubleValue();
				unit = Calc.getNonMetricUnit(unit);
			}
			
			Anchor label = new Anchor(
					ingredientNameString + " " + numberFormat.format(amount) + " " + constants.unitMap().get(unit.name()),
					IngredientPresenter.buildIngredientUrl(ingredientName));
			ingredientsList.add(new ListItem(label));
		}
		
	}
}
