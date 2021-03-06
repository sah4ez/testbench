/**
 * Copyright (C) 2012 Vaadin Ltd
 *
 * This program is available under Commercial Vaadin Add-On License 3.0
 * (CVALv3).
 *
 * See the file licensing.txt distributed with this software for more
 * information about licensing.
 *
 * You should have received a copy of the license along with this program.
 * If not, see <http://vaadin.com/license/cval-3>.
 */
package com.vaadin.testbench.elements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.vaadin.testbench.By;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.elementsbase.ServerClass;

@ServerClass("com.vaadin.ui.ComboBox")
public class ComboBoxElement extends AbstractSelectElement {

    private static org.openqa.selenium.By bySuggestionPopup = By
            .vaadin("#popup");
    private static org.openqa.selenium.By byNextPage = By
            .className("v-filterselect-nextpage");
    private static org.openqa.selenium.By byPrevPage = By
            .className("v-filterselect-prevpage");

    /**
     * Input the given text to ComboBox and click on the suggestion if it
     * matches.
     *
     * @param text
     */
    public void selectByText(String text) {
        if (text.contains("(")) {
            sendTextWithParentheses(text);
        } else {
            WebElement textBox = findElement(By.vaadin("#textbox"));
            TestBenchElement tb = (TestBenchElement) textBox;
            boolean isReadonly = getReadOnly(tb);
            // if element is readonly, we will change that, change the value
            // and restore the original value of readonly
            if (isReadonly) {
                setReadOnly(tb, false);
            }
            textBox.clear();
            textBox.sendKeys(text);
            if (isReadonly) {
                setReadOnly(tb, true);
            }
        }

        List<String> popupSuggestions = getPopupSuggestions();
        if (popupSuggestions.size() != 0
                && text.equals(popupSuggestions.get(0))) {
            getSuggestionPopup().findElement(By.tagName("td")).click();
        }
    }

    private boolean getReadOnly(WebElement elem) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return (Boolean) js.executeScript("return arguments[0].readOnly", elem);

    }

    private void setReadOnly(WebElement elem, boolean value) {
        String strValue = Boolean.toString(value);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].readOnly =" + strValue, elem);
    }

    /*
     * Workaround selenium's bug: sendKeys() will not send left parentheses
     * properly. See #14048.
     */
    private void sendTextWithParentheses(String text) {

        String OPEN_PARENTHESES = "_OPEN_PARENT#H#ESES_";

        WebElement textBox = findElement(By.vaadin("#textbox"));
        textBox.clear();
        String tamperedText = text.replaceAll("\\(", OPEN_PARENTHESES);
        findElement(By.vaadin("#textbox")).sendKeys(tamperedText);

        JavascriptExecutor js = getCommandExecutor();
        String jsScript = String.format(
                "arguments[0].value = arguments[0].value.replace(/%s/g, '(')",
                OPEN_PARENTHESES);
        js.executeScript(jsScript, textBox);

        // refresh suggestions popupBox
        textBox.sendKeys("a" + Keys.BACK_SPACE);
    }

    /**
     * Open the suggestion popup
     */
    public void openPopup() {
        findElement(By.vaadin("#button")).click();
    }

    /**
     * Get the text representation of all suggestions on the current page
     *
     * @return List of suggestion texts
     */
    public List<String> getPopupSuggestions() {
        WebElement popup = getSuggestionPopup();
        List<String> suggestionsTexts = new ArrayList<String>();
        // Check that there are suggestions
        List<WebElement> tables = getSuggestionPopup().findElements(
                By.tagName("table"));
        if (tables == null || tables.isEmpty()) {
            return suggestionsTexts;
        }
        WebElement table = tables.get(0);
        List<WebElement> suggestions = table.findElements(By.tagName("span"));
        for (WebElement suggestion : suggestions) {
            String text = suggestion.getText();
            if (!text.isEmpty()) {
                suggestionsTexts.add(text);
            }
        }
        return suggestionsTexts;
    }

    /**
     * Opens next popup page.
     *
     * @return True if next page opened. false if doesn't have next page
     */
    public boolean openNextPage() {
        try {
            getSuggestionPopup().findElement(byNextPage).click();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Open previous popup page.
     *
     * @return True if previous page opened. False if doesn't have previous page
     */
    public boolean openPrevPage() {
        try {
            getSuggestionPopup().findElement(byPrevPage).click();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Returns the suggestion popup element
     */
    public WebElement getSuggestionPopup() {
        ensurePopupOpen();
        return findElement(bySuggestionPopup);
    }

    /**
     * Return value of the combo box element
     *
     * @return value of the combo box element
     */
    public String getValue() {
        return findElement(By.tagName("input")).getAttribute("value");
    }

    private void ensurePopupOpen() {
        if (!isElementPresent(bySuggestionPopup)) {
            openPopup();
        }
    }
}
