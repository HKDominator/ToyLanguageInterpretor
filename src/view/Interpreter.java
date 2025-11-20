package view;

import controller.Controller;
import controller.IController;
import model.adt.exceptions.AppExceptions;
import model.expressions.ArithmeticExpression;
import model.expressions.IExpression;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepo;
import repository.Repo;

import java.util.Scanner;

public class Interpreter {
    public static void main(String[] args) {
        Programs programs = new Programs();

        TextMenu menu = new TextMenu();
        menu.addCommand( new ExitCommand("0", "exit"));
        menu.addCommand( new RunExample("1", "run first example", programs.getStatementController(1)));
        menu.addCommand( new RunExample("2", "run second example", programs.getStatementController(2)));
        menu.addCommand( new RunExample("3", "run third example", programs.getStatementController(3)));
        menu.addCommand(new RunExample("4", "run forth example", programs.getStatementController(4)));
        menu.addCommand(new RunExample("5", "run fifth example", programs.getStatementController(5)));
        menu.addCommand( new RunExample("6", "run sixth example", programs.getStatementController(6)));
        menu.show();
    }
}
