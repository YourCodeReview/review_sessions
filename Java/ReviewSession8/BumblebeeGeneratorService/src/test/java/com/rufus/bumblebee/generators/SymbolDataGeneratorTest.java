package com.rufus.bumblebee.generators;

import org.junit.Test;

import static org.junit.Assert.*;

public class SymbolDataGeneratorTest {

    @Test
    public void testAmountValues(){
        SymbolDataGenerator generator=new SymbolDataGenerator();
        generator.setLen(5);
        generator.setCount(5);
        generator.setMode("STRING");
        generator.setCascade(false);
        generator.setNull(false);
        generator.build();
        assertEquals(generator.build().size(), (int) generator.getCount());
    }

    @Test
    public void testLenTestValue(){
        SymbolDataGenerator generator=new SymbolDataGenerator();
        generator.setLen(5);
        generator.setCount(5);
        generator.setMode("STRING");
        generator.setCascade(false);
        generator.setNull(false);
        generator.build();
        assertEquals(generator.build().get(0).length(), (int) generator.getLen());
    }

}