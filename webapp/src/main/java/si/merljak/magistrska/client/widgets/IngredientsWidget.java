package si.merljak.magistrska.client.widgets;

import java.util.List;

import si.merljak.magistrska.client.KuharijaEntry;
import si.merljak.magistrska.client.i18n.GlobalConstants;
import si.merljak.magistrska.common.dto.IngredientDto;
import si.merljak.magistrska.common.enumeration.Unit;

import com.github.gwtbootstrap.client.ui.Heading;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class IngredientsWidget extends Composite {
	private static final GlobalConstants constants = KuharijaEntry.getConstants();
	private static final NumberFormat numberFormat = KuharijaEntry.getNumberFormat();

	private Heading heading = new Heading(2, constants.ingredients());
	private FlowPanel ingredientsList = new FlowPanel();
	private Button buttonPlus = new Button("+");
	private Button buttonMinus = new Button("-");
	private TextBox textBox = new TextBox();
	
	private List<IngredientDto> ingredients;
	private int numOfPeopleBase;
	private int numOfPeople;
	private CheckBox convertToNonMetric = new CheckBox();

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
			String ingredientName = constants.ingredientMap().get(ingredient.getName());
			if (amount == null) {
				// uncountable
				if (convertToNonMetric.getValue()) {
					unit = unit.getNonMetricUnit();
				}
				ingredientsList.add(new Label(ingredientName + " " + constants.unitMap().get(unit.name())));
				continue;
			}
			
			amount = ingredient.getAmount()  * numOfPeople / numOfPeopleBase;
			
			if (convertToNonMetric.getValue()) {
				amount = unit.convertToNonMetric(amount).doubleValue();
				unit = unit.getNonMetricUnit();
			}
			
			ingredientsList.add(new Label(ingredientName + " " + numberFormat.format(amount) + " " + constants.unitMap().get(unit.name())));
		}
		
	}
}
