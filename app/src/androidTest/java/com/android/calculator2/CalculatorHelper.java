/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.calculator2;

import android.content.Context;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.Direction;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class CalculatorHelper {
    private static CalculatorHelper mInstance = null;
    private static final int SHORT_TIMEOUT = 1000;
    private static final int LONG_TIMEOUT = 2000;
    public static final String PACKAGE_NAME = "com.android.calculator2";
    public static final String APP_NAME = "Calculator";
    public static final String TEST_TAG = "CalculatorTests";
    public final int TIMEOUT = 500;
    private Context mContext;
    private UiDevice mDevice = null;
    //public ILauncherStrategy mLauncherStrategy;

    private CalculatorHelper(UiDevice device, Context context) {
       mDevice = device;
       mContext = context;
       //mLauncherStrategy = LauncherStrategyFactory.getInstance(mDevice).getLauncherStrategy();
    }

    public static CalculatorHelper getInstance(UiDevice device, Context context) {
        if (mInstance == null) {
        mInstance = new CalculatorHelper(device, context);
      }
        return mInstance;
    }

    public void launchApp(String packageName, String appName) {
        if (!mDevice.hasObject(By.pkg(packageName).depth(0))) {
        //mLauncherStrategy.launch(appName, packageName);
      }
    }

    public void clickButton(String resource_id) {
//        UiObject2 button = mDevice.wait(
//            Until.findObject(By.res(PACKAGE_NAME, resource_id)),
//                LONG_TIMEOUT);
//        Assert.assertNotNull("Element not found or pressed", button);
//        button.click();
        clickButton(resource_id,1);
    }
    public void clickButton(String resource_id,long duration) {
        UiObject2 button = mDevice.wait(
                Until.findObject(By.res(PACKAGE_NAME, resource_id)),
                LONG_TIMEOUT);
        assertNotNull("Element not found or pressed", button);
        button.click(duration);
    }

    public void setToggleMode(String toggleModeText){
        UiObject2 toggleMode = mDevice.wait(
                Until.findObject(By.res(PACKAGE_NAME, "toggle_mode")),
                SHORT_TIMEOUT);
        if (toggleMode.getText().equals(toggleModeText)) {
            toggleMode.click();
        }
        assertNotEquals(toggleModeText,toggleMode.getText());
    }
    public void setToggleInv(Boolean isToggleInv){
        UiObject2 toggleInv = mDevice.wait(
                Until.findObject(By.res(PACKAGE_NAME, "toggle_inv")),
                SHORT_TIMEOUT);
        if (toggleInv.isSelected()^isToggleInv) {
            toggleInv.click();
        }
        assertEquals("Inv mode was wrong",isToggleInv,toggleInv.isSelected());
    }




    public void performCalculation(String input1, String operator, String input2) {
        clickButton(input1);
        clickButton(operator);
        clickButton(input2);
        clickButton("eq");
    }

    public void pressLongDigits() {
      for (int i=1; i<10; i++)    clickButton("digit_"+i);
    }

    public void pressNumber100000() {
        clickButton("digit_1");
        for (int i=0; i<5; i++)   clickButton("digit_0");
    }

    public String getResultText(String result) {
        UiObject2 resultText = mDevice.wait(
            Until.findObject(By.res(PACKAGE_NAME, result)),
                LONG_TIMEOUT);
        assertNotNull("Result text box not found", resultText);
        return resultText.getText();
    }

    public void clearResults(String result) {
        UiObject2 resultText = mDevice.wait(
              Until.findObject(By.res(PACKAGE_NAME, result)),
                  SHORT_TIMEOUT);
        assertNotNull("Result box not found", resultText);
        resultText.clear();
    }

    public void scrollResults(String result,Direction direction_start,Direction direction_recover) {
        UiObject2 resultText = mDevice.wait(
            Until.findObject(By.res(PACKAGE_NAME, result)),
                  SHORT_TIMEOUT);
        assertNotNull("Result text box not found", resultText);
        resultText.swipe(direction_start, 1.0f, 5000);
        assertEquals("Scroll failed","…41578750190521", getResultText("result"));
        mDevice.waitForIdle();
        resultText.swipe(direction_recover, 1.0f, 5000);
    }

    public void showAdvancedPad(){
        UiObject2 padAdvanced = mDevice.wait(
              Until.findObject(By.res(PACKAGE_NAME, "pad_advanced")),
                  SHORT_TIMEOUT);
            if (padAdvanced.isClickable()) {//don't click if already pad opened
                padAdvanced.click();
                assertNotNull("Advanced pad not found", padAdvanced);
            }
        mDevice.waitForIdle();
    }

    public void dismissAdvancedPad() {
        UiObject2 padAdvanced = mDevice.wait(
              Until.findObject(By.res(PACKAGE_NAME, "pad_advanced")),
                  SHORT_TIMEOUT);
        padAdvanced.swipe(Direction.RIGHT, 1.0f);
        mDevice.waitForIdle();
    }
}
