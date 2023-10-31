package com.example.myapplication.ui.utils;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

public class PercentageOperator extends Operator {


    /**
     * Create a new operator for use in expressions
     *
     * @param symbol           the symbol of the operator
     * @param numberOfOperands the number of operands the operator takes (1 or 2)
     * @param leftAssociative  set to true if the operator is left associative, false if it is right associative
     * @param precedence       the precedence value of the operator
     */
    public PercentageOperator(String symbol, int numberOfOperands, boolean leftAssociative, int precedence) {
        super(symbol, numberOfOperands, leftAssociative, precedence);
    }

    @Override
    public double apply(double[] values) {
        if (values.length == 1) {
            // 单目百分号操作，将值除以 100
            return values[0] / 100.0;
        } else {
            throw new IllegalArgumentException("Percentage operator is unary and accepts only one argument.");
        }
    }
}
