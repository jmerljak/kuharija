package si.merljak.magistrska.client.widgets;

import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.IngredientsConstants;
import si.merljak.magistrska.client.mvp.ingredient.IngredientPresenter;
import si.merljak.magistrska.client.utils.Calc;
import si.merljak.magistrska.common.dto.IngredientDto;
import si.merljak.magistrska.common.enumeration.Unit;

import com.github.gwtbootstrap.client.ui.AppendButton;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.base.InlineLabel;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
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
	private final Heading heading = new Heading(2, constants.ingredients());
	private final UnorderedList ingredientsList = new UnorderedList();
	private final AppendButton formPanel = new AppendButton();
	private final Button buttonPlus = new Button("+");
	private final Button buttonMinus = new Button("-");
	private final TextBox numberInput = new TextBox();
	private final CheckBox metricCheckBox = new CheckBox(constants.metricUnits());

	// variables
	private List<IngredientDto> ingredients;
	private int numOfMealsBase;
	private int numOfMeals;

	public IngredientsWidget() {
		numberInput.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				try {
					numOfMeals = Integer.parseInt(numberInput.getText());
					updateList();
				} catch (Exception e) {
					formPanel.addStyleName("error");
				}
			}
		});
		
		buttonMinus.setStyleName(Constants.BTN);
		buttonMinus.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (numOfMeals > 1) {
					numOfMeals--;
					updateList();
				}
			}
		});

		buttonPlus.setStyleName(Constants.BTN);
		buttonPlus.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				numOfMeals++;
				updateList();
			}
		});

		metricCheckBox.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateList();
			}
		});

		formPanel.addStyleName(Constants.CONTROL_GROUP);
		formPanel.add(numberInput);
		formPanel.add(buttonMinus);
		formPanel.add(buttonPlus);

		FlowPanel panel = new FlowPanel();
		panel.add(heading);
		panel.add(ingredientsList);
		panel.add(formPanel);
		panel.add(metricCheckBox);
		initWidget(panel);
	}

	public void setIngredients(List<IngredientDto> ingredients, int numOfMeals, boolean useMetric) {
		this.ingredients = ingredients;
		this.numOfMealsBase = numOfMeals;
		this.numOfMeals = numOfMeals;
		metricCheckBox.setValue(useMetric);
		updateList();
	}

	private void updateList() {
		numberInput.setText(Integer.toString(numOfMeals));
		formPanel.removeStyleName("error");

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
			boolean useMetric = metricCheckBox.getValue();

			if (amount != null) {
				double calculatedAmount = amount.doubleValue() * numOfMeals / numOfMealsBase;
				calculatedAmount = Calc.calculateAmount(unit, calculatedAmount, useMetric);
				listItem.add(new InlineLabel(" " + numberFormat.format(calculatedAmount)));
			}

			unit = Calc.getUnit(unit, useMetric);
			String localizedUnitName = unitMap.get(unit.name());
			listItem.add(new InlineLabel(" " + localizedUnitName));
		}
		
	}
}
