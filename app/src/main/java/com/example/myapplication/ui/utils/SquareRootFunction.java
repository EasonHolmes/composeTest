package com.example.myapplication.ui.utils;

import android.util.Log;

import net.objecthunter.exp4j.function.Function;

public class SquareRootFunction extends Function {
    
    public SquareRootFunction(String name) {
        super(name, 1); // 指定函数的名称和参数数量
    }

    @Override
    public double apply(double... args) {
        if (args.length == 1) {
            // 使用Math.sqrt来模拟根号操作
            return Math.sqrt(args[0]);
        } else {
            throw new IllegalArgumentException("Square root function accepts only one argument.");
        }
    }
}