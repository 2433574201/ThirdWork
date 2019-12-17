package com.example.thirdwork;

import com.example.thirdwork.utils.DegreesUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        System.out.println(DegreesUtil.getRotate(0,0,-1,0,-1,-1));
    }
}