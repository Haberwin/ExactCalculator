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
import android.os.RemoteException;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;


import androidx.test.uiautomator.By;
import androidx.test.uiautomator.Direction;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CalculatorTests {
    private CalculatorHelper mCalculatorHelper = null;
    private static final int SHORT_TIMEOUT = 1000;
    private static final int LONG_TIMEOUT = 2000;
    private UiDevice mDevice = null;

    @Rule
    public ActivityTestRule<Calculator> activityRule =
            new ActivityTestRule<>(Calculator.class);


    @Before
    public void setUp() throws Exception {
        mDevice = UiDevice.getInstance(getInstrumentation());
        Context mContext = getInstrumentation().getContext();
        mDevice.setOrientationNatural();
        //mDevice.pressHome();
        mCalculatorHelper = CalculatorHelper.getInstance(mDevice, mContext);
    }

    @After
    public void tearDown() throws Exception {
        mDevice.unfreezeRotation();
    }

    @Test
    //无输入时点击加号
    public void testInputStartWithAdd() {
        mCalculatorHelper.clickButton("op_add");
        assertNull(mCalculatorHelper.getResultText("formula"));
    }

    @Test
    //无输入时点击减号
    public void testInputStartWithSub() {
        mCalculatorHelper.clickButton("op_sub");
        assertNotNull(mCalculatorHelper.getResultText("formula"));
        assertEquals("Cannot start with '-'", "−", mCalculatorHelper.getResultText("formula"));
    }

    @Test
    //无输入时点击乘号
    public void testInputStartWithMul() {
        mCalculatorHelper.clickButton("op_mul");
        assertNull(mCalculatorHelper.getResultText("formula"));
    }

    @Test
    //无输入时点击除号
    public void testInputStartWithDiv() {
        mCalculatorHelper.clickButton("op_div");
        assertNull(mCalculatorHelper.getResultText("formula"));
    }

    @Test
    //无输入时点击等于号
    public void testInputStartWithEqual() {
        mCalculatorHelper.clickButton("eq");
        assertNull(mCalculatorHelper.getResultText("formula"));
    }


    @Test
    //Test to verify basic addition functionality
    public void testAdd() {
        mCalculatorHelper.performCalculation("digit_9", "op_add", "digit_9");
        assertEquals("Results are wrong", "18", mCalculatorHelper.getResultText("result"));
        mDevice.waitForIdle();
    }

    @Test

    //Test to verify basic subraction functionality
    public void testSubtract() {
        mCalculatorHelper.performCalculation("digit_6", "op_sub", "digit_4");
        assertEquals("Results are wrong", "2", mCalculatorHelper.getResultText("result"));
        mDevice.waitForIdle();
    }

    @Test

    //Test to verify basic multiplication functionality
    public void testSin90() {
        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.setToggleMode("DEG");

        mCalculatorHelper.clickButton("fun_sin");
        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("digit_9");
        mCalculatorHelper.clickButton("digit_0");
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "1", mCalculatorHelper.getResultText("result"));
    }

    @Test
    //测试持续输入小数点
    public void testContinueInputPoint() {
        for (int i = 0; i < 10; i++) {
            mCalculatorHelper.clickButton("dec_point");
        }
        assertEquals("Input points fail", ".", mCalculatorHelper.getResultText("formula"));
    }

    @Test

    //测试角度运算Tan45
    public void testTan45() {
        mCalculatorHelper.showAdvancedPad();

        mCalculatorHelper.setToggleMode("DEG");

        mCalculatorHelper.clickButton("fun_tan");
        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("digit_4");
        mCalculatorHelper.clickButton("digit_5");
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "1", mCalculatorHelper.getResultText("result"));
    }

    @Test

    //测试弧运算
    public void testRAD() {
        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.setToggleMode("RAD");


        mCalculatorHelper.clickButton("fun_cos");
        mCalculatorHelper.clickButton("const_pi");

        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "−1", mCalculatorHelper.getResultText("result"));
    }

    @Test

    //测试%运算
    public void testPercent() {


        mCalculatorHelper.clickButton("digit_0");
        mCalculatorHelper.clickButton("dec_point");
        mCalculatorHelper.clickButton("digit_5");
        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.clickButton("op_pct");
        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "0.005", mCalculatorHelper.getResultText("result"));
    }

    @Test

    //测试Ln运算和e的值
    public void testLnE() {


        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.clickButton("fun_ln");
        mCalculatorHelper.clickButton("const_e");

        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "1", mCalculatorHelper.getResultText("result"));
    }

    @Test

    //测试Log运算
    public void testLog100() {
        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.clickButton("fun_log");

        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("digit_1");
        mCalculatorHelper.clickButton("digit_0");
        mCalculatorHelper.clickButton("digit_0");
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "2", mCalculatorHelper.getResultText("result"));
    }

    @Test
    //测试阶乘运算
    public void testFactorial() {
        mCalculatorHelper.clickButton("digit_1");
        mCalculatorHelper.clickButton("digit_0");
        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.clickButton("op_fact");

        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "3,628,800", mCalculatorHelper.getResultText("result"));

    }

    @Test
    //测试开方
    public void testSqrt() {

        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.clickButton("op_sqrt");

        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("digit_4");
        mCalculatorHelper.clickButton("digit_9");
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "7", mCalculatorHelper.getResultText("result"));

    }

    @Test
    //测试乘方
    public void testPower() {
        mCalculatorHelper.clickButton("digit_2");

        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.clickButton("op_pow");

        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("digit_9");
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "512", mCalculatorHelper.getResultText("result"));

    }

    @Test
    //测试括号
    public void testParentheses() {
        mCalculatorHelper.clickButton("digit_8");
        mCalculatorHelper.clickButton("op_mul");

        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.clickButton("lparen");

        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("op_sub");
        mCalculatorHelper.clickButton("digit_2");

        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.clickButton("rparen");

        mCalculatorHelper.dismissAdvancedPad();

        assertEquals("Results are wrong", "−16", mCalculatorHelper.getResultText("result"));


    }

    @Test
    //测试反正弦函数
    public void testArcSin() {
        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.setToggleMode("DEG");
        mCalculatorHelper.setToggleInv(true);
        mCalculatorHelper.clickButton("fun_arcsin");
        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("digit_1");
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "90", mCalculatorHelper.getResultText("result"));
    }

    @Test
    //测试反余弦函数
    public void testArcCos() {
        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.setToggleMode("DEG");
        mCalculatorHelper.setToggleInv(true);
        mCalculatorHelper.clickButton("fun_arccos");
        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("digit_1");
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "0", mCalculatorHelper.getResultText("result"));
    }

    @Test
    //测试反正切函数
    public void testArcTan() {
        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.setToggleMode("DEG");
        mCalculatorHelper.setToggleInv(true);
        mCalculatorHelper.clickButton("fun_arctan");
        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("digit_1");
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "45", mCalculatorHelper.getResultText("result"));
    }

    @Test
    //测试e的幂运算
    public void testExp() {
        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.setToggleInv(true);
        mCalculatorHelper.clickButton("fun_exp");
        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("digit_2");
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "7.3890560989306", mCalculatorHelper.getResultText("result"));
    }

    @Test
    //测试10的幂运算
    public void test10Pow() {
        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.setToggleInv(true);
        mCalculatorHelper.clickButton("fun_10pow");
        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("digit_5");
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "100,000", mCalculatorHelper.getResultText("result"));
    }

    @Test
    //测试平方运算
    public void testSquare() {
        mCalculatorHelper.clickButton("digit_2");

        mCalculatorHelper.clickButton("digit_5");

        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.setToggleInv(true);
        mCalculatorHelper.clickButton("op_sqr");
        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "625", mCalculatorHelper.getResultText("result"));
    }


    @Test
    //Test to verify basic multiplication functionality
    public void testMultiply() {
        mCalculatorHelper.performCalculation("digit_7", "op_mul", "digit_5");
        assertEquals("Results are wrong", "35", mCalculatorHelper.getResultText("result"));
        mDevice.waitForIdle();
    }

    @Test

    //Test to verify basic divition functionality
    public void testDivide() {
        mCalculatorHelper.performCalculation("digit_8", "op_div", "digit_2");
        assertEquals("Results are wrong", "4", mCalculatorHelper.getResultText("result"));
        mDevice.waitForIdle();
    }

    @Test

    //Test to verify to clear the results
    public void testClearButton() {
        mCalculatorHelper.performCalculation("digit_9", "op_mul", "digit_9");
        mCalculatorHelper.clickButton("clr");

        assertNotNull(mCalculatorHelper.getObjectByResourceId("del"));

        assertNull(mCalculatorHelper.getResultText("result"));

    }

    @Test
    //测试长按删除键
    public void testLongPressDeleteButton() {
        mCalculatorHelper.pressLongDigits();

        assertEquals("Input Error", "123,456,789", mCalculatorHelper.getResultText("formula"));
        mCalculatorHelper.clickButton("del", 1000);
        assertNull(mCalculatorHelper.getResultText("formula"));

    }

    @Test
    //测试短按删除键
    public void testDeleteButton() {
        mCalculatorHelper.pressLongDigits();
        assertEquals("Input Error", "123,456,789", mCalculatorHelper.getResultText("formula"));

        for (int i = 0; i < 5; i++) {
            mCalculatorHelper.clickButton("del");
        }
        assertEquals("Delete fail", "1,234", mCalculatorHelper.getResultText("formula"));
    }

    @Test

    // Test divide by zero error
    public void testDivideByZero() {
        mCalculatorHelper.performCalculation("digit_1", "op_div", "digit_0");
        mDevice.waitForIdle();
        assertEquals("Error", "Can't divide by 0", mCalculatorHelper.getResultText("result"));

    }

    @Test

    // Test Scroll funtion in long results
    public void testScrollLongResult() {
        mCalculatorHelper.pressLongDigits();
        mCalculatorHelper.clickButton("op_mul");
        mCalculatorHelper.pressLongDigits();
        mCalculatorHelper.clickButton("eq");
        mCalculatorHelper.scrollResults("result", Direction.LEFT, Direction.RIGHT);
        assertEquals("Scroll failed", "1.52415787501E16", mCalculatorHelper.getResultText("result"));

    }

    @Test
    //测试横竖屏切换
    public void testLandScape() throws RemoteException {
        mDevice.unfreezeRotation();
        mDevice.setOrientationLeft();
        mDevice.waitForWindowUpdate(CalculatorHelper.PACKAGE_NAME,LONG_TIMEOUT);
        mCalculatorHelper.pressLongDigits();
        mCalculatorHelper.clickButton("op_mul");
        mCalculatorHelper.pressLongDigits();
        mCalculatorHelper.clickButton("eq");
        //mCalculatorHelper.scrollResults("result",Direction.UP,Direction.DOWN);
        assertEquals("Scroll failed", "15,241,578,750,190,521", mCalculatorHelper.getResultText("result"));


    }

    @Test

    // Test to verify the advanced panel and basic operation works
    public void testCos0() {
        mCalculatorHelper.showAdvancedPad();
        if (mCalculatorHelper.getResultText("toggle_mode").equals("DEG")) {
            mCalculatorHelper.clickButton("toggle_mode");
        }
        mCalculatorHelper.clickButton("fun_cos");
        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("digit_0");
        mCalculatorHelper.clickButton("eq");
        assertEquals("Results are wrong", "1", mCalculatorHelper.getResultText("result"));

    }

    @Test

    //Test timeouts on complex calculations
    public void testComplexCalculationTimeout() {
        mCalculatorHelper.pressNumber100000();
        mCalculatorHelper.showAdvancedPad();
        mCalculatorHelper.clickButton("op_fact");
        mCalculatorHelper.dismissAdvancedPad();
        mCalculatorHelper.clickButton("eq");
        mDevice.waitForIdle();
        UiObject2 alertTitle=mDevice.wait(Until.findObject(By.res("android:id/alertTitle")),LONG_TIMEOUT);
        assertNotNull("Alert pop up not found", alertTitle);

        mDevice.waitForIdle();
        UiObject2 message=mCalculatorHelper.getObjectByResourceId("message");
        assertEquals("Message not found", "Value may be infinite or undefined.", message.getText());
        UiObject2 button_Dismiss=mDevice.wait(Until.findObject(By.res("android:id/button2")),LONG_TIMEOUT);
        assertNotNull("Dismiss button not found", button_Dismiss);
        //mCalculatorHelper.clickButton("android:id/button2");
        button_Dismiss.click();
        mCalculatorHelper.clearResults("formula");
    }

    @Test

    // Test DEG/RAD switch happens and display changes
    public void testDegRadSwitch() {
        mCalculatorHelper.showAdvancedPad();

//        UiObject2 toggleButton = mDevice.wait(
//                Until.findObject(By.res(mCalculatorHelper.PACKAGE_NAME, "toggle_mode")),
//                SHORT_TIMEOUT);
        assertNotNull("Toggle Button not found", mCalculatorHelper.getObjectByResourceId("toggle_mode"));

//        UiObject2 modeBox = mDevice.wait(
//                Until.findObject(By.res(mCalculatorHelper.PACKAGE_NAME, "mode")),
//                SHORT_TIMEOUT);
        assertNotNull("Mode Box not found", mCalculatorHelper.getObjectByResourceId("mode"));

        for (int i = 0; i < 3; i++) { //Test the toggle button 3 times
            mCalculatorHelper.clickButton("toggle_mode");
            mDevice.waitForIdle();
            assertNotSame("Switch Failed", mCalculatorHelper.getResultText("toggle_mode"), mCalculatorHelper.getResultText("mode"));
        }
        mCalculatorHelper.dismissAdvancedPad();

    }
}
