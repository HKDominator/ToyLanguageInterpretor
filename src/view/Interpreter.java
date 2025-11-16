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
        IStatement ex1 = new ComposedStatements(
                new VariableDeclarationStatement("v", new IntType()),
                new ComposedStatements(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatements(new VariableExpression("v"))
                ));
        IRepo repo1 = new Repo(ex1, "log1.txt");
        IController controller1 = new Controller(repo1);

        IStatement ex2 = new ComposedStatements(
                new VariableDeclarationStatement("a", new IntType()),
                new ComposedStatements(
                        new VariableDeclarationStatement("b", new IntType()),
                        new ComposedStatements(
                                new AssignmentStatement("a",
                                        new ArithmeticExpression('+',
                                                new ValueExpression(new IntValue(2)),
                                                new ArithmeticExpression('*',
                                                        new ValueExpression(new IntValue(3)),
                                                        new ValueExpression(new IntValue(5))
                                                )
                                        )

                                ),
                                new ComposedStatements(
                                        new AssignmentStatement("b",
                                                new ArithmeticExpression('+',
                                                        new VariableExpression("a"),
                                                        new ValueExpression( new IntValue(1))
                                                )
                                        ),
                                        new PrintStatements(new VariableExpression("b"))
                                )
                        )
                )
        );
        IRepo repo2 = new Repo(ex2, "log2.txt");
        IController controller2 = new Controller(repo2);

        IStatement ex3 = new ComposedStatements(
                new VariableDeclarationStatement("a", new BoolType()),
                new ComposedStatements(
                        new VariableDeclarationStatement("v", new IntType()),//
                        new ComposedStatements(//
                                new AssignmentStatement("a",
                                        new ValueExpression(new BoolValue(true))
                                ),//
                                new ComposedStatements(
                                        new IfStatement(
                                                new VariableExpression("a"),//
                                                new AssignmentStatement("v",
                                                        new ValueExpression(new IntValue(2))
                                                ),
                                                new AssignmentStatement("v",
                                                        new ValueExpression( new IntValue(3)))),
                                        new PrintStatements(new VariableExpression("v"))
                                )
                        )
                )
        );
        IRepo repo3 = new Repo(ex3, "log3.txt");
        IController controller3 = new Controller(repo3);

        IStatement ex5 = new ComposedStatements(
                new VariableDeclarationStatement("varf", new StringType()),
                new ComposedStatements(
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new ComposedStatements(
                                new OpenReadFile(new VariableExpression("varf")),
                                new ComposedStatements(
                                        new VariableDeclarationStatement("varc", new IntType()),
                                        new ComposedStatements(
                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                new ComposedStatements(
                                                        new PrintStatements(new VariableExpression("varc")),
                                                        new ComposedStatements(
                                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new ComposedStatements(
                                                                        new PrintStatements(new VariableExpression("varc")),
                                                                        new CloseReadFile(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        // Create the Repo and Controller for Example 5
        IRepo repo5 = new Repo(ex5, "log5.txt");
        IController controller5 = new Controller(repo5);

        TextMenu menu = new TextMenu();
        menu.addCommand( new ExitCommand("0", "exit"));
        menu.addCommand( new RunExample("1", ex1.toString(), controller1));
        menu.addCommand( new RunExample("2", ex2.toString(), controller2));
        menu.addCommand( new RunExample("3", ex3.toString(), controller3));
        menu.addCommand(new RunExample("5", ex5.toString(), controller5));
        menu.show();
    }
}
